package com.erm.test.cleaner;

import com.erm.test.cleaner.impl.InMemoryExecutedQueryHolder;
import com.erm.test.cleaner.impl.InMemoryInsertQueryHolder;
import com.erm.test.cleaner.listener.TestQueryListener;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

public class CleanerAutoConfiguration {

    @Bean
    public TestQueryListener testQueryListener(ExecutedQueryHolder executedQueryHolder) {
        return new TestQueryListener(executedQueryHolder);
    }

    @Bean
    public ExecutedQueryHolder executedQueryHolder() {
        return new InMemoryExecutedQueryHolder();
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public InsertQueryHolder insertQueryHolder() {
        return new InMemoryInsertQueryHolder();
    }
}
