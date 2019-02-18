package org.kidzonshock.acase.acase.Models;

import com.google.gson.annotations.SerializedName;

public class ClientPaymentModel {

    @SerializedName("lawyer_id")
    private String lawyer_id;

    @SerializedName("payment_id")
    private String payment_id;

    @SerializedName("payment_method")
    private String payment_method;

    @SerializedName("payment_amount")
    private String payment_amount;


    public ClientPaymentModel(String lawyer_id, String payment_id, String payment_method, String payment_amount) {
        this.lawyer_id = lawyer_id;
        this.payment_id = payment_id;
        this.payment_method = payment_method;
        this.payment_amount = payment_amount;
    }

    public String getLawyer_id() {
        return lawyer_id;
    }

    public void setLawyer_id(String lawyer_id) {
        this.lawyer_id = lawyer_id;
    }

    public String getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(String payment_id) {
        this.payment_id = payment_id;
    }

    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }

    public String getPayment_amount() {
        return payment_amount;
    }

    public void setPayment_amount(String payment_amount) {
        this.payment_amount = payment_amount;
    }
}
