package com.example.exerciseprescription.class2;

public class UserModel {
    String name,dob,height,weight,email,password,gender,safetycheck,id;

    public UserModel(String name, String dob, String height, String weight, String email, String password, String gender, String safetycheck, String id) {
        this.name = name;
        this.dob = dob;
        this.height = height;
        this.weight = weight;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.safetycheck = safetycheck;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getDob() {
        return dob;
    }

    public String getHeight() {
        return height;
    }

    public String getWeight() {
        return weight;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getGender() {
        return gender;
    }

    public String getSafetycheck() {
        return safetycheck;
    }

    public String getId() {
        return id;
    }
}