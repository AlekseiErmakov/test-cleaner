package com.erm.test.cleaner;

import java.util.Optional;

public interface InsertQueryHolder {

    Optional<String> getQueryForTable(String tableName);

    void addQueryForTable(String tableName, String query);
}
