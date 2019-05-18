package org.kidzonshock.acase.acase.Models;

public class Feedbacks {

    private String feedback_id;
    private Lawyer lawyer;
    private Client client;
    private String rating;
    private String feedback;
    private String created;
    private String updated;

    public Feedbacks(String feedback_id, Lawyer lawyer, Client client, String rating, String feedback, String created, String updated) {
        this.feedback_id = feedback_id;
        this.lawyer = lawyer;
        this.client = client;
        this.rating = rating;
        this.feedback = feedback;
        this.created = created;
        this.updated = updated;
    }

    public String getFeedback_id() {
        return feedback_id;
    }

    public void setFeedback_id(String feedback_id) {
        this.feedback_id = feedback_id;
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

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
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
