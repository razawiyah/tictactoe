package com.example.exerciseprescription.class2;

public class FlexibilityModel {
    String date,type,intensity,repetition,set,note,id;

    public FlexibilityModel(String date, String type, String intensity, String repetition, String set, String note, String id) {
        this.date = date;
        this.type = type;
        this.intensity = intensity;
        this.repetition = repetition;
        this.set = set;
        this.note = note;
        this.id = id;
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

    public String getRepetition() {
        return repetition;
    }

    public void setRepetition(String repetition) {
        this.repetition = repetition;
    }

    public String getSet() {
        return set;
    }

    public void setSet(String set) {
        this.set = set;
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