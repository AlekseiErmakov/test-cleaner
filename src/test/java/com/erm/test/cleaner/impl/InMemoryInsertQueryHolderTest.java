package com.erm.test.cleaner.impl;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class InMemoryInsertQueryHolderTest {

    private static final String TEST_TABLE_NAME = "TEST_TABLE_NAME";
    private static final String TEST_QUERY = "TEST_QUERY";

    private InMemoryInsertQueryHolder insertQueryHolder;

    @BeforeEach
    public void init() {
        insertQueryHolder = new InMemoryInsertQueryHolder();
    }

    @Test
    void shouldAddTableToPresentCollection() {
        String secondTable = "secondTable";
        insertQueryHolder.addQueryForTable(TEST_TABLE_NAME, TEST_QUERY);
        insertQueryHolder.addQueryForTable(TEST_TABLE_NAME, secondTable);

        assertThat(insertQueryHolder.getQueriesForTable(TEST_TABLE_NAME))
                .isEqualTo(List.of(TEST_QUERY, secondTable));
    }


    @Test
    void shouldReturnQueryIfPresent() {
        insertQueryHolder.addQueryForTable(TEST_TABLE_NAME, TEST_QUERY);

        assertThat(insertQueryHolder.getQueriesForTable(TEST_TABLE_NAME))
                .isEqualTo(List.of(TEST_QUERY));
    }

    @Test
    void shouldReturnEmptyCollectionIfNotPresent() {
        assertThat(insertQueryHolder.getQueriesForTable(TEST_TABLE_NAME))
                .isEqualTo(List.of());
    }
}