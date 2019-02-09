package org.kidzonshock.acase.acase.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ListClient {

    @SerializedName("error")
    @Expose
    private boolean error;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("clients")
    @Expose
    private ArrayList<Client> list_clients = null;

    public ListClient(boolean error, String message, ArrayList<Client> list_clients) {
        this.error = error;
        this.message = message;
        this.list_clients = list_clients;
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

    public ArrayList<Client> getList_clients() {
        return list_clients;
    }

    public void setList_clients(ArrayList<Client> list_clients) {
        this.list_clients = list_clients;
    }
}
