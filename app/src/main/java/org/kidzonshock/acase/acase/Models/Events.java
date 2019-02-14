package org.kidzonshock.acase.acase.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Events {

    @SerializedName("event_id")
    @Expose
    private String event_id;

    @SerializedName("client")
    @Expose
    private Client client;

    @SerializedName("lawyer")
    @Expose
    private Lawyer lawyer;

    @SerializedName("event_title")
    @Expose
    private String eventTitle;
    @SerializedName("event_location")
    @Expose
    private String eventLocation;
    @SerializedName("event_details")
    @Expose
    private String eventDetails;
    @SerializedName("event_date")
    @Expose
    private String eventDate;
    @SerializedName("event_time")
    @Expose
    private String eventTime;
    @SerializedName("event_type")
    @Expose
    private String eventType;

    public Events(String event_id,Client client, Lawyer lawyer, String eventTitle, String eventLocation, String eventDetails, String eventDate, String eventTime, String eventType) {
        this.event_id = event_id;
        this.client = client;
        this.lawyer = lawyer;
        this.eventTitle = eventTitle;
        this.eventLocation = eventLocation;
        this.eventDetails = eventDetails;
        this.eventDate = eventDate;
        this.eventTime = eventTime;
        this.eventType = eventType;
    }

    public String getEvent_id() {
        return event_id;
    }

    public void setEvent_id(String event_id) {
        this.event_id = event_id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Lawyer getLawyer() {
        return lawyer;
    }

    public void setLawyer(Lawyer lawyer) {
        this.lawyer = lawyer;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }

    public String getEventDetails() {
        return eventDetails;
    }

    public void setEventDetails(String eventDetails) {
        this.eventDetails = eventDetails;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }
}
