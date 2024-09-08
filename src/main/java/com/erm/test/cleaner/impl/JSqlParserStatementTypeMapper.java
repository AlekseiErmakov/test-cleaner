package com.erm.test.cleaner.impl;

import com.erm.test.cleaner.StatementType;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.truncate.Truncate;
import net.sf.jsqlparser.statement.update.Update;
import org.springframework.data.relational.core.sql.Select;

import static com.erm.test.cleaner.StatementType.DELETE;
import static com.erm.test.cleaner.StatementType.INSERT;
import static com.erm.test.cleaner.StatementType.SELECT;
import static com.erm.test.cleaner.StatementType.UNKNOWN;
import static com.erm.test.cleaner.StatementType.UPDATE;


public class JSqlParserStatementTypeMapper {

    public static StatementType mapToStatementType(Statement statement) {
        return switch (statement) {
            case Insert i -> INSERT;
            case Update u -> UPDATE;
            case Delete d -> DELETE;
            case Truncate t -> UPDATE;
            case Select s -> SELECT;
            default -> UNKNOWN;
        };
    }
}
