package com.erm.test.cleaner;


import com.erm.test.cleaner.impl.ParsingResult;

public interface TableNameExtractor {

    ParsingResult extractTableName(String query);
}
