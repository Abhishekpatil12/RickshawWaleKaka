package com.example.demo1;

public class RequestUser {

    String name;
    String child;
    String status;
    String profilephoto;
    String phoneno;
    String schoolname;

    public RequestUser()
    {

    }

    public RequestUser(String name, String child, String status, String profilephoto, String phoneno, String schoolname) {
        this.name = name;
        this.child = child;
        this.status = status;
        this.profilephoto = profilephoto;
        this.phoneno = phoneno;
        this.schoolname = schoolname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChild() {
        return child;
    }

    public void setChild(String child) {
        this.child = child;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProfilephoto() {
        return profilephoto;
    }

    public void setProfilephoto(String profilephoto) {
        this.profilephoto = profilephoto;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public String getSchoolname() {
        return schoolname;
    }

    public void setSchoolname(String schoolname) {
        this.schoolname = schoolname;
    }
}
