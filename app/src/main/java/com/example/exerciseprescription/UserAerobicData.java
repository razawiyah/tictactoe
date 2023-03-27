package com.example.exerciseprescription;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class UserAerobicData extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_aerobic_data);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(UserAerobicData.this, UserExerciseData.class));
        finish();
    }
}