package org.kidzonshock.acase.acase.Models;

public class CaseModel {

//  case
    private String title;
    private String clientName;
    private String date_created;
    private String status;

//  client
    private String clientEmail;
    private String clientPhone;
    private String clientAddress;

//  lawyer
    private String lawyerName;
    private String lawyyerEmail;
    private String lawyerPhone;
    private String lawyerOffice;

    public CaseModel(String title, String clientName, String date_created, String status, String clientEmail, String clientPhone, String clientAddress, String lawyerName, String lawyyerEmail, String lawyerPhone, String lawyerOffice) {
        this.title = title;
        this.clientName = clientName;
        this.date_created = date_created;
        this.status = status;
        this.clientEmail = clientEmail;
        this.clientPhone = clientPhone;
        this.clientAddress = clientAddress;
        this.lawyerName = lawyerName;
        this.lawyyerEmail = lawyyerEmail;
        this.lawyerPhone = lawyerPhone;
        this.lawyerOffice = lawyerOffice;
    }

    public String getTitle() {
        return title;
    }

    public String getClientName() {
        return clientName;
    }

    public String getDate_created() {
        return date_created;
    }

    public String getStatus() {
        return status;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public String getClientPhone() {
        return clientPhone;
    }

    public String getClientAddress() {
        return clientAddress;
    }

    public String getLawyerName() {
        return lawyerName;
    }

    public String getLawyyerEmail() {
        return lawyyerEmail;
    }

    public String getLawyerPhone() {
        return lawyerPhone;
    }

    public String getLawyerOffice() {
        return lawyerOffice;
    }
}
