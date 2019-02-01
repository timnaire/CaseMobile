package org.kidzonshock.acase.acase.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CaseDetails {

    @SerializedName("case_title")
    @Expose
    private String case_title;

    @SerializedName("created")
    @Expose
    private String created;

    public CaseDetails(String case_title, String created) {
        this.case_title = case_title;
        this.created = created;
    }

    public String getCase_title() {
        return case_title;
    }

    public void setCase_title(String case_title) {
        this.case_title = case_title;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }
}
