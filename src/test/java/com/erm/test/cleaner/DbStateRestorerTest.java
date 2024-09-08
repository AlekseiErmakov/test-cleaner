package com.erm.test.cleaner;

import com.erm.test.cleaner.impl.BasedOnQueryDbStateRestorer;
import com.erm.test.cleaner.impl.ParsingResult;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import static com.erm.test.cleaner.StatementType.INSERT;
import static com.erm.test.cleaner.StatementType.SELECT;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class DbStateRestorerTest {

    private static final String TEST_TABLE_NAME = "test";
    private static final String TEST_EXECUTED_QUERY = "query";
    private static final String TEST_INSERT = "insert";

    private ExecutedQueryHolder executedQueryHolder;
    private TableNameExtractor tableNameExtractor;
    private InsertQueryHolder insertQueryHolder;
    private JdbcTemplate jdbcTemplate;
    private InsertQueryProvider insertQueryProvider;
    private SafeCleanWrapper safeCleanWrapper;

    private DbStateRestorer dbStateRestorer;

    @BeforeEach
    public void init() {
        executedQueryHolder = mock(ExecutedQueryHolder.class);
        tableNameExtractor = mock(TableNameExtractor.class);
        insertQueryHolder = mock(InsertQueryHolder.class);
        jdbcTemplate = mock(JdbcTemplate.class);
        insertQueryProvider = mock(InsertQueryProvider.class);
        safeCleanWrapper = mock(SafeCleanWrapper.class);
        dbStateRestorer = new BasedOnQueryDbStateRestorer(
                executedQueryHolder,
                tableNameExtractor,
                insertQueryHolder,
                jdbcTemplate,
                insertQueryProvider,
                safeCleanWrapper
        );
    }

    @Test
    void shouldRestoreDb() {
        when(executedQueryHolder.getExecutedQueries()).thenReturn(Set.of(TEST_EXECUTED_QUERY));
        when(tableNameExtractor.extractTableName(TEST_EXECUTED_QUERY)).thenReturn(new ParsingResult(Set.of(TEST_TABLE_NAME), INSERT));
        when(insertQueryHolder.getQueryForTable(TEST_TABLE_NAME)).thenReturn(Optional.empty());

        dbStateRestorer.restore();
        verify(executedQueryHolder).getExecutedQueries();
        verify(tableNameExtractor).extractTableName(TEST_EXECUTED_QUERY);
        verify(insertQueryHolder).getQueryForTable(TEST_TABLE_NAME);
        verify(jdbcTemplate).update("TRUNCATE TABLE " + TEST_TABLE_NAME);
        verify(executedQueryHolder).clean();
        verify(safeCleanWrapper).before();
        verify(safeCleanWrapper).after();
    }

    @Test
    void shouldRestoreDbWithInsert() {
        when(executedQueryHolder.getExecutedQueries()).thenReturn(Set.of(TEST_EXECUTED_QUERY));
        when(tableNameExtractor.extractTableName(TEST_EXECUTED_QUERY)).thenReturn(new ParsingResult(Set.of(TEST_TABLE_NAME), INSERT));
        when(insertQueryHolder.getQueryForTable(TEST_TABLE_NAME)).thenReturn(Optional.of(TEST_INSERT));

        dbStateRestorer.restore();
        verify(executedQueryHolder).getExecutedQueries();
        verify(tableNameExtractor).extractTableName(TEST_EXECUTED_QUERY);
        verify(insertQueryHolder).getQueryForTable(TEST_TABLE_NAME);
        verify(jdbcTemplate).update("TRUNCATE TABLE " + TEST_TABLE_NAME);
        verify(jdbcTemplate).update(TEST_INSERT);
        verify(executedQueryHolder).clean();
        verify(safeCleanWrapper).before();
        verify(safeCleanWrapper).after();
    }

    @Test
    void shouldCreateBackup() {
        when(insertQueryProvider.getInsertQueries())
                .thenReturn(List.of(TEST_EXECUTED_QUERY));
        when(tableNameExtractor.extractTableName(TEST_EXECUTED_QUERY))
                .thenReturn(new ParsingResult(Set.of(TEST_TABLE_NAME), INSERT));

        dbStateRestorer.createBackup();

        verify(insertQueryProvider).getInsertQueries();
        verify(tableNameExtractor).extractTableName(TEST_EXECUTED_QUERY);
        verify(insertQueryHolder).addQueryForTable(TEST_TABLE_NAME, TEST_EXECUTED_QUERY);
    }

    @Test
    void shouldNotCreateBackupIfStatementIsNotModifying() {
        when(insertQueryProvider.getInsertQueries())
                .thenReturn(List.of(TEST_EXECUTED_QUERY));
        when(tableNameExtractor.extractTableName(TEST_EXECUTED_QUERY))
                .thenReturn(new ParsingResult(Set.of(TEST_TABLE_NAME), SELECT));

        dbStateRestorer.createBackup();

        verify(insertQueryProvider).getInsertQueries();
        verify(tableNameExtractor).extractTableName(TEST_EXECUTED_QUERY);
        verify(insertQueryHolder, never()).addQueryForTable(anyString(), anyString());
    }
}