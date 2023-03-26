package com.example.exerciseprescription;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AdminHomepage extends AppCompatActivity {

    CardView exercisePCard,progressCCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_homepage);

        exercisePCard = findViewById(R.id.exercisePCard);
        progressCCard = findViewById(R.id.progressCCard);

        exercisePCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminHomepage.this, AdminExercisePrescription.class));
                finish();
            }
        });
    }
}