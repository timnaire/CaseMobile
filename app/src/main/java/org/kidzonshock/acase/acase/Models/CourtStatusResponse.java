package org.kidzonshock.acase.acase.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CourtStatusResponse {

    @SerializedName("error")
    private boolean error;
    @SerializedName("message")
    private String message;
    @SerializedName("court_status")
    private ArrayList<CourtStatus> court_status;

    public CourtStatusResponse(boolean error, String message, ArrayList<CourtStatus> court_status) {
        this.error = error;
        this.message = message;
        this.court_status = court_status;
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

    public ArrayList<CourtStatus> getCourt_status() {
        return court_status;
    }

    public void setCourt_status(ArrayList<CourtStatus> court_status) {
        this.court_status = court_status;
    }
}
