package com.example.exerciseprescription;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class AdminExerciseChart extends AppCompatActivity {

    String id,patientId;

    public static final String PATIENT_ID = "PATIENTID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_exercise_chart);

        Intent intent = getIntent();
        patientId  = intent.getStringExtra(AdminProgressChartOption.PATIENT_ID);
    }

    @Override
    public void onBackPressed() {
        Intent intent2 = new Intent(AdminExerciseChart.this,AdminProgressChartOption.class);
        intent2.putExtra(PATIENT_ID,patientId);
        startActivity(intent2);
    }
}