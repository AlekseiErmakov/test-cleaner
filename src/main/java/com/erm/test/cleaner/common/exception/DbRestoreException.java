package com.erm.test.cleaner.common.exception;

import static java.lang.String.format;

public class DbRestoreException extends RuntimeException {

    public DbRestoreException(String msg, Object... args) {
        super(format(msg, args));
    }

    public DbRestoreException(Throwable cause, String msg, Object... args) {
        super(format(msg, args), cause);
    }
}
