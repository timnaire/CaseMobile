package org.kidzonshock.acase.acase.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Document {

    @SerializedName("case_file")
    @Expose
    private String filename;

    @SerializedName("file_type")
    @Expose
    private String fileType;

    @SerializedName("file_privacy")
    @Expose
    private String filePrivacy;

    public Document(String filename, String fileType, String filePrivacy) {
        this.filename = filename;
        this.fileType = fileType;
        this.filePrivacy = filePrivacy;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFilePrivacy() {
        return filePrivacy;
    }

    public void setFilePrivacy(String filePrivacy) {
        this.filePrivacy = filePrivacy;
    }
}
