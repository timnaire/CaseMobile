package org.kidzonshock.acase.acase.Models;

import com.google.gson.annotations.SerializedName;

public class AddFCMToken {

    @SerializedName("fcm_token")
    private String token;

    public AddFCMToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
