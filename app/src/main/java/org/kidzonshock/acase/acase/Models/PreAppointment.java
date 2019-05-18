package org.kidzonshock.acase.acase.Models;

import com.google.gson.annotations.SerializedName;

public class PreAppointment {

    @SerializedName("lawyer_id")
    private String lawyer_id;
    @SerializedName("status")
    private String status;

    public PreAppointment(String lawyer_id, String status) {
        this.lawyer_id = lawyer_id;
        this.status = status;
    }

    public String getLawyer_id() {
        return lawyer_id;
    }

    public void setLawyer_id(String lawyer_id) {
        this.lawyer_id = lawyer_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
