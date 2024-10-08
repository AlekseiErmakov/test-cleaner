package com.erm.test.cleaner.impl;

import com.erm.test.cleaner.BackupCommandProvider;
import com.erm.test.cleaner.InsertQueryProvider;
import com.erm.test.cleaner.LightSqlDumpParser;
import com.erm.test.cleaner.common.exception.DbRestoreException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import org.testcontainers.containers.Container.ExecResult;
import org.testcontainers.containers.JdbcDatabaseContainer;

public class DbSqlInsertQueryProvider implements InsertQueryProvider {

    protected final JdbcDatabaseContainer<?> container;
    protected final String backupFileName;
    private final LightSqlDumpParser lightSqlDumpParser;
    private final BackupCommandProvider backupCommandProvider;

    public DbSqlInsertQueryProvider(JdbcDatabaseContainer<?> container, String backupFileName, LightSqlDumpParser lightSqlDumpParser,
            BackupCommandProvider backupCommandProvider) {
        this.container = container;
        this.backupFileName = backupFileName;
        this.lightSqlDumpParser = lightSqlDumpParser;
        this.backupCommandProvider = backupCommandProvider;
    }

    @Override
    public Map<String, List<String>> getInsertQueries() {
        try {
            createBackup();
            String sqlDump = container.copyFileFromContainer(backupFileName,
                    inputStream -> new String(inputStream.readAllBytes(), StandardCharsets.UTF_8));
            return lightSqlDumpParser.parse(sqlDump);
        } catch (IOException e) {
            throw new DbRestoreException(e, "Failed to create insert query dump");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new DbRestoreException(e, "Failed to create insert query dump");
        }
    }

    private void createBackup() throws IOException, InterruptedException {
        ExecResult execResult = container.execInContainer(backupCommandProvider.createBackupCommand());
        if (execResult.getExitCode() == 1) {
            throw new DbRestoreException("Backup creation failed. Error log: %n. Log: %n. Exit code: %n.", execResult.getStderr(),
                    execResult.getStdout(), execResult.getExitCode());
        }
    }

}
