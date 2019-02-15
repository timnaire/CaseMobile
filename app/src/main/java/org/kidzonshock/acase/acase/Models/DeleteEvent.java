package org.kidzonshock.acase.acase.Models;

import com.google.gson.annotations.SerializedName;

public class DeleteEvent {

    @SerializedName("event_id")
    private String event_id;

    public DeleteEvent(String event_id) {
        this.event_id = event_id;
    }

    public String getEvent_id() {
        return event_id;
    }

    public void setEvent_id(String event_id) {
        this.event_id = event_id;
    }
}
