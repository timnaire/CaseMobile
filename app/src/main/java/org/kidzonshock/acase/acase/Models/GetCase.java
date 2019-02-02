package org.kidzonshock.acase.acase.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GetCase {

    @SerializedName("cases")
    @Expose
    private ArrayList<Cases> cases = null;

    public ArrayList<Cases> getCases() {
        return cases;
    }

    public void setCases(ArrayList<Cases> cases) {
        this.cases = cases;
    }
}
