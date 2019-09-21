package com.example.stressbuster;

public class MyListDataForCounselling {
    private String typeOfCounselling;
    private String counsellorName;
    private String dateOfCounselling;
    private String durationOfCounselling;
    private int ratingValue;

    public MyListDataForCounselling(String typeOfCounselling, String counsellorName, String dateOfCounselling, String durationOfCounselling, int ratingValue) {
        this.counsellorName =counsellorName;
        this.dateOfCounselling = dateOfCounselling;
        this.durationOfCounselling = durationOfCounselling;
        this.typeOfCounselling = typeOfCounselling;
        this.ratingValue = ratingValue;
    }

    public String getCounsellorName() {
        return counsellorName;
    }

    public int getRatingValue() {
        return ratingValue;
    }

    public String getDateOfCounselling() {
        return dateOfCounselling;
    }

    public String getDurationOfCounselling() {
        return durationOfCounselling;
    }

    public String getTypeOfCounselling() {
        return typeOfCounselling;
    }
}
