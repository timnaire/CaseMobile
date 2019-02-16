package org.kidzonshock.acase.acase.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Document {

    @SerializedName("file_id")
    @Expose
    private String file_id;

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

    @SerializedName("uploaded_by")
    @Expose
    private String uploaded_by;

    public Document(String file_id, String caseFile, String fileName, String fileType, String filePrivacy, String uploaded_by) {
        this.file_id = file_id;
        this.caseFile = caseFile;
        this.fileName = fileName;
        this.fileType = fileType;
        this.filePrivacy = filePrivacy;
        this.uploaded_by = uploaded_by;
    }

    public String getFile_id() {
        return file_id;
    }

    public void setFile_id(String file_id) {
        this.file_id = file_id;
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

    public String getUploaded_by() {
        return uploaded_by;
    }

    public void setUploaded_by(String uploaded_by) {
        this.uploaded_by = uploaded_by;
    }
}
