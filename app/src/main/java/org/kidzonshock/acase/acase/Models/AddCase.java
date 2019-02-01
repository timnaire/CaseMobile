package org.kidzonshock.acase.acase.Models;

import com.google.gson.annotations.SerializedName;

public class AddCase {

    @SerializedName("case_title")
    private String case_title;

    @SerializedName("client_id")
    private String client_id;

    @SerializedName("case_description")
    private String case_description;

    public AddCase(String case_title, String client_id, String case_description) {
        this.case_title = case_title;
        this.client_id = client_id;
        this.case_description = case_description;
    }

    public String getCase_title() {
        return case_title;
    }

    public void setCase_title(String case_title) {
        this.case_title = case_title;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getCase_description() {
        return case_description;
    }

    public void setCase_description(String case_description) {
        this.case_description = case_description;
    }
}
