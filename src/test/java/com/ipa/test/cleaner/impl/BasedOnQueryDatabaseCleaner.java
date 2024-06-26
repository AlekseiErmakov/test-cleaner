package com.ipa.test.cleaner.impl;

import com.ipa.test.cleaner.DbCleaner;
import com.ipa.test.cleaner.ExecutedQueryHolder;
import com.ipa.test.cleaner.TableNameExtractor;

public class BasedOnQueryDatabaseCleaner implements DbCleaner {

    private final ExecutedQueryHolder executedQueryHolder;
    private final TableNameExtractor tableNameExtractor;

    public BasedOnQueryDatabaseCleaner(ExecutedQueryHolder executedQueryHolder, TableNameExtractor tableNameExtractor) {
        this.executedQueryHolder = executedQueryHolder;
        this.tableNameExtractor = tableNameExtractor;
    }

    @Override
    public void restore() {

    }
}
