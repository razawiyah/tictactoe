package com.example.exerciseprescription;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class UserFlexibilityData extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_flexibility_data);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(UserFlexibilityData.this, UserExerciseData.class));
        finish();
    }
}