package com.razawiyahdev.exerciseprescription;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.razawiyahdev.exerciseprescription.R;

public class UserHealthChart extends AppCompatActivity {

    CardView userAerobicBtn,userFlexBtn,userStrengthBtn;
    public static final String CHART_TYPE = "CHARTTYPE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_health_chart);

        userAerobicBtn = findViewById(R.id.userAerobicBtn);
        userFlexBtn = findViewById(R.id.userFlexBtn);
        userStrengthBtn = findViewById(R.id.userStrengthBtn);

        Intent intent2 = new Intent(UserHealthChart.this,ChartExample.class);


        userAerobicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent2.putExtra(CHART_TYPE, "aerobic");
                startActivity(intent2);
                finish();
            }
        });

        userFlexBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent2.putExtra(CHART_TYPE, "flexibility");
                startActivity(intent2);
                finish();
            }
        });

        userStrengthBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent2.putExtra(CHART_TYPE, "strength");
                startActivity(intent2);
                finish();
            }
        });


    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(UserHealthChart.this, UserProgressChart.class));
        finish();
    }
}