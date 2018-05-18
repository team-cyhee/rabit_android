package com.cyhee.android.rabit;

public class Participant {

    private String gId;
    private String gEmail;
    private String pEmail;
    private String pName;
    private String pStartDate;
    private int pDays;
    private String pStatus;

    public Participant(String gId, String gEmail, String pEmail,
                       String pName, String pStartDate, int pDays, String pStatus) {
        this.gId = gId;
        this.gEmail = gEmail;
        this.pEmail = pEmail;
        this.pName = pName;
        this.pStartDate = pStartDate;
        this.pDays = pDays;
        this.pStatus = pStatus;
    }

    public String getgId() {
        return gId;
    }

    public void setgId(String gId) {
        this.gId = gId;
    }

    public String getgEmail() {
        return gEmail;
    }

    public void setgEmail(String gEmail) {
        this.gEmail = gEmail;
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

    public String getpStartDate() {
        return pStartDate;
    }

    public void setpStartDate(String pStartDate) {
        this.pStartDate = pStartDate;
    }

    public int getpDays() {
        return pDays;
    }

    public void setpDays(int pDays) {
        this.pDays = pDays;
    }

    public String getpStatus() {
        return pStatus;
    }

    public void setpStatus(String pStatus) {
        this.pStatus = pStatus;
    }
}
