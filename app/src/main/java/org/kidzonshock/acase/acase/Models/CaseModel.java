package org.kidzonshock.acase.acase.Models;

public class CaseModel {

//  case
    private String case_id;
    private String client_id;
    private String title;
    private String clientName;
    private String date_created;
    private String case_description;
    private String status;
    private String remarks;

//  client
    private String clientEmail;
    private String clientPhone;
    private String clientAddress;

//  lawyer
    private String lawyerName;
    private String lawyyerEmail;
    private String lawyerPhone;
    private String lawyerOffice;

    public CaseModel(String case_id,String client_id,String title, String clientName, String date_created, String case_description, String status, String remarks, String clientEmail, String clientPhone, String clientAddress, String lawyerName, String lawyyerEmail, String lawyerPhone, String lawyerOffice) {
        this.case_id = case_id;
        this.client_id = client_id;
        this.title = title;
        this.clientName = clientName;
        this.date_created = date_created;
        this.case_description = case_description;
        this.status = status;
        this.remarks = remarks;
        this.clientEmail = clientEmail;
        this.clientPhone = clientPhone;
        this.clientAddress = clientAddress;
        this.lawyerName = lawyerName;
        this.lawyyerEmail = lawyyerEmail;
        this.lawyerPhone = lawyerPhone;
        this.lawyerOffice = lawyerOffice;
    }

    public String getCase_id() {
        return case_id;
    }

    public void setCase_id(String case_id) {
        this.case_id = case_id;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getDate_created() {
        return date_created;
    }

    public void setDate_created(String date_created) {
        this.date_created = date_created;
    }

    public String getCase_description() {
        return case_description;
    }

    public void setCase_description(String case_description) {
        this.case_description = case_description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    public String getClientPhone() {
        return clientPhone;
    }

    public void setClientPhone(String clientPhone) {
        this.clientPhone = clientPhone;
    }

    public String getClientAddress() {
        return clientAddress;
    }

    public void setClientAddress(String clientAddress) {
        this.clientAddress = clientAddress;
    }

    public String getLawyerName() {
        return lawyerName;
    }

    public void setLawyerName(String lawyerName) {
        this.lawyerName = lawyerName;
    }

    public String getLawyyerEmail() {
        return lawyyerEmail;
    }

    public void setLawyyerEmail(String lawyyerEmail) {
        this.lawyyerEmail = lawyyerEmail;
    }

    public String getLawyerPhone() {
        return lawyerPhone;
    }

    public void setLawyerPhone(String lawyerPhone) {
        this.lawyerPhone = lawyerPhone;
    }

    public String getLawyerOffice() {
        return lawyerOffice;
    }

    public void setLawyerOffice(String lawyerOffice) {
        this.lawyerOffice = lawyerOffice;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
