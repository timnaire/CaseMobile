package org.kidzonshock.acase.acase.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetCase {

    @SerializedName("cases")
    @Expose
    private List<CaseDetails> case_details = null;

    @SerializedName("client")
    @Expose
    private List<Client> client_details = null;

    public GetCase(List<CaseDetails> case_details, List<Client> client_details) {
        this.case_details = case_details;
        this.client_details = client_details;
    }

    public List<CaseDetails> getCase_details() {
        return case_details;
    }

    public void setCase_details(List<CaseDetails> case_details) {
        this.case_details = case_details;
    }

    public List<Client> getClient_details() {
        return client_details;
    }

    public void setClient_details(List<Client> client_details) {
        this.client_details = client_details;
    }
}
