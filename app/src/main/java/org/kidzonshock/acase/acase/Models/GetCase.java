package org.kidzonshock.acase.acase.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GetCase {

    @SerializedName("error")
    @Expose
    private boolean error;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("cases")
    @Expose
    private ArrayList<Cases> cases = null;

    public GetCase(boolean error, String message, ArrayList<Cases> cases) {
        this.error = error;
        this.message = message;
        this.cases = cases;
    }

    public boolean isError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public ArrayList<Cases> getCases() {
        return cases;
    }
}
