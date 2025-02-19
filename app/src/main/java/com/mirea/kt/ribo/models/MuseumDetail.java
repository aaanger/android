package com.mirea.kt.ribo.models;

public class MuseumDetail {
    String name;
    int image;
    String address;
    String phone;
    String website;

    public MuseumDetail(String name, int image, String address, String phone, String website) {
        this.name = name;
        this.image = image;
        this.address = address;
        this.phone = phone;
        this.website = website;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getWebsite() {
        return website;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setWebsite(String website) {
        this.website = website;
    }
}
