package org.kidzonshock.acase.acase.Models;

import com.google.gson.annotations.SerializedName;

public class DeleteCase {

    @SerializedName("case_id")
    private String case_id;

    public DeleteCase(String case_id) {
        this.case_id = case_id;
    }

    public String getCase_id() {
        return case_id;
    }

    public void setCase_id(String case_id) {
        this.case_id = case_id;
    }
}
