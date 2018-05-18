package com.cyhee.android.rabit;

public class Goal {

    private String gName;
    private String gEmail;
    private String gTitle;
    private String gStartDate;
    private String gId;
    private String[][] gParticipant;
    private String gRecent;
    private String gDays;
    private String gStatus;

    protected Goal(String gName, String gEmail, String gTitle, String gStartDate,
                   String gDays, String gId, String[][] gParticipant, String gRecent,
                   String gStatus) {
        this.gName = gName;
        this.gEmail = gEmail;
        this.gTitle = gTitle;
        this.gStartDate = gStartDate;
        this.gId = gId;
        this.gParticipant = gParticipant;
        this.gRecent = gRecent;
        this.gDays = gDays;
        this.gStatus = gStatus;
    }

    public String getgName() {
        return gName;
    }

    public void setgName(String gName) {
        this.gName = gName;
    }

    public String getgEmail() {
        return gEmail;
    }

    public void setgEmail(String gEmail) {
        this.gEmail = gEmail;
    }

    public String getgTitle() {
        return gTitle;
    }

    public void setgTitle(String gTitle) {
        this.gTitle = gTitle;
    }

    public String getgStartDate() {
        return gStartDate;
    }

    public void setgStartDate(String gStartDate) {
        this.gStartDate = gStartDate;
    }

    public String getgId() {
        return gId;
    }

    public void setgId(String gId) {
        this.gId = gId;
    }

    public String getgPname(int idx) {
        return gParticipant[idx][1];
    }

    public String getgPday(int idx) {
        return gParticipant[idx][2];
    }

    public void setgParticipant(String[][] gParticipant) {
        this.gParticipant = gParticipant;
    }

    public String getgRecent() {
        return gRecent;
    }

    public void setgRecent(String gRecent) {
        this.gRecent = gRecent;
    }

    public String getgDays() {
        return gDays;
    }

    public void setgDays(String gDays) {
        this.gDays = gDays;
    }

    public String getgStatus() {
        return gStatus;
    }

    public void setgStatus(String gStatus) {
        this.gStatus = gStatus;
    }
}