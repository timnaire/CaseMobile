package org.kidzonshock.acase.acase.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PreAppointRequestResponse {

    @SerializedName("relation")
    private ArrayList<Relation> relation;

    public PreAppointRequestResponse(ArrayList<Relation> relation) {
        this.relation = relation;
    }

    public ArrayList<Relation> getRelation() {
        return relation;
    }

    public void setRelation(ArrayList<Relation> relation) {
        this.relation = relation;
    }
}
