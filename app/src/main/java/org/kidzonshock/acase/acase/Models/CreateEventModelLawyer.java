package org.kidzonshock.acase.acase.Models;

import com.google.gson.annotations.SerializedName;

public class CreateEventModelLawyer {

    @SerializedName("client_id")
    private String client_id;

    @SerializedName("event_title")
    private String event_title;

    @SerializedName("event_location")
    private String event_location;

    @SerializedName("event_details")
    private String event_details;

    @SerializedName("event_date")
    private String event_date;

    @SerializedName("event_time")
    private String event_time;

    @SerializedName("event_type")
    private String event_type;

    public CreateEventModelLawyer(String client_id, String event_title, String event_location, String event_details, String event_date, String event_time, String event_type) {
        this.client_id = client_id;
        this.event_title = event_title;
        this.event_location = event_location;
        this.event_details = event_details;
        this.event_date = event_date;
        this.event_time = event_time;
        this.event_type = event_type;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getEvent_title() {
        return event_title;
    }

    public void setEvent_title(String event_title) {
        this.event_title = event_title;
    }

    public String getEvent_location() {
        return event_location;
    }

    public void setEvent_location(String event_location) {
        this.event_location = event_location;
    }

    public String getEvent_details() {
        return event_details;
    }

    public void setEvent_details(String event_details) {
        this.event_details = event_details;
    }

    public String getEvent_date() {
        return event_date;
    }

    public void setEvent_date(String event_date) {
        this.event_date = event_date;
    }

    public String getEvent_time() {
        return event_time;
    }

    public void setEvent_time(String event_time) {
        this.event_time = event_time;
    }

    public String getEvent_type() {
        return event_type;
    }

    public void setEvent_type(String event_type) {
        this.event_type = event_type;
    }
}
