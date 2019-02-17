package org.kidzonshock.acase.acase.Models;

import com.google.gson.annotations.SerializedName;

public class PaymentModel {

    @SerializedName("payment_id")
    private String payment_id;

    @SerializedName("payment_method")
    private String payment_method;

    @SerializedName("payment_amount")
    private String payment_amount;

    public PaymentModel(String payment_id, String payment_method, String payment_amount) {
        this.payment_id = payment_id;
        this.payment_method = payment_method;
        this.payment_amount = payment_amount;
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
