package org.kidzonshock.acase.acase.Models;

public class LawyerModel {
    private String lawyer_id;
    private String name;
    private String email;
    private String phone;
    private String office;
    private String profile_pic;
    private String fid;

    public LawyerModel(String lawyer_id,String name, String email, String phone, String office, String profile_pic) {
        this.lawyer_id = lawyer_id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.office = office;
        this.profile_pic = profile_pic;
    }

    public LawyerModel(String lawyer_id,String name, String email, String phone, String office, String profile_pic, String fid) {
        this.lawyer_id = lawyer_id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.office = office;
        this.profile_pic = profile_pic;
        this.fid = fid;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getLawyer_id() {
        return lawyer_id;
    }

    public void setLawyer_id(String lawyer_id) {
        this.lawyer_id = lawyer_id;
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

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public String getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }
}
