package com.erm.test.cleaner.impl;

import com.erm.test.cleaner.BackupCommandProvider;
import com.erm.test.cleaner.InsertQueryProvider;
import com.erm.test.cleaner.LightSqlDumpParser;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.Container.ExecResult;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.utility.ThrowingFunction;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class DbSqlInsertQueryProviderTest {

    private static final String BACKUP_FILE = "Test backupFile";
    private static final String SQL_DUMP = "dump";
    private static final String[] CREATE_BACKUP_COMMAND = new String[0];
    private static final String INSERT_QUERY = "insert query";
    private static final String TABLE_NAME = "tableName";
    private static final int SUCCESS_CODE = 0;

    private InsertQueryProvider provider;
    private JdbcDatabaseContainer<?> container;
    private LightSqlDumpParser parser;
    private BackupCommandProvider backupCommandProvider;

    @BeforeEach
    public void init() {
        parser = mock(LightSqlDumpParser.class);
        container = mock(JdbcDatabaseContainer.class);
        backupCommandProvider = mock(BackupCommandProvider.class);
        provider = new DbSqlInsertQueryProvider(container, BACKUP_FILE, parser, backupCommandProvider){};
    }

    @Test
    void shouldReturnInsertQueryMap() throws Exception {
        ExecResult backupExecutionResult = mock(ExecResult.class);
        when(container.execInContainer(CREATE_BACKUP_COMMAND)).thenReturn(backupExecutionResult);
        when(backupExecutionResult.getExitCode()).thenReturn(SUCCESS_CODE);
        when(container.copyFileFromContainer(eq(BACKUP_FILE), any(ThrowingFunction.class))).thenReturn(SQL_DUMP);
        when(backupCommandProvider.createBackupCommand(container)).thenReturn(CREATE_BACKUP_COMMAND);
        Map<String, List<String>> expectedQueries = Map.of(TABLE_NAME, List.of(INSERT_QUERY));
        when(parser.parse(SQL_DUMP)).thenReturn(expectedQueries);

        assertThat(provider.getInsertQueries())
                .isEqualTo(Map.of(TABLE_NAME, List.of(INSERT_QUERY)));
        verify(container).execInContainer(CREATE_BACKUP_COMMAND);
        verify(backupExecutionResult).getExitCode();
        verify(container).copyFileFromContainer(eq(BACKUP_FILE), any(ThrowingFunction.class));
        verify(parser).parse(SQL_DUMP);
    }

}