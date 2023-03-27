package com.example.exerciseprescription;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.exerciseprescription.class2.AerobicModel;
import com.example.exerciseprescription.class2.WeightModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class UserWeightData extends AppCompatActivity {

    EditText dateET,weightET;
    Button submitBtn2;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Weight");
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser fUser;
    String id;
    String timeStamp = String.valueOf(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_weight_data);

        dateET = findViewById(R.id.dateET);
        weightET = findViewById(R.id.weightET);
        submitBtn2 = findViewById(R.id.submitBtn2);

        fUser = mAuth.getCurrentUser();
        id = fUser.getUid();

        submitBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = dateET.getText().toString();
                String weight = weightET.getText().toString();

                if (date.isEmpty()){
                    Toast.makeText(UserWeightData.this,"Fill in the details!",Toast.LENGTH_LONG).show();
                }else if (weight.isEmpty()){
                    Toast.makeText(UserWeightData.this,"Fill in the details!",Toast.LENGTH_LONG).show();
                }else{

                    WeightModel model = new WeightModel(date,weight,id);
                    databaseReference.child(id).child(timeStamp).setValue(model);
                    Toast.makeText(UserWeightData.this,"Data Saved!",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(UserWeightData.this, UserHealthData.class));
                    finish();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(UserWeightData.this, UserHealthData.class));
        finish();
    }
}