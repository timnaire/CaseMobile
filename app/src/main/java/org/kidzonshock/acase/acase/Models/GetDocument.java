package org.kidzonshock.acase.acase.Models;

import com.google.gson.annotations.SerializedName;

public class GetDocument {

    @SerializedName("case")
    private String case_id;

    public GetDocument(String case_id) {
        this.case_id = case_id;
    }

    public String getCase_id() {
        return case_id;
    }

    public void setCase_id(String case_id) {
        this.case_id = case_id;
    }
}
