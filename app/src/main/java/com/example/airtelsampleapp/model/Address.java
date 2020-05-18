package com.example.airtelsampleapp.model;

public class Address {
    private String id;
    private String city;
    private String address;

    public void setId(String id){
        this.id = id;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }
}
