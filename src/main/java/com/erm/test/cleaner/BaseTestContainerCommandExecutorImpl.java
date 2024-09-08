package com.erm.test.cleaner;

import org.testcontainers.containers.Container;

public abstract class BaseTestContainerCommandExecutorImpl implements TestContainerCommandExecutor {

    @Override
    public void execute(ContainerCommand command) {
    }

    protected abstract Container<?> getContainer();
    protected abstract String username();
    protected abstract String password();
}
