package org.kidzonshock.acase.acase.Models;

public class PreAppoint {

    private Lawyer lawyer;
    private Client client;
    private String status;
    private String created;
    private String updated;

    public PreAppoint(Lawyer lawyer, Client client, String status, String created, String updated) {
        this.lawyer = lawyer;
        this.client = client;
        this.status = status;
        this.created = created;
        this.updated = updated;
    }

    public Lawyer getLawyer() {
        return lawyer;
    }

    public void setLawyer(Lawyer lawyer) {
        this.lawyer = lawyer;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }
}
