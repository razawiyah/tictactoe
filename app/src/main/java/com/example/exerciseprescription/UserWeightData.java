package com.example.exerciseprescription;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class UserWeightData extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_weight_data);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(UserWeightData.this, UserHealthData.class));
        finish();
    }
}