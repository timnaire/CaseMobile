package org.kidzonshock.acase.acase.Models;

import com.google.gson.annotations.SerializedName;

public class ClientType {

    @SerializedName("client_type")
    private String client_type;

    public ClientType(String client_type) {
        this.client_type = client_type;
    }

    public String getClient_type() {
        return client_type;
    }

    public void setClient_type(String client_type) {
        this.client_type = client_type;
    }
}
