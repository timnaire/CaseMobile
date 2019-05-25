package org.kidzonshock.acase.acase.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Cases {

    @SerializedName("case_id")
    @Expose
    private String case_id;

    @SerializedName("case_title")
    @Expose
    private String case_title;

    @SerializedName("client_id")
    @Expose
    private String client_id;

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

    @SerializedName("remarks")
    @Expose
    private String remarks;

    @SerializedName("court_status")
    @Expose
    private String court_status;

    @SerializedName("client_type")
    @Expose
    private String client_type;

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
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
