package com.example.exerciseprescription;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class UserProgressChart extends AppCompatActivity {

    CardView userEDBtn,userHDBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_progress_chart);

        userEDBtn = findViewById(R.id.userEDBtn);
        userHDBtn = findViewById(R.id.userHDBtn);

        userEDBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserProgressChart.this, UserHealthChart.class));
                finish();
            }
        });

        userHDBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserProgressChart.this, UserExerciseChart.class));
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(UserProgressChart.this, UserHomepage.class));
        finish();
    }
}