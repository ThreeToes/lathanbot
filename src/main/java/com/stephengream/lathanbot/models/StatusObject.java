package com.stephengream.lathanbot.models;

/**
 * Simple status object to pass back to clients
 * @author stephen
 */
public class StatusObject {
    private String message;
    
    public StatusObject(String message){
        this.message = message;
    }
    
    public String getMessage(){
        return message;
    }
}
