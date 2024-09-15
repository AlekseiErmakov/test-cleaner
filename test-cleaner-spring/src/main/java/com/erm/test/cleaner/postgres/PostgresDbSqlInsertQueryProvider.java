package com.erm.test.cleaner.postgres;

import com.erm.test.cleaner.BackupCommandProvider;
import com.erm.test.cleaner.BashContainerCommand;
import java.util.List;
import org.testcontainers.containers.JdbcDatabaseContainer;

public class PostgresDbSqlInsertQueryProvider implements BackupCommandProvider {
    private final JdbcDatabaseContainer<?> container;
    private final String databaseName;
    private final String backupFileName;

    public PostgresDbSqlInsertQueryProvider(JdbcDatabaseContainer<?> container, String databaseName, String backupFileName) {
        this.container = container;
        this.databaseName = databaseName;
        this.backupFileName = backupFileName;
    }

    @Override
    public String[] createBackupCommand() {
        return BashContainerCommand.builder()
                .withApplicationName("pgDump")
                .withArguments(
                        List.of("-U", container.getUsername(), "--column-inserts", "--data-only", databaseName, ">", backupFileName))
                .build()
                .getCommand();
    }
}
