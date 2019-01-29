package org.kidzonshock.acase.acase.Models;

public class CommonResponse {

    private boolean error;
    private String message;

    public CommonResponse(boolean error, String message) {
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
