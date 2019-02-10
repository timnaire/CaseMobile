package org.kidzonshock.acase.acase.Models;

import com.google.gson.annotations.SerializedName;

public class PreAppointResponse {

    @SerializedName("lawyer_id")
    private String lawyer_id;
    @SerializedName("relation_id")
    private String relation_id;
    @SerializedName("status")
    private String status;

    public PreAppointResponse(String lawyer_id, String relation_id, String status) {
        this.lawyer_id = lawyer_id;
        this.relation_id = relation_id;
        this.status = status;
    }

    public String getLawyer_id() {
        return lawyer_id;
    }

    public void setLawyer_id(String lawyer_id) {
        this.lawyer_id = lawyer_id;
    }

    public String getRelation_id() {
        return relation_id;
    }

    public void setRelation_id(String relation_id) {
        this.relation_id = relation_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
