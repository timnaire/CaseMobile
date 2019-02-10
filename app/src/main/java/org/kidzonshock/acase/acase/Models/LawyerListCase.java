package org.kidzonshock.acase.acase.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LawyerListCase {

    @SerializedName("client_id")
    @Expose
    private String client_id;

    @SerializedName("client")
    @Expose
    private Client client;

    public LawyerListCase(String client_id, Client client) {
        this.client_id = client_id;
        this.client = client;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}