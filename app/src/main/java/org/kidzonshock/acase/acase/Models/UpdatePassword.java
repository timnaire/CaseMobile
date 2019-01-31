package org.kidzonshock.acase.acase.Models;

public class UpdatePassword {

    private String current;
    private String newpass;
    private String confirm;

    public UpdatePassword(String current, String newpass, String confirm) {
        this.current = current;
        this.newpass = newpass;
        this.confirm = confirm;
    }

    public String getCurrent() {
        return current;
    }

    public void setCurrent(String current) {
        this.current = current;
    }

    public String getNewpass() {
        return newpass;
    }

    public void setNewpass(String newpass) {
        this.newpass = newpass;
    }

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }
}
