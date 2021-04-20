package com.example.project;

public class Book {
    public String btname;
    public String bbranch;
    public String byear;
    public String bdiv;
    public String bname;
    public String bemail;
    public String btopic;
    public String location;
    public String url;

    public Book() {
    }

    public Book(String btname,String bemail, String bbranch, String byear, String bdiv, String bname, String btopic, String location, String url) {
        this.btname = btname;
        this.bemail = bemail;
        this.bbranch = bbranch;
        this.byear = byear;
        this.bdiv = bdiv;
        this.bname = bname;
        this.btopic = btopic;
        this.location = location;
        this.url = url;
    }

    public String getBtname() {
        return btname;
    }

    public void setBtname(String btname) {
        this.btname = btname;
    }

    public String getBbranch() {
        return bbranch;
    }

    public void setBbranch(String bbranch) {
        this.bbranch = bbranch;
    }

    public String getByear() {
        return byear;
    }

    public void setByear(String byear) {
        this.byear = byear;
    }

    public String getBdiv() {
        return bdiv;
    }

    public void setBdiv(String bdiv) {
        this.bdiv = bdiv;
    }

    public String getBname() {
        return bname;
    }

    public void setBname(String bname) {
        this.bname = bname;
    }

    public String getBemail() {
        return bemail;
    }

    public void setBemail(String bemail) {
        this.bemail = bemail;
    }

    public String getBtopic() {
        return btopic;
    }

    public void setBtopic(String btopic) {
        this.btopic = btopic;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
