package com.erm.test.cleaner.impl;

import com.erm.test.cleaner.InsertQueryHolder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryInsertQueryHolder implements InsertQueryHolder {

    private final Map<String, List<String>> insertQueryMap = new HashMap<>();

    @Override
    public List<String> getQueriesForTable(String tableName) {
        return insertQueryMap.getOrDefault(tableName, Collections.emptyList());
    }

    @Override
    public void addQueryForTable(String tableName, String query) {
        insertQueryMap.computeIfAbsent(tableName, (key) -> new ArrayList<>()).add(query);
    }
}
