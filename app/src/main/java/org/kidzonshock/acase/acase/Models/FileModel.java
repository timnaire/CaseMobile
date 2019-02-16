package org.kidzonshock.acase.acase.Models;

public class FileModel {
    private String fileId;
    private int img;
    private String filename;
    private String uploadedBy;

    public FileModel(String fileId, int img, String filename, String uploadedBy) {
        this.fileId = fileId;
        this.img = img;
        this.filename = filename;
        this.uploadedBy = uploadedBy;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getUploadedBy() {
        return uploadedBy;
    }

    public void setUploadedBy(String uploadedBy) {
        this.uploadedBy = uploadedBy;
    }
}
