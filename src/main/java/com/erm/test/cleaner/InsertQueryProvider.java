package com.erm.test.cleaner;

import java.util.List;
import java.util.Map;

public interface InsertQueryProvider {

    Map<String, List<String>> getInsertQueries();
}
