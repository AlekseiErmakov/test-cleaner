package com.erm.test.cleaner;

import com.erm.test.cleaner.impl.ExecutedQueryHolderImpl;
import com.erm.test.cleaner.impl.InsertQueryHolderImpl;
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
        return new ExecutedQueryHolderImpl();
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public InsertQueryHolder insertQueryHolder() {
        return new InsertQueryHolderImpl();
    }
}
