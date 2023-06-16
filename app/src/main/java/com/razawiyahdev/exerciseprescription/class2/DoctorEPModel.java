package com.razawiyahdev.exerciseprescription.class2;

public class DoctorEPModel {

    public DoctorEPModel(){}
    String id,name;

    public DoctorEPModel(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}