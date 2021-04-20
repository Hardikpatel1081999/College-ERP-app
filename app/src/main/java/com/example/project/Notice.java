package com.example.project;

public class Notice {
    public String nname;
    public String ntype;
    public String location;
    public String url;

    public Notice() {
    }

    public Notice(String nname, String ntype, String location, String url) {
        this.nname = nname;
        this.ntype = ntype;
        this.location = location;
        this.url = url;
    }

    public String getNname() {
        return nname;
    }

    public void setNname(String nname) {
        this.nname = nname;
    }

    public String getNtype() {
        return ntype;
    }

    public void setNtype(String ntype) {
        this.ntype = ntype;
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
