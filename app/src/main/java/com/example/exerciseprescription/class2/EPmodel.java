package com.example.exerciseprescription.class2;

public class EPmodel {
    String name,week,duration,intensity,aerobic,strength,flexibility,note,ptId,drId,timeStamp,date,drName,drEmail;

    public EPmodel() {
    }

    public EPmodel(String name, String week, String duration, String intensity, String aerobic, String strength, String flexibility, String note, String ptId, String drId, String date, String drName, String drEmail) {
        this.name = name;
        this.week = week;
        this.duration = duration;
        this.intensity = intensity;
        this.aerobic = aerobic;
        this.strength = strength;
        this.flexibility = flexibility;
        this.note = note;
        this.ptId = ptId;
        this.drId = drId;
        this.date = date;
        this.drName = drName;
        this.drEmail = drEmail;
    }

    public EPmodel(String name, String week, String duration, String intensity, String aerobic, String strength, String flexibility, String note, String ptId, String drId, String timeStamp, String date) {
        this.name = name;
        this.week = week;
        this.duration = duration;
        this.intensity = intensity;
        this.aerobic = aerobic;
        this.strength = strength;
        this.flexibility = flexibility;
        this.note = note;
        this.ptId = ptId;
        this.drId = drId;
        this.timeStamp = timeStamp;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getDrName() {
        return drName;
    }

    public void setDrName(String drName) {
        this.drName = drName;
    }

    public String getDrEmail() {
        return drEmail;
    }

    public void setDrEmail(String drEmail) {
        this.drEmail = drEmail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getIntensity() {
        return intensity;
    }

    public void setIntensity(String intensity) {
        this.intensity = intensity;
    }

    public String getAerobic() {
        return aerobic;
    }

    public void setAerobic(String aerobic) {
        this.aerobic = aerobic;
    }

    public String getStrength() {
        return strength;
    }

    public void setStrength(String strength) {
        this.strength = strength;
    }

    public String getFlexibility() {
        return flexibility;
    }

    public void setFlexibility(String flexibility) {
        this.flexibility = flexibility;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPtId() {
        return ptId;
    }

    public void setPtId(String ptId) {
        this.ptId = ptId;
    }

    public String getDrId() {
        return drId;
    }

    public void setDrId(String drId) {
        this.drId = drId;
    }
}