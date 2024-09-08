package com.erm.test.cleaner.db.postgres;

import com.erm.test.cleaner.InsertQueryProvider;
import java.util.List;

public class PostgresSqlInsertQueryProvider implements InsertQueryProvider {
//    pg_dump --column-inserts --data-only --table=<table> <database>

    @Override
    public List<String> getInsertQueries() {
        return List.of();
    }

}
