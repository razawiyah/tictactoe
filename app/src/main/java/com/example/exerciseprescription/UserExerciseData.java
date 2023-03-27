package com.example.exerciseprescription;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class UserExerciseData extends AppCompatActivity {

    CardView aerobicBtn,flexBtn,strengthBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_exercise_data);

        aerobicBtn = findViewById(R.id.aerobicBtn);
        flexBtn = findViewById(R.id.flexBtn);
        strengthBtn = findViewById(R.id.strengthBtn);

        aerobicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserExerciseData.this, UserAerobicData.class));
                finish();
            }
        });

        flexBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserExerciseData.this, UserFlexibilityData.class));
                finish();
            }
        });

        strengthBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserExerciseData.this, UserStrengthData.class));
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(UserExerciseData.this, UserHomepage.class));
        finish();
    }
}