package com.example.stressbuster;

public class FacultyDataReminders {

    String assignmentInfo;
    String awardedPoints;
    String userId;

    FacultyDataReminders(String assignmentInfo, String awardedPoints, String userId) {
        this.assignmentInfo = assignmentInfo;
        this.awardedPoints = awardedPoints;
        this.userId = userId;
    }

    public String getAssignmentInfo() {
        return assignmentInfo;
    }

    public String getAwardedPoints() {
        return awardedPoints;
    }

    public String getUserId() {
        return userId;
    }
}
