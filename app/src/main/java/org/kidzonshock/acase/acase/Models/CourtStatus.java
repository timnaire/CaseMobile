package org.kidzonshock.acase.acase.Models;

import com.google.gson.annotations.SerializedName;

public class CourtStatus {

    @SerializedName("court_status")
    private String court_status;

    public CourtStatus(String court_status) {
        this.court_status = court_status;
    }

    public String getCourt_status() {
        return court_status;
    }

    public void setCourt_status(String court_status) {
        this.court_status = court_status;
    }
}
