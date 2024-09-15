package com.erm.test.cleaner;

import java.util.List;
import java.util.Optional;

public interface InsertQueryHolder {

    List<String> getQueriesForTable(String tableName);

    void addQueryForTable(String tableName, String query);
}
