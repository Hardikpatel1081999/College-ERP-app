package com.example.project;

public class expense {
    public String sr;
    public String transemail;
    public String amount;
    public String cdate;
    public String paytype;
    public String transname;
    public String transphone;
    public String transbranch;
    public String ediscription;
    public String url;
    public expense(){

    }

    public expense(String sr, String transemail, String amount, String cdate, String paytype, String transname, String transphone, String transbranch, String ediscription, String url) {
        this.sr = sr;
        this.transemail = transemail;
        this.amount = amount;
        this.cdate = cdate;
        this.paytype = paytype;
        this.transname = transname;
        this.transphone = transphone;
        this.transbranch = transbranch;
        this.ediscription = ediscription;
        this.url = url;
    }

    public String getSr() {
        return sr;
    }

    public void setSr(String sr) {
        this.sr = sr;
    }

    public String getTransemail() {
        return transemail;
    }

    public void setTransemail(String transemail) {
        this.transemail = transemail;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCdate() {
        return cdate;
    }

    public void setCdate(String cdate) {
        this.cdate = cdate;
    }

    public String getPaytype() {
        return paytype;
    }

    public void setPaytype(String paytype) {
        this.paytype = paytype;
    }

    public String getTransname() {
        return transname;
    }

    public void setTransname(String transname) {
        this.transname = transname;
    }

    public String getTransphone() {
        return transphone;
    }

    public void setTransphone(String transphone) {
        this.transphone = transphone;
    }

    public String getTransbranch() {
        return transbranch;
    }

    public void setTransbranch(String transbranch) {
        this.transbranch = transbranch;
    }

    public String getEdiscription() {
        return ediscription;
    }

    public void setEdiscription(String ediscription) {
        this.ediscription = ediscription;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}