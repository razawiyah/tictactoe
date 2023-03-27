package com.example.exerciseprescription;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class UserStrengthData extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_strength_data);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(UserStrengthData.this, UserExerciseData.class));
        finish();
    }
}