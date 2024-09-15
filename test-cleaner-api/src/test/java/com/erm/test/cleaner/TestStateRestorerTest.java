package com.erm.test.cleaner;

import java.util.List;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class TestStateRestorerTest {

    @Test
    void restoreState() {
        StateRestorer stateRestorer = mock(StateRestorer.class);
        TestStateRestorer restorer = new TestStateRestorer(List.of(stateRestorer));

        restorer.restoreState();

        verify(stateRestorer).restore();
    }
}