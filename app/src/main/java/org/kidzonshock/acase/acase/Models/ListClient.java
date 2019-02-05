package org.kidzonshock.acase.acase.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ListClient {

    @SerializedName("error")
    @Expose
    private boolean error;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("cases")
    @Expose
    private ArrayList<LawyerListCase> lawyerListCases = null;

    public ListClient(boolean error, String message, ArrayList<LawyerListCase> lawyerListCases) {
        this.error = error;
        this.message = message;
        this.lawyerListCases = lawyerListCases;
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

    public ArrayList<LawyerListCase> getLawyerListCases() {
        return lawyerListCases;
    }

    public void setLawyerListCases(ArrayList<LawyerListCase> lawyerListCases) {
        this.lawyerListCases = lawyerListCases;
    }
}
