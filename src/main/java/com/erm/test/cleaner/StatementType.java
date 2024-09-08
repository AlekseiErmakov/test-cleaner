package com.erm.test.cleaner;

import java.util.Set;

public enum StatementType {
    INSERT, UPDATE, DELETE, SELECT, TRUNCATE, UNKNOWN;

    private static final Set<StatementType> MODIFYING_STATEMENT_TYPES = Set.of(INSERT, UPDATE, DELETE, TRUNCATE);

    public boolean isModifying() {
        return MODIFYING_STATEMENT_TYPES.contains(this);
    }
}
