package com.erm.test.cleaner.db.postgres;

import com.erm.test.cleaner.BashContainerCommand;
import com.erm.test.cleaner.LightSqlDumpParser;
import com.erm.test.cleaner.impl.DbSqlInsertQueryProvider;
import java.util.List;
import org.testcontainers.containers.PostgreSQLContainer;

public class PostgresDbSqlInsertQueryProvider extends DbSqlInsertQueryProvider {

    private static final String REGEX = "(?ms)^INSERT INTO\\s+([^\\s(]+).*?\\);\\s*$";

    private final String databaseName;

    public PostgresDbSqlInsertQueryProvider(PostgreSQLContainer<?> container, String backupFileName, String databaseName,
            LightSqlDumpParser lightSqlDumpParser) {
        super(container, backupFileName, lightSqlDumpParser);
        this.databaseName = databaseName;
    }

    @Override
    protected String[] getCreateBackupCommand() {
        return BashContainerCommand.builder()
                .withApplicationName("pgDump")
                .withArguments(
                        List.of("-U", container.getUsername(), "--column-inserts", "--data-only", databaseName, ">", backupFileName))
                .build()
                .getCommand();
    }


}
