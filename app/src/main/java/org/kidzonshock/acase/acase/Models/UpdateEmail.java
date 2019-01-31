package org.kidzonshock.acase.acase.Models;

import com.google.gson.annotations.SerializedName;

public class UpdateEmail {

    @SerializedName("current")
    private String current;
    @SerializedName("new_email")
    private String newemail;
    @SerializedName("password")
    private String password;

    public UpdateEmail(String current, String newemail, String password) {
        this.current = current;
        this.newemail = newemail;
        this.password = password;
    }

    public String getCurrent() {
        return current;
    }

    public void setCurrent(String current) {
        this.current = current;
    }

    public String getNewemail() {
        return newemail;
    }

    public void setNewemail(String newemail) {
        this.newemail = newemail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
