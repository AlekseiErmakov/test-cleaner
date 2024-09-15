package com.erm.test.cleaner;

import java.util.List;
import java.util.Map;

public interface LightSqlDumpParser {

    Map<String, List<String>> parse(String dump);
}
