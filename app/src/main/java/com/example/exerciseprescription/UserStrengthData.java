package com.example.exerciseprescription;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.exerciseprescription.class2.AerobicModel;
import com.example.exerciseprescription.class2.StrengthModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class UserStrengthData extends AppCompatActivity {

    EditText dateET,typeET,intensityET,durationET,noteET2;
    Button submitBtn2;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Strength");
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser fUser;
    String id;
    String timeStamp = String.valueOf(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_strength_data);

        dateET = findViewById(R.id.dateET);
        typeET = findViewById(R.id.typeET);
        intensityET = findViewById(R.id.intensityET);
        durationET = findViewById(R.id.durationET);
        noteET2 = findViewById(R.id.noteET2);
        submitBtn2 = findViewById(R.id.submitBtn2);

        fUser = mAuth.getCurrentUser();
        id = fUser.getUid();

        submitBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = dateET.getText().toString();
                String type = typeET.getText().toString();
                String intensity = intensityET.getText().toString();
                String duration = durationET.getText().toString();
                String note = noteET2.getText().toString();

                if (date.isEmpty()){
                    Toast.makeText(UserStrengthData.this,"Fill in the details!",Toast.LENGTH_LONG).show();
                }else if (type.isEmpty()){
                    Toast.makeText(UserStrengthData.this,"Fill in the details!",Toast.LENGTH_LONG).show();
                }else if (intensity.isEmpty()){
                    Toast.makeText(UserStrengthData.this,"Fill in the details!",Toast.LENGTH_LONG).show();
                }else if (duration.isEmpty()){
                    Toast.makeText(UserStrengthData.this,"Fill in the details!",Toast.LENGTH_LONG).show();
                }else if (note.isEmpty()){
                    Toast.makeText(UserStrengthData.this,"Fill in the details!",Toast.LENGTH_LONG).show();
                }else{

                    StrengthModel model = new StrengthModel(date,type,intensity,duration,note,id);
                    databaseReference.child(id).child(timeStamp).setValue(model);
                    Toast.makeText(UserStrengthData.this,"Data Saved!",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(UserStrengthData.this, UserExerciseData.class));
                    finish();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(UserStrengthData.this, UserExerciseData.class));
        finish();
    }
}