package com.erm.test.cleaner;

import com.erm.test.cleaner.impl.DbSqlInsertQueryProvider;
import com.erm.test.cleaner.impl.InMemoryExecutedQueryHolder;
import com.erm.test.cleaner.impl.InMemoryInsertQueryHolder;
import com.erm.test.cleaner.impl.JSqlTableNameExtractor;
import com.erm.test.cleaner.listener.TestQueryListener;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.testcontainers.containers.JdbcDatabaseContainer;

@Configuration
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

    @Bean
    public TableNameExtractor tableNameExtractor() {
        return new JSqlTableNameExtractor();
    }

    @Bean
    public SafeCleanWrapper safeCleanWrapper(@Value("before.query.script") String beforeQuery, @Value("after.query.script") String afterQuery,
            JdbcTemplate jdbcTemplate) {
        return new SpringJdbcSafeCleanWrapper(beforeQuery, afterQuery, jdbcTemplate);
    }

    @Bean
    public TableRestorer tableRestorer(JdbcTemplate jdbcTemplate, InsertQueryHolder insertQueryHolder) {
        return new SpringDataJdbcTableRestorer(jdbcTemplate, insertQueryHolder);
    }

    @Bean
    public InsertQueryProvider insertQueryProvider(JdbcDatabaseContainer<?> container, @Value("backup.file.name") String backupFileName,
            LightSqlDumpParser lightSqlDumpParser,
            BackupCommandProvider backupCommandProvider) {
        return new DbSqlInsertQueryProvider(container, backupFileName, lightSqlDumpParser, backupCommandProvider);
    }

    @Bean
    public DbStateRestorer dbStateRestorer(ExecutedQueryHolder executedQueryHolder, TableNameExtractor tableNameExtractor,
            InsertQueryHolder insertQueryHolder, TableRestorer tableRestorer, InsertQueryProvider insertQueryProvider,
            SafeCleanWrapper safeCleanWrapper) {
        return new SpringBasedOnQueryDbStateRestorer(executedQueryHolder, tableNameExtractor, insertQueryHolder, tableRestorer, insertQueryProvider,
                safeCleanWrapper);
    }

    @Bean
    public TestStateRestorer testStateRestorer(List<StateRestorer> stateRestorers) {
        return new TestStateRestorer(stateRestorers);
    }
}
