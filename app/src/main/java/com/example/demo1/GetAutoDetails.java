package com.example.demo1;

public class GetAutoDetails {

    String name,age,email,ProfilePhoto,mobileNo;

    GetAutoDetails()
    {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfilePhoto() {
        return ProfilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        ProfilePhoto = profilePhoto;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public GetAutoDetails(String name, String age, String email, String profilePhoto, String mobileNo) {
        this.name = name;
        this.age = age;
        this.email = email;
        ProfilePhoto = profilePhoto;
        this.mobileNo = mobileNo;
    }
}
