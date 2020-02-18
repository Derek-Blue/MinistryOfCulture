package com.example.ministryofculture.Model;

public class Remain {

    private String imageUri;
    private String casename;
    private String stylename;
    private String address;
    private String intro;

    public Remain(String imageUri, String casename, String stylename, String address, String intro) {
        this.imageUri = imageUri;
        this.casename = casename;
        this.stylename = stylename;
        this.address = address;
        this.intro = intro;
    }

    public Remain() {
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getCasename() {
        return casename;
    }

    public void setCasename(String casename) {
        this.casename = casename;
    }

    public String getStylename() {
        return stylename;
    }

    public void setStylename(String stylename) {
        this.stylename = stylename;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
