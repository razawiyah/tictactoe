package com.example.exerciseprescription;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.exerciseprescription.class2.AerobicModel;
import com.example.exerciseprescription.class2.BloodPressureModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

public class UserBloodPressureData extends AppCompatActivity {

    EditText dateET,systolicET,diastolicET;
    Button submitBtn2;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("BloodPressure");
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser fUser;
    String id;
    String timeStamp = String.valueOf(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()));

    ZoneId zoneId = ZoneId.of("Asia/Kuala_Lumpur");
    ZonedDateTime zonedDateTime = ZonedDateTime.now(zoneId);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    String formattedDate = zonedDateTime.format(formatter);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_blood_pressure_data);

        dateET = findViewById(R.id.dateET);
        systolicET = findViewById(R.id.systolicET);
        diastolicET = findViewById(R.id.diastolicET);

        submitBtn2 = findViewById(R.id.submitBtn2);

        fUser = mAuth.getCurrentUser();
        id = fUser.getUid();

        dateET.setText(formattedDate);
        dateET.setFocusable(false);
        dateET.setFocusableInTouchMode(false);

        submitBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = dateET.getText().toString();
                String systolic = systolicET.getText().toString();
                String diastolic = diastolicET.getText().toString();

                if (date.isEmpty()){
                    Toast.makeText(UserBloodPressureData.this,"Fill in the details!",Toast.LENGTH_LONG).show();
                }else if (systolic.isEmpty()){
                    Toast.makeText(UserBloodPressureData.this,"Fill in the details!",Toast.LENGTH_LONG).show();
                }else if (diastolic.isEmpty()){
                    Toast.makeText(UserBloodPressureData.this,"Fill in the details!",Toast.LENGTH_LONG).show();
                }else{

                    BloodPressureModel model = new BloodPressureModel(date,systolic,diastolic,id);
                    databaseReference.child(id).child(timeStamp).setValue(model);
                    Toast.makeText(UserBloodPressureData.this,"Data Saved!",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(UserBloodPressureData.this, UserHealthData.class));
                    finish();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(UserBloodPressureData.this, UserHealthData.class));
        finish();
    }
}