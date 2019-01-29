package org.kidzonshock.acase.acase.Models;

public class SigninResponse {

    private boolean error;
    private String message, lawyer, first_name, last_name, email, phone, cityOrMunicipality, office, profile_pic, aboutme;

    public SigninResponse(boolean error, String message, String lawyer, String first_name, String last_name, String email, String phone, String cityOrMunicipality, String office, String profile_pic, String aboutme) {
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
    }

    public boolean isError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public String getLawyer() {
        return lawyer;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getCityOrMunicipality() {
        return cityOrMunicipality;
    }

    public String getOffice() {
        return office;
    }

    public String getProfile_pic() {
        return profile_pic;
    }

    public String getAboutme() { return aboutme; }
}
