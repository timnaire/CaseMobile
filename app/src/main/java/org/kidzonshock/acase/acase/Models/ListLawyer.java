package org.kidzonshock.acase.acase.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ListLawyer {
    @SerializedName("error")
    @Expose
    private boolean error;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("lawyers")
    @Expose
    private ArrayList<ClientListCase> list_lawyers = null;

    @SerializedName("feedbacks")
    @Expose
    private ArrayList<Feedbacks> feedbacks = null;

    public ListLawyer(boolean error, String message, ArrayList<ClientListCase> list_lawyers, ArrayList<Feedbacks> feedbacks) {
        this.error = error;
        this.message = message;
        this.list_lawyers = list_lawyers;
        this.feedbacks = feedbacks;
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

    public ArrayList<ClientListCase> getList_lawyers() {
        return list_lawyers;
    }

    public void setList_lawyers(ArrayList<ClientListCase> list_lawyers) {
        this.list_lawyers = list_lawyers;
    }

    public ArrayList<Feedbacks> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(ArrayList<Feedbacks> feedbacks) {
        this.feedbacks = feedbacks;
    }
}
