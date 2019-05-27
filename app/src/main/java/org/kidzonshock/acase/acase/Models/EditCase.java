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

    @SerializedName("remarks")
    private String remarks;

    @SerializedName("court_status")
    private String court_status;

    @SerializedName("client_type")
    private String client_type;

    public EditCase(String case_id, String case_title, String case_description, String case_status, String remarks, String court_status, String client_type) {
        this.case_id = case_id;
        this.case_title = case_title;
        this.case_description = case_description;
        this.case_status = case_status;
        this.remarks = remarks;
        this.court_status = court_status;
        this.client_type = client_type;
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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getCourt_status() {
        return court_status;
    }

    public void setCourt_status(String court_status) {
        this.court_status = court_status;
    }

    public String getClient_type() {
        return client_type;
    }

    public void setClient_type(String client_type) {
        this.client_type = client_type;
    }
}
