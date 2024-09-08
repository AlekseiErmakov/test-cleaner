package com.erm.test.cleaner;

import java.util.Set;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.truncate.Truncate;
import net.sf.jsqlparser.statement.update.Update;

public enum StatementType {
    INSERT, UPDATE, DELETE, SELECT, TRUNCATE, UNKNOWN;

    private static final Set<StatementType> MODIFYING_STATEMENT_TYPES = Set.of(INSERT, UPDATE, DELETE, TRUNCATE);

    public boolean isModifying() {
        return MODIFYING_STATEMENT_TYPES.contains(this);
    }

    public static StatementType from(Statement statement) {
        return switch (statement) {
            case Insert i -> INSERT;
            case Update u -> UPDATE;
            case Delete d -> DELETE;
            case Truncate t -> TRUNCATE;
            case Select s -> SELECT;
            default -> UNKNOWN;
        };
    }
}
