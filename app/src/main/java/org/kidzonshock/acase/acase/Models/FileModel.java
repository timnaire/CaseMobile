package org.kidzonshock.acase.acase.Models;

public class FileModel {
    private int img;
    private String filename;

    public FileModel(int img, String filename) {
        this.img = img;
        this.filename = filename;
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
}
