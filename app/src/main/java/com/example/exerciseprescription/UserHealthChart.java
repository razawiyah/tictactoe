package com.example.exerciseprescription;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class UserHealthChart extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_health_chart);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(UserHealthChart.this, UserProgressChart.class));
        finish();
    }
}