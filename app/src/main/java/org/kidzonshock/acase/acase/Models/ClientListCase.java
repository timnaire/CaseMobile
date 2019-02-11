package org.kidzonshock.acase.acase.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClientListCase {

    @SerializedName("lawyer_id")
    @Expose
    private String lawyer_id;

    @SerializedName("lawyer")
    @Expose
    private Lawyer lawyer;

    public ClientListCase(String lawyer_id, Lawyer lawyer) {
        this.lawyer_id = lawyer_id;
        this.lawyer = lawyer;
    }

    public String getLawyer_id() {
        return lawyer_id;
    }

    public void setLawyer_id(String lawyer_id) {
        this.lawyer_id = lawyer_id;
    }

    public Lawyer getLawyer() {
        return lawyer;
    }

    public void setLawyer(Lawyer lawyer) {
        this.lawyer = lawyer;
    }
}
