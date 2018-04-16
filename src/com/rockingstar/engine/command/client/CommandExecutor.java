package com.rockingstar.engine.command.client;

/**
 * @author Rocking Stars
 * @since  beta 1.0
 */
public class CommandExecutor {
    /**
     * Executes Commands
     */
    public static void execute(Command command) {
        command.execute();
    }
}
