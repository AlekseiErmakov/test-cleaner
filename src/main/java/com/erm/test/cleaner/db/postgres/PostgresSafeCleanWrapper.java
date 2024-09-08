package com.erm.test.cleaner.db.postgres;

import com.erm.test.cleaner.SafeCleanWrapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class PostgresSafeCleanWrapper implements SafeCleanWrapper {

    private final JdbcTemplate jdbcTemplate;

    public PostgresSafeCleanWrapper(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void before() {
        jdbcTemplate.execute("SET session_replication_role = 'replica';");
    }

    @Override
    public void after() {
        jdbcTemplate.execute("SET session_replication_role = 'origin';");
    }
}
