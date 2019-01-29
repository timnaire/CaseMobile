package org.kidzonshock.acase.acase.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetLawPractice {
    @SerializedName("practice")
    @Expose
    private List<LawPractice> practice = null;

    public GetLawPractice(List<LawPractice> practice) {
        this.practice = practice;
    }

    public List<LawPractice> getPractice() {
        return practice;
    }
}
