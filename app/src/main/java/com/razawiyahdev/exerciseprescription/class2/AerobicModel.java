package com.razawiyahdev.exerciseprescription.class2;

public class AerobicModel {

    String date,type,intensity,duration,rpe,note,id;

    public AerobicModel() {
    }

    public AerobicModel(String date, String duration) {
        this.date = date;
        this.duration = duration;
    }

    public AerobicModel(String date, String type, String intensity, String duration, String rpe, String note, String id) {
        this.date = date;
        this.type = type;
        this.intensity = intensity;
        this.duration = duration;
        this.rpe = rpe;
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

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getRpe() {
        return rpe;
    }

    public void setRpe(String rpe) {
        this.rpe = rpe;
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