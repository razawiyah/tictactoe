package com.razawiyahdev.exerciseprescription;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.razawiyahdev.exerciseprescription.R;

public class AdminExerciseChart extends AppCompatActivity {

    String id,patientId;

    public static final String PATIENT_ID = "PATIENTID";

    CardView userAerobicBtn,userFlexBtn,userStrengthBtn;
    public static final String CHART_TYPE = "CHARTTYPE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_exercise_chart);

        Intent intent = getIntent();
        patientId  = intent.getStringExtra(AdminProgressChartOption.PATIENT_ID);

        userAerobicBtn = findViewById(R.id.userAerobicBtn);
        userFlexBtn = findViewById(R.id.userFlexBtn);
        userStrengthBtn = findViewById(R.id.userStrengthBtn);

        Intent intent2 = new Intent(AdminExerciseChart.this,AdminChart.class);


        userAerobicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent2.putExtra(CHART_TYPE, "aerobic");
                intent2.putExtra(PATIENT_ID,patientId);
                startActivity(intent2);
                finish();
            }
        });

        userFlexBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent2.putExtra(CHART_TYPE, "flexibility");
                intent2.putExtra(PATIENT_ID,patientId);
                startActivity(intent2);
                finish();
            }
        });

        userStrengthBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent2.putExtra(CHART_TYPE, "strength");
                intent2.putExtra(PATIENT_ID,patientId);
                startActivity(intent2);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent2 = new Intent(AdminExerciseChart.this,AdminProgressChartOption.class);
        intent2.putExtra(PATIENT_ID,patientId);
        startActivity(intent2);
    }
}