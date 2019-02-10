package org.kidzonshock.acase.acase.Models;

import com.google.gson.annotations.SerializedName;

public class EditCase {

    @SerializedName("case_title")
    private String case_title;

    @SerializedName("case_description")
    private String case_description;

    public EditCase(String case_title, String case_description) {
        this.case_title = case_title;
        this.case_description = case_description;
    }

    public String getCase_title() {
        return case_title;
    }

    public void setCase_title(String case_title) {
        this.case_title = case_title;
    }

    public String getCase_description() {
        return case_description;
    }

    public void setCase_description(String case_description) {
        this.case_description = case_description;
    }
}
