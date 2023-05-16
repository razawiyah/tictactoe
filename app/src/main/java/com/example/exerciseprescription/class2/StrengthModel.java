package com.example.exerciseprescription.class2;

public class StrengthModel {
    String date,type,intensity,repset,duration,note,id;

    public StrengthModel() {
    }

    public StrengthModel(String date, String duration) {
        this.date = date;
        this.duration = duration;
    }

    public StrengthModel(String date, String type, String intensity, String repset, String duration, String note, String id) {
        this.date = date;
        this.type = type;
        this.intensity = intensity;
        this.repset = repset;
        this.duration = duration;
        this.note = note;
        this.id = id;
    }

    public String getRepset() {
        return repset;
    }

    public void setRepset(String repset) {
        this.repset = repset;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIntensity() {
        return intensity;
    }

    public void setIntensity(String intensity) {
        this.intensity = intensity;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}