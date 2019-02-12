package org.kidzonshock.acase.acase.Models;

import com.google.gson.annotations.SerializedName;

public class AddFile {

    @SerializedName("case")
    private String case_id;

    @SerializedName("case_file")
    private String case_file;

    @SerializedName("file_privacy")
    private String file_privacy;

    public AddFile(String case_id, String case_file, String file_privacy) {
        this.case_id = case_id;
        this.case_file = case_file;
        this.file_privacy = file_privacy;
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

    public String getFile_privacy() {
        return file_privacy;
    }

    public void setFile_privacy(String file_privacy) {
        this.file_privacy = file_privacy;
    }
}
