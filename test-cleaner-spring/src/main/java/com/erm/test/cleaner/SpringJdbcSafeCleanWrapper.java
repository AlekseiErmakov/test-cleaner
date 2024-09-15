package com.erm.test.cleaner;

import org.springframework.jdbc.core.JdbcTemplate;

public class SpringJdbcSafeCleanWrapper implements SafeCleanWrapper {

    private final String beforeQuery;
    private final String afterQuery;
    private final JdbcTemplate jdbcTemplate;

    public SpringJdbcSafeCleanWrapper(String beforeQuery, String afterQuery, JdbcTemplate jdbcTemplate) {
        this.beforeQuery = beforeQuery;
        this.afterQuery = afterQuery;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void before() {
        jdbcTemplate.update(beforeQuery);
    }

    @Override
    public void after() {
        jdbcTemplate.update(afterQuery);
    }
}
