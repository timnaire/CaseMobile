package org.kidzonshock.acase.acase.Models;

public class Relation {

    private String relation_id;
    private Client client;
    private String client_id;

    public Relation(String relation_id, Client client, String client_id) {
        this.relation_id = relation_id;
        this.client = client;
        this.client_id = client_id;
    }

    public String getRelation_id() {
        return relation_id;
    }

    public void setRelation_id(String relation_id) {
        this.relation_id = relation_id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }
}
