package com.example.exerciseprescription.class2;

public class HeartRateModel {

    String date,resthr,peakhr,id;

    public HeartRateModel() {
    }

    public HeartRateModel(String date, String resthr, String peakhr) {
        this.date = date;
        this.resthr = resthr;
        this.peakhr = peakhr;
    }

    public HeartRateModel(String date, String resthr, String peakhr, String id) {
        this.date = date;
        this.resthr = resthr;
        this.peakhr = peakhr;
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getResthr() {
        return resthr;
    }

    public void setResthr(String resthr) {
        this.resthr = resthr;
    }

    public String getPeakhr() {
        return peakhr;
    }

    public void setPeakhr(String peakhr) {
        this.peakhr = peakhr;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}