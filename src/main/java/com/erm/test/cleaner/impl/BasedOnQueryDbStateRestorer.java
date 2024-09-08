package com.erm.test.cleaner.impl;


import com.erm.test.cleaner.DbStateRestorer;
import com.erm.test.cleaner.ExecutedQueryHolder;
import com.erm.test.cleaner.InsertQueryHolder;
import com.erm.test.cleaner.InsertQueryProvider;
import com.erm.test.cleaner.SafeCleanWrapper;
import com.erm.test.cleaner.TableNameExtractor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

public class BasedOnQueryDbStateRestorer implements DbStateRestorer {

    private final ExecutedQueryHolder executedQueryHolder;
    private final TableNameExtractor tableNameExtractor;
    private final InsertQueryHolder insertQueryHolder;
    private final JdbcTemplate jdbcTemplate;
    private final InsertQueryProvider insertQueryProvider;
    private final SafeCleanWrapper safeCleanWrapper;

    public BasedOnQueryDbStateRestorer(ExecutedQueryHolder executedQueryHolder, TableNameExtractor tableNameExtractor,
            InsertQueryHolder insertQueryHolder, JdbcTemplate jdbcTemplate, InsertQueryProvider insertQueryProvider,
            SafeCleanWrapper safeCleanWrapper) {
        this.executedQueryHolder = executedQueryHolder;
        this.tableNameExtractor = tableNameExtractor;
        this.insertQueryHolder = insertQueryHolder;
        this.jdbcTemplate = jdbcTemplate;
        this.insertQueryProvider = insertQueryProvider;
        this.safeCleanWrapper = safeCleanWrapper;
    }

    @Override
    @Transactional
    public void restore() {
        safeCleanWrapper.before();
        executedQueryHolder.getExecutedQueries().forEach(query -> {
            ParsingResult result = tableNameExtractor.extractTableName(query);
            if (result.statementType().isModifying()) {
                result.tableNames().forEach(tableName -> {
                    jdbcTemplate.update("TRUNCATE TABLE " + tableName);
                    insertQueryHolder.getQueryForTable(tableName).ifPresent(jdbcTemplate::update);
                });
            }
        });
        executedQueryHolder.clean();
        safeCleanWrapper.after();
    }

    @Override
    public void createBackup() {
        insertQueryProvider.getInsertQueries().forEach(query -> {
            ParsingResult result = tableNameExtractor.extractTableName(query);
            if (result.statementType().isModifying()) {
                result.tableNames().forEach(tableName -> insertQueryHolder.addQueryForTable(tableName, query));
            }
        });
    }
}