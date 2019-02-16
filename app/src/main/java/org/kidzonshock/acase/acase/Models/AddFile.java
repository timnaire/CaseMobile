package org.kidzonshock.acase.acase.Models;

import com.google.gson.annotations.SerializedName;

public class AddFile {

    @SerializedName("case")
    private String case_id;

    @SerializedName("case_file")
    private String case_file;

    @SerializedName("file_name")
    private String file_name;

    @SerializedName("file_privacy")
    private String file_privacy;

    @SerializedName("uploaded_by")
    private String uploaded_by;

    public AddFile(String case_id, String case_file, String file_name, String file_privacy, String uploaded_by) {
        this.case_id = case_id;
        this.case_file = case_file;
        this.file_name = file_name;
        this.file_privacy = file_privacy;
        this.uploaded_by = uploaded_by;
    }

    public String getCase_id() {
        return case_id;
    }

    public void setCase_id(String case_id) {
        this.case_id = case_id;
    }

    public String getCase_file() {
        return case_file;
    }

    public void setCase_file(String case_file) {
        this.case_file = case_file;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public String getFile_privacy() {
        return file_privacy;
    }

    public void setFile_privacy(String file_privacy) {
        this.file_privacy = file_privacy;
    }

    public String getUploaded_by() {
        return uploaded_by;
    }

    public void setUploaded_by(String uploaded_by) {
        this.uploaded_by = uploaded_by;
    }
}
