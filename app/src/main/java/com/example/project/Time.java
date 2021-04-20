package com.example.project;

public class Time {
    public String timename;
    public String ttype;
    public String tbranch;
    public String tclass;
    public String div;
    public String tdate;

    public Time() {
    }

    public Time(String timename, String ttype, String tbranch, String tclass, String div, String tdate) {
        this.timename = timename;
        this.ttype = ttype;
        this.tbranch = tbranch;
        this.tclass = tclass;
        this.div = div;
        this.tdate = tdate;
    }

    public String getTimename() {
        return timename;
    }

    public void setTimename(String timename) {
        this.timename = timename;
    }

    public String getTtype() {
        return ttype;
    }

    public void setTtype(String ttype) {
        this.ttype = ttype;
    }

    public String getTbranch() {
        return tbranch;
    }

    public void setTbranch(String tbranch) {
        this.tbranch = tbranch;
    }

    public String getTclass() {
        return tclass;
    }

    public void setTclass(String tclass) {
        this.tclass = tclass;
    }

    public String getDiv() {
        return div;
    }

    public void setDiv(String div) {
        this.div = div;
    }

    public String getTdate() {
        return tdate;
    }

    public void setTdate(String tdate) {
        this.tdate = tdate;
    }
}
