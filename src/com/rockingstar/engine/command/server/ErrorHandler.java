package com.rockingstar.engine.command.server;

/**
 * @author Rocking Stars
 * @since  beta 1.0
 */
public class ErrorHandler {

    private String error;

    /**
     * forwards error to Errorhandler
     * @param error String with error.
     */
    public ErrorHandler(String error){
        this.error = error;
    }

    /**
     *  returns the error
     * @return String containing the error
     */
    public String getError(){
        return error;
    }

}
