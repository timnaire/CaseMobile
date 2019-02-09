package org.kidzonshock.acase.acase.Models;

public class ClientModel {

    private String name;
    private String email;
    private String phone;
    private String address;
    private String profile_pic;

    public ClientModel(String name, String email, String phone, String address, String profile_pic) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.profile_pic = profile_pic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
