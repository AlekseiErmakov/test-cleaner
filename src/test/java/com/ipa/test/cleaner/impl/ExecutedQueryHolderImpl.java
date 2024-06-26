package com.ipa.test.cleaner.impl;

import com.ipa.test.cleaner.ExecutedQueryHolder;
import java.util.HashSet;
import java.util.Set;

public class ExecutedQueryHolderImpl implements ExecutedQueryHolder {

    private final Set<String> executedQueries = new HashSet<>();

    @Override
    public void addQuery(String query) {
        executedQueries.add(query);
    }

    @Override
    public Set<String> getExecutedQueries() {
        return Set.copyOf(executedQueries);
    }

    @Override
    public void clean() {
        executedQueries.clear();
    }
}
