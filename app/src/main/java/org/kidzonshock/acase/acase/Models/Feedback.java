package org.kidzonshock.acase.acase.Models;

import com.google.gson.annotations.SerializedName;

public class Feedback {

    @SerializedName("lawyer_id")
    private String lawyer_id;
    @SerializedName("rating")
    private Float rating;
    @SerializedName("feedback")
    private String feedback;

    public Feedback(String lawyer_id, Float rating, String feedback) {
        this.lawyer_id = lawyer_id;
        this.rating = rating;
        this.feedback = feedback;
    }

    public String getLawyer_id() {
        return lawyer_id;
    }

    public void setLawyer_id(String lawyer_id) {
        this.lawyer_id = lawyer_id;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
