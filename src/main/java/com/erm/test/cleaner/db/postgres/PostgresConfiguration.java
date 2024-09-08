package com.erm.test.cleaner.db.postgres;

import com.erm.test.cleaner.SafeCleanWrapper;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

public class PostgresConfiguration {


    @Bean
    public SafeCleanWrapper safeCleanWrapper(JdbcTemplate jdbcTemplate) {
        return new PostgresSafeCleanWrapper(jdbcTemplate);
    }

}
