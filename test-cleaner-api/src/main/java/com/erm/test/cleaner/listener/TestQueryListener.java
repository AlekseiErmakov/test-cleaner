package com.erm.test.cleaner.listener;

import com.erm.test.cleaner.ExecutedQueryHolder;
import java.util.List;
import net.ttddyy.dsproxy.ExecutionInfo;
import net.ttddyy.dsproxy.QueryInfo;
import net.ttddyy.dsproxy.listener.QueryExecutionListener;

public class TestQueryListener implements QueryExecutionListener {

    private final ExecutedQueryHolder executedQueryHolder;

    public TestQueryListener(ExecutedQueryHolder executedQueryHolder) {
        this.executedQueryHolder = executedQueryHolder;
    }

    @Override
    public void beforeQuery(ExecutionInfo executionInfo, List<QueryInfo> list) {
        list.forEach(queryInfo -> executedQueryHolder.addQuery(queryInfo.getQuery()));
    }

    @Override
    public void afterQuery(ExecutionInfo executionInfo, List<QueryInfo> list) {

    }
}
