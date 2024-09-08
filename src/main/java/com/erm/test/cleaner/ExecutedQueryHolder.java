package com.erm.test.cleaner;

import java.util.Set;

public interface ExecutedQueryHolder {

    void addQuery(String query);

    Set<String> getExecutedQueries();

    void clean();
}
