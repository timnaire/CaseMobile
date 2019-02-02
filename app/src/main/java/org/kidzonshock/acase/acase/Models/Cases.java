package org.kidzonshock.acase.acase.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Cases {

    @SerializedName("case_title")
    @Expose
    private String case_title;

    @SerializedName("created")
    @Expose
    private String created;

    @SerializedName("client")
    @Expose
    private Client client;

    @SerializedName("lawyer")
    @Expose
    private Lawyer lawyer;

    @SerializedName("case_description")
    @Expose
    private String case_description;

    @SerializedName("case_status")
    @Expose
    private String case_status;

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

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Lawyer getLawyer() {
        return lawyer;
    }

    public void setLawyer(Lawyer lawyer) {
        this.lawyer = lawyer;
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
