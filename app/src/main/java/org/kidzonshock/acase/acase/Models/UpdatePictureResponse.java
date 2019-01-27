package org.kidzonshock.acase.acase.Models;

public class UpdatePictureResponse {

    private boolean error;
    private String message;

    public UpdatePictureResponse(boolean error, String message) {
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
