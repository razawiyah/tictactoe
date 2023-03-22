package com.example.exerciseprescription;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SignUp extends AppCompatActivity {

    TextView loginTV,safetyTV;
    public SafetyPrecaution safetyPrecaution;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        loginTV = findViewById(R.id.loginTV);
        safetyPrecaution = new SafetyPrecaution(this);
        safetyTV = findViewById(R.id.safetyTV);

        loginTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUp.this, Login.class));
                finish();
            }
        });

        safetyTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                safetyPrecaution.show();
            }
        });
    }
}