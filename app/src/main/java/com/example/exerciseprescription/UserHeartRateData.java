package com.example.exerciseprescription;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class UserHeartRateData extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_heart_rate_data);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(UserHeartRateData.this, UserHealthData.class));
        finish();
    }
}