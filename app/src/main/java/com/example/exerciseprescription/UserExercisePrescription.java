package com.example.exerciseprescription;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class UserExercisePrescription extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_exercise_prescription);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(UserExercisePrescription.this, UserHomepage.class));
        finish();
    }
}