package org.kidzonshock.acase.acase.Models;

import com.google.gson.annotations.SerializedName;

public class UpdatePicture {

    @SerializedName("profile_pic")
    private String uri;

    public UpdatePicture(String uri) {
        this.uri = uri;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
