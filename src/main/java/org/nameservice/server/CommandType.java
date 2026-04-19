package org.nameservice.server;


public enum CommandType {
    REGISTER,
    RESOLVE,
    DEREGISTER;

    public static CommandType fromString(String command) {
        try {
            return CommandType.valueOf(command.toUpperCase());
        } catch (Exception e) {
            return null;
        }
    }
}

