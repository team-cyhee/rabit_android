package com.cyhee.android.rabit;

public class Goal {

    String gName;
    String gEmail;
    String gTitle;
    String gStartDate;
    String gId;

    public Goal(String gName, String gEmail, String gTitle, String gStartDate, String gId) {
        this.gName = gName;
        this.gEmail = gEmail;
        this.gTitle = gTitle;
        this.gStartDate = gStartDate;
        this.gId = gId;
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
}
