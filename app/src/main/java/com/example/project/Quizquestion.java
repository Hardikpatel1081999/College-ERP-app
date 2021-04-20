package com.example.project;

import android.widget.ImageView;

public class Quizquestion {
    public String ques;
    public String op1;
    public String op2;
    public String op3;
    public String op4;
    public String correctans;
    public String chapno;

    public Quizquestion() {
    }

    public Quizquestion(String ques, String op1, String op2, String op3, String op4, String correctans, String chapno) {
        this.ques = ques;
        this.op1 = op1;
        this.op2 = op2;
        this.op3 = op3;
        this.op4 = op4;
        this.correctans = correctans;
        this.chapno = chapno;
    }

    public String getQues() {
        return ques;
    }

    public void setQues(String ques) {
        this.ques = ques;
    }

    public String getOp1() {
        return op1;
    }

    public void setOp1(String op1) {
        this.op1 = op1;
    }

    public String getOp2() {
        return op2;
    }

    public void setOp2(String op2) {
        this.op2 = op2;
    }

    public String getOp3() {
        return op3;
    }

    public void setOp3(String op3) {
        this.op3 = op3;
    }

    public String getOp4() {
        return op4;
    }

    public void setOp4(String op4) {
        this.op4 = op4;
    }

    public String getCorrectans() {
        return correctans;
    }

    public void setCorrectans(String correctans) {
        this.correctans = correctans;
    }

    public String getChapno() {
        return chapno;
    }

    public void setChapno(String chapno) {
        this.chapno = chapno;
    }
}
