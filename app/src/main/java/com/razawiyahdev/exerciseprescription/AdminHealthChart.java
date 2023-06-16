package com.razawiyahdev.exerciseprescription;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.razawiyahdev.exerciseprescription.R;

public class AdminHealthChart extends AppCompatActivity {

    String id,patientId;

    CardView userWeightBtn,userHRBtn,userBPBtn;

    public static final String CHART_TYPE = "CHARTTYPE";

    public static final String PATIENT_ID = "PATIENTID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_health_chart);

        Intent intent = getIntent();
        patientId  = intent.getStringExtra(AdminProgressChartOption.PATIENT_ID);

        userWeightBtn = findViewById(R.id.userWeightBtn);
        userHRBtn = findViewById(R.id.userHRBtn);
        userBPBtn = findViewById(R.id.userBPBtn);

        Intent intent2 = new Intent(AdminHealthChart.this,AdminChart.class);


        userWeightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent2.putExtra(CHART_TYPE, "weight");
                intent2.putExtra(PATIENT_ID,patientId);
                startActivity(intent2);
                finish();
            }
        });

        userHRBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent2.putExtra(CHART_TYPE, "heartRate");
                intent2.putExtra(PATIENT_ID,patientId);
                startActivity(intent2);
                finish();
            }
        });

        userBPBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent2.putExtra(CHART_TYPE, "bloodPressure");
                intent2.putExtra(PATIENT_ID,patientId);
                startActivity(intent2);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent2 = new Intent(AdminHealthChart.this,AdminProgressChartOption.class);
        intent2.putExtra(PATIENT_ID,patientId);
        startActivity(intent2);
    }
}