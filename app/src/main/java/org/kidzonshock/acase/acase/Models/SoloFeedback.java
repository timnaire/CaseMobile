package org.kidzonshock.acase.acase.Models;

import com.google.gson.annotations.SerializedName;

public class SoloFeedback {

    @SerializedName("error")
    private boolean error;
    @SerializedName("message")
    private String message;
    @SerializedName("feedback")
    private Feedbacks feedback;

    public SoloFeedback(boolean error, String message, Feedbacks feedback) {
        this.error = error;
        this.message = message;
        this.feedback = feedback;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Feedbacks getFeedback() {
        return feedback;
    }

    public void setFeedback(Feedbacks feedback) {
        this.feedback = feedback;
    }
}
