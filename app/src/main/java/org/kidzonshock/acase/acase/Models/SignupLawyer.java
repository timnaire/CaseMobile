package org.kidzonshock.acase.acase.Models;

import com.google.gson.annotations.SerializedName;

public class SignupLawyer {

    @SerializedName("first_name")
    private String firstname;
    @SerializedName("last_name")
    private String lastname;
    @SerializedName("email")
    private String email;
    @SerializedName("phone")
    private String phone;
    @SerializedName("rollno")
    private String rollno;
    @SerializedName("sex")
    private String sex;
    @SerializedName("cityOrMunicipality")
    private String cityOrMunicipality;
    @SerializedName("office")
    private String office;
    @SerializedName("law_practice")
    private String lawpractice;
    @SerializedName("password")
    private String password;
    @SerializedName("confirm")
    private String confirm;

    public SignupLawyer(String firstname, String lastname, String email, String phone, String rollno, String sex, String cityOrMunicipality, String office, String lawpractice, String password, String confirm) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phone = phone;
        this.rollno = rollno;
        this.sex = sex;
        this.cityOrMunicipality = cityOrMunicipality;
        this.office = office;
        this.lawpractice = lawpractice;
        this.password = password;
        this.confirm = confirm;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
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

    public String getRollno() {
        return rollno;
    }

    public void setRollno(String rollno) {
        this.rollno = rollno;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getCityOrMunicipality() {
        return cityOrMunicipality;
    }

    public void setCityOrMunicipality(String cityOrMunicipality) {
        this.cityOrMunicipality = cityOrMunicipality;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public String getLawpractice() {
        return lawpractice;
    }

    public void setLawpractice(String lawpractice) {
        this.lawpractice = lawpractice;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }
}
