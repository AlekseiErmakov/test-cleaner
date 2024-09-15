package com.erm.test.cleaner.listener;

import com.erm.test.cleaner.ExecutedQueryHolder;
import java.util.List;
import net.ttddyy.dsproxy.ExecutionInfo;
import net.ttddyy.dsproxy.QueryInfo;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TestQueryListenerTest {

    private ExecutedQueryHolder executedQueryHolder;
    private TestQueryListener testQueryListener;

    @Test
    void beforeQuery() {
        QueryInfo info = mock(QueryInfo.class);
        executedQueryHolder = mock(ExecutedQueryHolder.class);
        testQueryListener = new TestQueryListener(executedQueryHolder);
        String testQuery = "testQuery";
        when(info.getQuery()).thenReturn(testQuery);

        testQueryListener.beforeQuery(mock(ExecutionInfo.class), List.of(info));

        verify(info).getQuery();
        verify(executedQueryHolder).addQuery(testQuery);
    }
}