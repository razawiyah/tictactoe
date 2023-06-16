package com.razawiyahdev.exerciseprescription;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.razawiyahdev.exerciseprescription.R;

public class UserHealthData extends AppCompatActivity {

    CardView userWeightBtn,userHRBtn,userBPBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_health_data);

        userWeightBtn = findViewById(R.id.userWeightBtn);
        userHRBtn = findViewById(R.id.userHRBtn);
        userBPBtn = findViewById(R.id.userBPBtn);

        userWeightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserHealthData.this, UserWeightData.class));
                finish();
            }
        });

        userHRBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserHealthData.this, UserHeartRateData.class));
                finish();
            }
        });

        userBPBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserHealthData.this, UserBloodPressureData.class));
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(UserHealthData.this, UserHomepage.class));
        finish();
    }
}