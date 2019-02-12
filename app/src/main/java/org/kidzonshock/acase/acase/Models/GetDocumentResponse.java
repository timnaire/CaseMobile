package org.kidzonshock.acase.acase.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GetDocumentResponse {

    @SerializedName("error")
    @Expose
    private boolean error;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("list_files")
    @Expose
    private ArrayList<Document> file;

    public GetDocumentResponse(boolean error, String message, ArrayList<Document> file) {
        this.error = error;
        this.message = message;
        this.file = file;
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

    public ArrayList<Document> getFile() {
        return file;
    }

    public void setFile(ArrayList<Document> file) {
        this.file = file;
    }
}
