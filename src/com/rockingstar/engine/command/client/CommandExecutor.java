package com.rockingstar.engine.command.client;


public class CommandExecutor {
    /**
     * Executes Commands
     */
    public static void execute(Command command) {
        command.execute();
    }
}
