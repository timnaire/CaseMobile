package org.kidzonshock.acase.acase.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class EventResponse {

    @SerializedName("error")
    @Expose
    private boolean error;

    @SerializedName("events")
    @Expose
    private ArrayList<Events> events;

    @SerializedName("message")
    @Expose
    private String message;

    public EventResponse(boolean error, ArrayList<Events> events, String message) {
        this.error = error;
        this.events = events;
        this.message = message;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public ArrayList<Events> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<Events> events) {
        this.events = events;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
