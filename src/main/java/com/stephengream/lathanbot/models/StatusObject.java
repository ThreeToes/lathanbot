package com.stephengream.lathanbot.models;

import java.io.Serializable;

/**
 * Simple status object to pass back to clients
 * @author stephen
 */
public class StatusObject implements Serializable{
    private String message;

    public void setMessage(String message) {
        this.message = message;
    }
    
    public StatusObject(){}
        
    public String getMessage(){
        return message;
    }
}
