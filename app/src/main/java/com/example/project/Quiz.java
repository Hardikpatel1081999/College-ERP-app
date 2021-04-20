package com.example.project;

public class Quiz {
    public String sname;
    public String chapno;

    public Quiz() {
    }

    public Quiz(String sname, String chapno) {
        this.sname = sname;
        this.chapno = chapno;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getChapno() {
        return chapno;
    }

    public void setChapno(String chapno) {
        this.chapno = chapno;
    }
}
