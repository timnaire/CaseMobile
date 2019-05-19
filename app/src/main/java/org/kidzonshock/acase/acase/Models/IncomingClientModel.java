package org.kidzonshock.acase.acase.Models;

public class IncomingClientModel {

    private String status;
    private String client_id;
    private String preappoint_id;

    public IncomingClientModel(String status, String client_id, String preappoint_id) {
        this.status = status;
        this.client_id = client_id;
        this.preappoint_id = preappoint_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getPreappoint_id() {
        return preappoint_id;
    }

    public void setPreappoint_id(String preappoint_id) {
        this.preappoint_id = preappoint_id;
    }
}
