package com.erm.test.cleaner;

import java.util.List;

public class BashContainerCommand implements ContainerCommand {

    private final String[] command;

    private BashContainerCommand(String... command) {
        this.command = command;
    }

    @Override
    public String[] getCommand() {
        return command;
    }

    public static BashCommandBuilder builder() {
        return new BashCommandBuilder();
    }

    public static class BashCommandBuilder {

        private static final String BASH = "bash";
        private static final String C = "-c";
        private String userName;
        private String password;
        private String applicationName;
        private List<String> arguments;

        private BashCommandBuilder() {
        }

        public BashCommandBuilder withUserName(String userName) {
            this.userName = userName;
            return this;
        }

        public BashCommandBuilder withPassword(String password) {
            this.password = password;
            return this;
        }

        public BashCommandBuilder withArguments(List<String> arguments) {
            this.arguments = arguments;
            return this;
        }

        public BashCommandBuilder withApplicationName(String applicationName) {
            this.applicationName = applicationName;
            return this;
        }

        public BashContainerCommand build() {
            return new BashContainerCommand(BASH, C, applicationName + " -u " + userName + " --password="
                    + password + " " + String.join(" ", arguments));
        }
    }
}
