package com.erm.test.cleaner.impl;

import java.util.Set;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class InMemoryExecutedQueryHolderTest {

    private static final String QUERY = "query";

    private InMemoryExecutedQueryHolder inMemoryExecutedQueryHolder = new InMemoryExecutedQueryHolder();

    @Test
    void shouldAddQuery() {
        inMemoryExecutedQueryHolder.addQuery(QUERY);

        assertThat(inMemoryExecutedQueryHolder.getExecutedQueries())
                .isEqualTo(Set.of(QUERY));
    }

    @Test
    void shouldCleanQueries() {
        inMemoryExecutedQueryHolder.addQuery(QUERY);

        inMemoryExecutedQueryHolder.clean();

        assertThat(inMemoryExecutedQueryHolder.getExecutedQueries())
                .isEqualTo(Set.of());
    }
}