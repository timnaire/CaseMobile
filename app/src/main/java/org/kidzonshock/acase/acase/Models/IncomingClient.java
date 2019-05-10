package org.kidzonshock.acase.acase.Models;

import java.util.ArrayList;

public class IncomingClient {

    private boolean error;
    private String message;
    private ArrayList<PreAppoint> preappoints;

    public IncomingClient(boolean error, String message, ArrayList<PreAppoint> preappoints) {
        this.error = error;
        this.message = message;
        this.preappoints = preappoints;
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

    public ArrayList<PreAppoint> getPreappoints() {
        return preappoints;
    }

    public void setPreappoints(ArrayList<PreAppoint> preappoints) {
        this.preappoints = preappoints;
    }

}
