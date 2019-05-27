package org.kidzonshock.acase.acase.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ClientTypeResponse {

    @SerializedName("error")
    private boolean error;
    @SerializedName("message")
    private String message;
    @SerializedName("client_type")
    private ArrayList<ClientType> client_type;

    public ClientTypeResponse(boolean error, String message, ArrayList<ClientType> client_type) {
        this.error = error;
        this.message = message;
        this.client_type = client_type;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<ClientType> getClient_type() {
        return client_type;
    }

    public void setClient_type(ArrayList<ClientType> client_type) {
        this.client_type = client_type;
    }
}
