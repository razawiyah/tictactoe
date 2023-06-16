package com.razawiyahdev.exerciseprescription.class2;

public class WeightModel {
    String date,weight,id;

    public WeightModel(String date, String weight, String id) {
        this.date = date;
        this.weight = weight;
        this.id = id;
    }

    public WeightModel(String date, String weight) {
        this.date = date;
        this.weight = weight;
    }

    public WeightModel() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}