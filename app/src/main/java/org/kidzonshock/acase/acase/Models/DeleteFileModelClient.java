package org.kidzonshock.acase.acase.Models;

import com.google.gson.annotations.SerializedName;

public class DeleteFileModelClient {

    @SerializedName("file_id")
    private String file_id;

    @SerializedName("uploaded_by")
    private String uploaded_by;

    public DeleteFileModelClient(String file_id, String uploaded_by) {
        this.file_id = file_id;
        this.uploaded_by = uploaded_by;
    }

    public String getFile_id() {
        return file_id;
    }

    public void setFile_id(String file_id) {
        this.file_id = file_id;
    }

    public String getUploaded_by() {
        return uploaded_by;
    }

    public void setUploaded_by(String uploaded_by) {
        this.uploaded_by = uploaded_by;
    }
}
