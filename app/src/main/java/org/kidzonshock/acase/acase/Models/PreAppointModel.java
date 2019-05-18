package org.kidzonshock.acase.acase.Models;

public class PreAppointModel {
    private String pid;
    private String client_id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String profile_pic;

    public PreAppointModel(String pid, String client_id, String name, String email, String phone, String address, String profile_pic) {
        this.pid = pid;
        this.client_id = client_id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.profile_pic = profile_pic;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
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
