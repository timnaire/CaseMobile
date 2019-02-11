package org.kidzonshock.acase.acase.Models;

public class SigninResponseLawyer {

    private boolean error;
    private String message, lawyer, first_name, last_name, email, phone, cityOrMunicipality, office, profile_pic, aboutme, firm, sex;

    public SigninResponseLawyer(boolean error, String message, String lawyer, String first_name, String last_name, String email, String phone, String cityOrMunicipality, String office, String profile_pic, String aboutme, String firm, String sex) {
        this.error = error;
        this.message = message;
        this.lawyer = lawyer;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.phone = phone;
        this.cityOrMunicipality = cityOrMunicipality;
        this.office = office;
        this.profile_pic = profile_pic;
        this.aboutme = aboutme;
        this.firm = firm;
        this.sex = sex;
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

    public String getLawyer() {
        return lawyer;
    }

    public void setLawyer(String lawyer) {
        this.lawyer = lawyer;
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

    public String getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }

    public String getAboutme() {
        return aboutme;
    }

    public void setAboutme(String aboutme) {
        this.aboutme = aboutme;
    }

    public String getFirm() {
        return firm;
    }

    public void setFirm(String firm) {
        this.firm = firm;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
