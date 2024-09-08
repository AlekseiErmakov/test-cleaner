package com.erm.test.cleaner.impl;

import com.erm.test.cleaner.TableNameExtractor;
import java.util.Set;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.util.TablesNamesFinder;

public class JSqlTableNameExtractor implements TableNameExtractor {

    private static Statement parse(String query) {
        try {
            return CCJSqlParserUtil.parse(query);
        } catch (JSQLParserException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ParsingResult extractTableName(String query) {
        Statement statement = parse(query);
        TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();
        Set<String> tables = tablesNamesFinder.getTables(statement);
        return new ParsingResult(tables, JSqlParserStatementTypeMapper.mapToStatementType(statement));
    }
}
