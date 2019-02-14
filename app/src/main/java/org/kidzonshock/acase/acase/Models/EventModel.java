package org.kidzonshock.acase.acase.Models;

public class EventModel {

    private String event_id;

    private String eventWith;
    private String eventTitle;
    private String eventDate;

    //  client
    private String clientName;
    private String clientEmail;
    private String clientPhone;
    private String clientAddress;

    //  lawyer
    private String lawyerName;
    private String lawyyerEmail;
    private String lawyerPhone;
    private String lawyerOffice;

    public EventModel(String event_id,String eventWith,String eventTitle, String eventDate, String clientName, String clientEmail, String clientPhone, String clientAddress, String lawyerName, String lawyyerEmail, String lawyerPhone, String lawyerOffice) {
        this.event_id = event_id;
        this.eventWith = eventWith;
        this.eventTitle = eventTitle;
        this.eventDate = eventDate;
        this.clientName = clientName;
        this.clientEmail = clientEmail;
        this.clientPhone = clientPhone;
        this.clientAddress = clientAddress;
        this.lawyerName = lawyerName;
        this.lawyyerEmail = lawyyerEmail;
        this.lawyerPhone = lawyerPhone;
        this.lawyerOffice = lawyerOffice;
    }

    public String getEvent_id() {
        return event_id;
    }

    public void setEvent_id(String event_id) {
        this.event_id = event_id;
    }

    public String getEventWith() {
        return eventWith;
    }

    public void setEventWith(String eventWith) {
        this.eventWith = eventWith;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
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
}
