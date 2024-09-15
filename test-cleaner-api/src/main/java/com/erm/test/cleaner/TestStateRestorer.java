package com.erm.test.cleaner;

import java.util.List;

public class TestStateRestorer {

    private final List<StateRestorer> stateRestorers;

    public TestStateRestorer(List<StateRestorer> stateRestorers) {
        this.stateRestorers = stateRestorers;
    }

    public void restoreState() {
        stateRestorers.forEach(StateRestorer::restore);
    }
}
