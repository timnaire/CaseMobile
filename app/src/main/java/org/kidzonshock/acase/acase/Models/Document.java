package org.kidzonshock.acase.acase.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Document {

    @SerializedName("case_file")
    @Expose
    private String caseFile;

    @SerializedName("file_name")
    @Expose
    private String fileName;

    @SerializedName("file_type")
    @Expose
    private String fileType;

    @SerializedName("file_privacy")
    @Expose
    private String filePrivacy;

    public Document(String caseFile, String fileName, String fileType, String filePrivacy) {
        this.caseFile = caseFile;
        this.fileName = fileName;
        this.fileType = fileType;
        this.filePrivacy = filePrivacy;
    }

    public String getCaseFile() {
        return caseFile;
    }

    public void setCaseFile(String caseFile) {
        this.caseFile = caseFile;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
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
