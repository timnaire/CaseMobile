package org.kidzonshock.acase.acase.Models;

import com.google.gson.annotations.SerializedName;

public class DeleteFileModel {
    @SerializedName("file_id")
    private String file_id;

    public DeleteFileModel(String file_id) {
        this.file_id = file_id;
    }

    public String getFile_id() {
        return file_id;
    }

    public void setFile_id(String file_id) {
        this.file_id = file_id;
    }
}
