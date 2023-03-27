package com.example.exerciseprescription;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.exerciseprescription.class2.AerobicModel;
import com.example.exerciseprescription.class2.HeartRateModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class UserHeartRateData extends AppCompatActivity {

    EditText dateET,resthrET,peakhrET;
    Button submitBtn2;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("HeartRate");
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser fUser;
    String id;
    String timeStamp = String.valueOf(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_heart_rate_data);

        dateET = findViewById(R.id.dateET);
        resthrET = findViewById(R.id.resthrET);
        peakhrET = findViewById(R.id.peakhrET);

        submitBtn2 = findViewById(R.id.submitBtn2);

        fUser = mAuth.getCurrentUser();
        id = fUser.getUid();

        submitBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = dateET.getText().toString();
                String resthr = resthrET.getText().toString();
                String peakhr = peakhrET.getText().toString();


                if (date.isEmpty()){
                    Toast.makeText(UserHeartRateData.this,"Fill in the details!",Toast.LENGTH_LONG).show();
                }else if (resthr.isEmpty()){
                    Toast.makeText(UserHeartRateData.this,"Fill in the details!",Toast.LENGTH_LONG).show();
                }else if (peakhr.isEmpty()){
                    Toast.makeText(UserHeartRateData.this,"Fill in the details!",Toast.LENGTH_LONG).show();
                }else{

                    HeartRateModel model = new HeartRateModel(date,resthr,peakhr,id);
                    databaseReference.child(id).child(timeStamp).setValue(model);
                    Toast.makeText(UserHeartRateData.this,"Data Saved!",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(UserHeartRateData.this, UserHealthData.class));
                    finish();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(UserHeartRateData.this, UserHealthData.class));
        finish();
    }
}