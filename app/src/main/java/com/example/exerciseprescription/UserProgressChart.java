package com.example.exerciseprescription;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class UserProgressChart extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_progress_chart);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(UserProgressChart.this, UserHomepage.class));
        finish();
    }
}