package org.kidzonshock.acase.acase.Models;

public class SigninResponseClient {

    private boolean error;
    private String message, client, first_name, last_name, email,sex, phone, address, profile_pic;

    public SigninResponseClient(boolean error, String message, String client, String first_name, String last_name, String email, String sex, String phone, String address, String profile_pic) {
        this.error = error;
        this.message = message;
        this.client = client;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.sex = sex;
        this.phone = phone;
        this.address = address;
        this.profile_pic = profile_pic;
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

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }
}
