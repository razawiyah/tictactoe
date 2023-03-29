package com.example.exerciseprescription;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.exerciseprescription.class2.AerobicModel;
import com.example.exerciseprescription.class2.EPmodel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

public class UserAerobicData extends AppCompatActivity {

    EditText dateET,typeET,intensityET,durationET,rpeET,noteET2;
    Button submitBtn2;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Aerobic");
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
        setContentView(R.layout.activity_user_aerobic_data);

        dateET = findViewById(R.id.dateET);
        typeET = findViewById(R.id.typeET);
        intensityET = findViewById(R.id.intensityET);
        durationET = findViewById(R.id.durationET);
        rpeET = findViewById(R.id.rpeET);
        noteET2 = findViewById(R.id.noteET2);
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
                String type = typeET.getText().toString();
                String intensity = intensityET.getText().toString();
                String duration = durationET.getText().toString();
                String rpe = rpeET.getText().toString();
                String note = noteET2.getText().toString();

                if (date.isEmpty()){
                    Toast.makeText(UserAerobicData.this,"Fill in the details!",Toast.LENGTH_LONG).show();
                }else if (type.isEmpty()){
                    Toast.makeText(UserAerobicData.this,"Fill in the details!",Toast.LENGTH_LONG).show();
                }else if (intensity.isEmpty()){
                    Toast.makeText(UserAerobicData.this,"Fill in the details!",Toast.LENGTH_LONG).show();
                }else if (duration.isEmpty()){
                    Toast.makeText(UserAerobicData.this,"Fill in the details!",Toast.LENGTH_LONG).show();
                }else if (rpe.isEmpty()){
                    Toast.makeText(UserAerobicData.this,"Fill in the details!",Toast.LENGTH_LONG).show();
                }else if (note.isEmpty()){
                    Toast.makeText(UserAerobicData.this,"Fill in the details!",Toast.LENGTH_LONG).show();
                }else{

                    AerobicModel model = new AerobicModel(date,type,intensity,duration,rpe,note,id);
                    databaseReference.child(id).child(timeStamp).setValue(model);
                    Toast.makeText(UserAerobicData.this,"Data Saved!",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(UserAerobicData.this, UserExerciseData.class));
                    finish();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(UserAerobicData.this, UserExerciseData.class));
        finish();
    }
}