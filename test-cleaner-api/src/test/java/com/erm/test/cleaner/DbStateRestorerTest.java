package com.erm.test.cleaner;

import com.erm.test.cleaner.impl.BasedOnQueryDbStateRestorer;
import com.erm.test.cleaner.impl.ParsingResult;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.erm.test.cleaner.StatementType.INSERT;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class DbStateRestorerTest {

    private static final String TEST_TABLE_NAME = "test";
    private static final String TEST_EXECUTED_QUERY = "query";
    private static final String TEST_INSERT = "test insert";

    private ExecutedQueryHolder executedQueryHolder;
    private TableNameExtractor tableNameExtractor;
    private InsertQueryHolder insertQueryHolder;
    private TableRestorer tableRestorer;
    private InsertQueryProvider insertQueryProvider;
    private SafeCleanWrapper safeCleanWrapper;

    private DbStateRestorer dbStateRestorer;

    @BeforeEach
    public void init() {
        executedQueryHolder = mock(ExecutedQueryHolder.class);
        tableNameExtractor = mock(TableNameExtractor.class);
        insertQueryHolder = mock(InsertQueryHolder.class);
        tableRestorer = mock(TableRestorer.class);
        insertQueryProvider = mock(InsertQueryProvider.class);
        safeCleanWrapper = mock(SafeCleanWrapper.class);
        dbStateRestorer = new BasedOnQueryDbStateRestorer(
                executedQueryHolder,
                tableNameExtractor,
                insertQueryHolder,
                tableRestorer,
                insertQueryProvider,
                safeCleanWrapper
        );
    }

    @Test
    void shouldRestoreDb() {
        when(executedQueryHolder.getExecutedQueries()).thenReturn(Set.of(TEST_EXECUTED_QUERY));
        when(tableNameExtractor.extractTableName(TEST_EXECUTED_QUERY)).thenReturn(new ParsingResult(Set.of(TEST_TABLE_NAME), INSERT));
        when(insertQueryHolder.getQueriesForTable(TEST_TABLE_NAME)).thenReturn(List.of());

        dbStateRestorer.restore();
        verify(executedQueryHolder).getExecutedQueries();
        verify(tableNameExtractor).extractTableName(TEST_EXECUTED_QUERY);
        verify(executedQueryHolder).clean();
        verify(safeCleanWrapper).before();
        verify(safeCleanWrapper).after();
    }

    @Test
    void shouldRestoreDbWithInsert() {
        when(executedQueryHolder.getExecutedQueries()).thenReturn(Set.of(TEST_EXECUTED_QUERY));
        when(tableNameExtractor.extractTableName(TEST_EXECUTED_QUERY)).thenReturn(new ParsingResult(Set.of(TEST_TABLE_NAME), INSERT));
        when(insertQueryHolder.getQueriesForTable(TEST_TABLE_NAME)).thenReturn(List.of(TEST_INSERT));

        dbStateRestorer.restore();
        verify(executedQueryHolder).getExecutedQueries();
        verify(tableNameExtractor).extractTableName(TEST_EXECUTED_QUERY);
        verify(tableRestorer).restore(TEST_TABLE_NAME);
        verify(executedQueryHolder).clean();
        verify(safeCleanWrapper).before();
        verify(safeCleanWrapper).after();
    }

    @Test
    void shouldCreateBackup() {
        when(insertQueryProvider.getInsertQueries())
                .thenReturn(Map.of(TEST_TABLE_NAME, List.of(TEST_EXECUTED_QUERY)));

        dbStateRestorer.createBackup();

        verify(insertQueryProvider).getInsertQueries();
        verify(insertQueryHolder).addQueryForTable(TEST_TABLE_NAME, TEST_EXECUTED_QUERY);
    }

    @Test
    void shouldNotCreateBackupIfStatementIsNotModifying() {
        when(insertQueryProvider.getInsertQueries())
                .thenReturn(Map.of(TEST_TABLE_NAME, List.of()));

        dbStateRestorer.createBackup();

        verify(insertQueryProvider).getInsertQueries();
        verify(insertQueryHolder, never()).addQueryForTable(anyString(), anyString());
    }
}