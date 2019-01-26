package org.kidzonshock.acase.acase.Models;

public class SignupResponse {

    private boolean error;
    private String message;

    public SignupResponse(boolean error, String message) {
        this.error = error;
        this.message = message;
    }

    public boolean isError() {
        return error;
    }

    public String getMessage() {
        return message;
    }
}
