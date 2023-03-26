package com.example.exerciseprescription;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class UserHealthData extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_health_data);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(UserHealthData.this, UserHomepage.class));
        finish();
    }
}