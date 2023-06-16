package com.razawiyahdev.exerciseprescription.class2;

public class BloodPressureModel {
    String date,systolic,diastolic,id,mbp;

    public BloodPressureModel() {
    }

    public BloodPressureModel(String date, String systolic, String diastolic) {
        this.date = date;
        this.systolic = systolic;
        this.diastolic = diastolic;
    }

    public BloodPressureModel(String date, String systolic, String diastolic, String id) {
        this.date = date;
        this.systolic = systolic;
        this.diastolic = diastolic;
        this.id = id;
    }

    public String getMbp() {
        return mbp;
    }

    public void setMbp(String mbp) {
        this.mbp = mbp;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSystolic() {
        return systolic;
    }

    public void setSystolic(String systolic) {
        this.systolic = systolic;
    }

    public String getDiastolic() {
        return diastolic;
    }

    public void setDiastolic(String diastolic) {
        this.diastolic = diastolic;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}