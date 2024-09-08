package com.erm.test.cleaner.impl;


import com.erm.test.cleaner.DbStateRestorer;
import com.erm.test.cleaner.ExecutedQueryHolder;
import com.erm.test.cleaner.InsertQueryHolder;
import com.erm.test.cleaner.InsertQueryProvider;
import com.erm.test.cleaner.SafeCleanWrapper;
import com.erm.test.cleaner.TableNameExtractor;
import com.erm.test.cleaner.TableRestorer;
import org.springframework.transaction.annotation.Transactional;

public class BasedOnQueryDbStateRestorer implements DbStateRestorer {

    private final ExecutedQueryHolder executedQueryHolder;
    private final TableNameExtractor tableNameExtractor;
    private final InsertQueryHolder insertQueryHolder;
    private final TableRestorer tableRestorer;
    private final InsertQueryProvider insertQueryProvider;
    private final SafeCleanWrapper safeCleanWrapper;

    public BasedOnQueryDbStateRestorer(ExecutedQueryHolder executedQueryHolder, TableNameExtractor tableNameExtractor,
            InsertQueryHolder insertQueryHolder, TableRestorer tableRestorer, InsertQueryProvider insertQueryProvider, SafeCleanWrapper safeCleanWrapper) {
        this.executedQueryHolder = executedQueryHolder;
        this.tableNameExtractor = tableNameExtractor;
        this.insertQueryHolder = insertQueryHolder;
        this.tableRestorer = tableRestorer;
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
                result.tableNames().forEach(tableRestorer::restore);
            }
        });
        executedQueryHolder.clean();
        safeCleanWrapper.after();
    }

    @Override
    public void createBackup() {
        insertQueryProvider.getInsertQueries()
                .forEach((tableName, queries) -> queries.forEach(query -> insertQueryHolder.addQueryForTable(tableName, query)));
    }
}
