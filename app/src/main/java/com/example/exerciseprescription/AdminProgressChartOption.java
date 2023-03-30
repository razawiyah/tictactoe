package com.example.exerciseprescription;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AdminProgressChartOption extends AppCompatActivity {

    CardView exerciseDCard,healthDCard,exercisePCard;
    public static final String PATIENT_ID = "PATIENTID";
    public static final String TIMESTAMP = "TIMESTAMP";

    String patientId,timeStamp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_progress_chart_option);

        exerciseDCard = findViewById(R.id.exerciseDCard);
        healthDCard = findViewById(R.id.healthDCard);
        exercisePCard = findViewById(R.id.exercisePCard);

        Intent intent = getIntent();
        if(!((RecyclerAdapter3.PATIENT_ID).isEmpty())){
            patientId  = intent.getStringExtra(RecyclerAdapter3.PATIENT_ID);
        }else if(!((AdminExercisePrescriptionModify.PATIENT_ID).isEmpty())){
            patientId  = intent.getStringExtra(AdminExercisePrescriptionModify.PATIENT_ID);
        }else if(!((AdminExerciseChart.PATIENT_ID).isEmpty())){
            patientId  = intent.getStringExtra(AdminExerciseChart.PATIENT_ID);
        }else if(!((AdminHealthChart.PATIENT_ID).isEmpty())){
            patientId  = intent.getStringExtra(AdminHealthChart.PATIENT_ID);
        }

        timeStamp = intent.getStringExtra(RecyclerAdapter3.TIMESTAMP);


        exerciseDCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(AdminProgressChartOption.this,AdminExerciseChart.class);
                intent2.putExtra(PATIENT_ID,patientId);
                intent2.putExtra(TIMESTAMP,timeStamp);
                startActivity(intent2);
                finish();
            }
        });

        healthDCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(AdminProgressChartOption.this,AdminHealthChart.class);
                intent2.putExtra(PATIENT_ID,patientId);
                intent2.putExtra(TIMESTAMP,timeStamp);
                startActivity(intent2);
                finish();
            }
        });

        exercisePCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(AdminProgressChartOption.this,AdminExercisePrescriptionModify.class);
                intent2.putExtra(PATIENT_ID,patientId);
                intent2.putExtra(TIMESTAMP,timeStamp);
                startActivity(intent2);
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(AdminProgressChartOption.this, AdminProgressChartList.class));
        finish();
    }
}