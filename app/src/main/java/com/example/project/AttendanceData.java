package com.example.project;

public class AttendanceData {
    public String stpemail;
    public String stpname;
    public String teachername;
    public String teacheremail;

    public AttendanceData() {
    }

    public AttendanceData(String stpemail, String stpname, String teachername, String teacheremail) {
        this.stpemail = stpemail;
        this.stpname = stpname;
        this.teachername = teachername;
        this.teacheremail = teacheremail;
    }

    public String getStpemail() {
        return stpemail;
    }

    public void setStpemail(String stpemail) {
        this.stpemail = stpemail;
    }

    public String getStpname() {
        return stpname;
    }

    public void setStpname(String stpname) {
        this.stpname = stpname;
    }

    public String getTeachername() {
        return teachername;
    }

    public void setTeachername(String teachername) {
        this.teachername = teachername;
    }

    public String getTeacheremail() {
        return teacheremail;
    }

    public void setTeacheremail(String teacheremail) {
        this.teacheremail = teacheremail;
    }
}
