package com.sky.usermanager.rest.handlers;

public class ObjectErrorResponse {

    private int status;

    private String message;

    private long timeStamp;

    public ObjectErrorResponse() {
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public static class NotFoundException extends RuntimeException {

        public NotFoundException() {
            super("Object with given ID not found");
        }
    }

    public static class ForbiddenException extends RuntimeException {

        public ForbiddenException() {
            super("Method not allowed for current user");
        }
    }

}