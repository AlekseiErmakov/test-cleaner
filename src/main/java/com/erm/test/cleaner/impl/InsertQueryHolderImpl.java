package com.erm.test.cleaner.impl;

import com.erm.test.cleaner.InsertQueryHolder;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InsertQueryHolderImpl implements InsertQueryHolder {

    private Map<String, String> insertQueryMap = new HashMap<>();

    @Override
    public Optional<String> getQueryForTable(String tableName) {
        return Optional.ofNullable(insertQueryMap.get(tableName));
    }

    @Override
    public void addQueryForTable(String tableName, String query) {
        insertQueryMap.put(tableName, query);
    }
}
