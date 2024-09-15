package com.erm.test.cleaner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

public class SpringDataJdbcTableRestorer implements TableRestorer {

    private Logger log = LoggerFactory.getLogger(SpringDataJdbcTableRestorer.class);
    private final JdbcTemplate jdbcTemplate;
    private final InsertQueryHolder insertQueryHolder;

    public SpringDataJdbcTableRestorer(JdbcTemplate jdbcTemplate, InsertQueryHolder insertQueryHolder) {
        this.jdbcTemplate = jdbcTemplate;
        this.insertQueryHolder = insertQueryHolder;
    }

    @Override
    public void restore(String tableName) {
        try {
            jdbcTemplate.update("TRUNCATE TABLE " + tableName);
            insertQueryHolder.getQueriesForTable(tableName).forEach(jdbcTemplate::update);
        } catch (Exception e) {
            log.error("", e);
        }
    }
}
