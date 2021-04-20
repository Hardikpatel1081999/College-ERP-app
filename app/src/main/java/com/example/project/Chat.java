package com.example.project;

public class Chat {
    private String msgsender;
    private String msgreceiver;
    private String msg;

    public Chat(String msgsender, String msgreceiver, String msg) {
        this.msgsender = msgsender;
        this.msgreceiver = msgreceiver;
        this.msg = msg;
    }

    public Chat(){

    }

    public String getMsgsender() {
        return msgsender;
    }

    public void setMsgsender(String msgsender) {
        this.msgsender = msgsender;
    }

    public String getMsgreceiver() {
        return msgreceiver;
    }

    public void setMsgreceiver(String msgreceiver) {
        this.msgreceiver = msgreceiver;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
