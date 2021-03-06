package org.kidzonshock.acase.acase.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class UpdateLawyerInfo {

    @SerializedName("first_name")
    private String first_name;
    @SerializedName("last_name")
    private String last_name;
    @SerializedName("phone")
    private String phone;
    @SerializedName("cityOrMunicipality")
    private String cityOrMunicipality;
    @SerializedName("office")
    private String office;
    @SerializedName("aboutme")
    private String aboutme;
    @SerializedName("firm")
    private String firm;
    @SerializedName("sex")
    private String sex;
    @SerializedName("law_practice")
    private ArrayList<String> law_practice;

    public UpdateLawyerInfo(String first_name, String last_name, String phone, String cityOrMunicipality, String office, String aboutme, String firm,String sex, ArrayList<String> law_practice) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.phone = phone;
        this.cityOrMunicipality = cityOrMunicipality;
        this.office = office;
        this.aboutme = aboutme;
        this.firm = firm;
        this.sex = sex;
        this.law_practice = law_practice;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getAboutme() {
        return aboutme;
    }

    public void setAboutme(String aboutme) {
        this.aboutme = aboutme;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getFirm() {
        return firm;
    }

    public void setFirm(String firm) {
        this.firm = firm;
    }

    public ArrayList<String> getLaw_practice() {
        return law_practice;
    }

    public void setLaw_practice(ArrayList<String> law_practice) {
        this.law_practice = law_practice;
    }
}
