package org.kidzonshock.acase.acase.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LawPractice {
    @SerializedName("law_practice")
    @Expose
    private String law_practice;

    public LawPractice(String law_practice) {
        this.law_practice = law_practice;
    }

    public String getLaw_practice() {
        return law_practice;
    }
}
