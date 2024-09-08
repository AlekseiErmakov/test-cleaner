package com.erm.test.cleaner.impl;

import com.erm.test.cleaner.StatementType;
import java.util.Set;

public record ParsingResult(
        Set<String> tableNames,
        StatementType statementType
) {

}
