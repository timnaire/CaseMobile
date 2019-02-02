package org.kidzonshock.acase.acase.Models;

public class CaseModel {

    private String title;
    private String clientname;
    private String date_created;
    private String status;

    public CaseModel(String title, String clientname, String date_created, String status) {
        this.title = title;
        this.clientname = clientname;
        this.date_created = date_created;
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getClientname() {
        return clientname;
    }

    public void setClientname(String clientid) {
        this.clientname = clientid;
    }

    public String getDate_created() {
        return date_created;
    }

    public void setDate_created(String date_created) {
        this.date_created = date_created;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
