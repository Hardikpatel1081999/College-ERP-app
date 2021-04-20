package com.example.project;

public class feedbackdata {
    public String feedbackemail;
    public String feedbackname;
    public String feedbacktype;
    public String comment;
    public String rating;

    public feedbackdata() {
    }

    public feedbackdata(String feedbackemail, String feedbackname, String feedbacktype, String comment, String rating) {
        this.feedbackemail = feedbackemail;
        this.feedbackname = feedbackname;
        this.feedbacktype = feedbacktype;
        this.comment = comment;
        this.rating = rating;
    }

    public String getFeedbackemail() {
        return feedbackemail;
    }

    public void setFeedbackemail(String feedbackemail) {
        this.feedbackemail = feedbackemail;
    }

    public String getFeedbackname() {
        return feedbackname;
    }

    public void setFeedbackname(String feedbackname) {
        this.feedbackname = feedbackname;
    }

    public String getFeedbacktype() {
        return feedbacktype;
    }

    public void setFeedbacktype(String feedbacktype) {
        this.feedbacktype = feedbacktype;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
