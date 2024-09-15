package com.erm.test.cleaner;

import com.erm.test.cleaner.impl.BasedOnQueryDbStateRestorer;
import org.springframework.transaction.annotation.Transactional;

public class SpringBasedOnQueryDbStateRestorer extends BasedOnQueryDbStateRestorer {

    public SpringBasedOnQueryDbStateRestorer(ExecutedQueryHolder executedQueryHolder, TableNameExtractor tableNameExtractor,
            InsertQueryHolder insertQueryHolder, TableRestorer tableRestorer, InsertQueryProvider insertQueryProvider,
            SafeCleanWrapper safeCleanWrapper) {
        super(executedQueryHolder, tableNameExtractor, insertQueryHolder, tableRestorer, insertQueryProvider, safeCleanWrapper);
    }

    @Override
    @Transactional
    public void restore() {
        super.restore();
    }
}
