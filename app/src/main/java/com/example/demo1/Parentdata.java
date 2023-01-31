package com.example.demo1;

public class Parentdata {

    String houseno;
    String society;
    String street;
    String pincode;
    String city;
    String district;
    String state;


    public String getHouseno() {
        return houseno;
    }

    public void setHouseno(String houseno) {
        this.houseno = houseno;
    }

    public String getSociety() {
        return society;
    }

    public void setSociety(String society) {
        this.society = society;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }



    public Parentdata(String houseno, String society, String street, String pincode, String city, String district, String state) {
        this.houseno = houseno;
        this.society = society;
        this.street = street;
        this.pincode = pincode;
        this.city = city;
        this.district = district;
        this.state = state;
    }

}
