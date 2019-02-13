package org.kidzonshock.acase.acase.Models;

import com.google.gson.annotations.SerializedName;

public class EditCase {

    @SerializedName("case_id")
    private String case_id;

    @SerializedName("case_title")
    private String case_title;

    @SerializedName("case_description")
    private String case_description;

    @SerializedName("case_status")
    private String case_status;

    public EditCase(String case_id, String case_title, String case_description, String case_status) {
        this.case_id = case_id;
        this.case_title = case_title;
        this.case_description = case_description;
        this.case_status = case_status;
    }

    public String getCase_id() {
        return case_id;
    }

    public void setCase_id(String case_id) {
        this.case_id = case_id;
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

    public String getCase_status() {
        return case_status;
    }

    public void setCase_status(String case_status) {
        this.case_status = case_status;
    }
}
