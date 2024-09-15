package com.erm.test.cleaner;

public interface DbStateRestorer {
    void restore();
    void createBackup();
}
