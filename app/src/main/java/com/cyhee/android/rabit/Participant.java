package com.cyhee.android.rabit;

public class Participant {

    String pName;
    String pEmail;
    int pDays;
    String pContent;

    public Participant(String pEmail, String pName, int pDays, String pContent) {
        this.pEmail = pEmail;
        this.pName = pName;
        this.pDays = pDays;
        this.pContent = pContent;
    }

    public String getpEmail() {
        return pEmail;
    }

    public void setpEmail(String pEmail) {
        this.pEmail = pEmail;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public int getpDays() {
        return pDays;
    }

    public void setpDays(int pDays) {
        this.pDays = pDays;
    }

    public String getpContent() {
        return pContent;
    }

    public void setpContent(String pContent) {
        this.pContent = pContent;
    }
}
