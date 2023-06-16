package com.razawiyahdev.exerciseprescription;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.razawiyahdev.exerciseprescription.R;
import com.razawiyahdev.exerciseprescription.class2.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpPatient extends AppCompatActivity {

    TextView loginTV,safetyTV;
    public SafetyPrecaution safetyPrecaution;

    EditText nameET,dobET,heightET,weightET,emailET,passwordET;
    RadioButton maleRB, femaleRB;
    CheckBox safetyCB;
    Button signupBtn;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User");
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser fUser;
    String id,gender,safetyCheck;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_patient);

        loginTV = findViewById(R.id.loginTV);
        safetyPrecaution = new SafetyPrecaution(this);
        safetyTV = findViewById(R.id.safetyTV);

        nameET = findViewById(R.id.nameET);
        dobET = findViewById(R.id.dobET);
        heightET = findViewById(R.id.heightET);
        weightET = findViewById(R.id.weightET);
        emailET = findViewById(R.id.emailET);
        passwordET = findViewById(R.id.passwordET);
        maleRB = findViewById(R.id.maleRB);
        femaleRB = findViewById(R.id.femaleRB);
        safetyCB = findViewById(R.id.checkBox);
        signupBtn = findViewById(R.id.signupBtn);

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameET.getText().toString();
                String dob = dobET.getText().toString();
                String height = heightET.getText().toString();
                String weight = weightET.getText().toString();
                String email = emailET.getText().toString();
                String password = passwordET.getText().toString();
                gender = " ";
                safetyCheck = "";

                if(maleRB.isChecked()){
                    gender= "male";
                }else if(femaleRB.isChecked()){
                    gender= "female";
                }

                if(safetyCB.isChecked()){
                    safetyCheck = "true";
                }else {
                    safetyCheck = "false";
                }

                if (name.isEmpty()){
                    Toast.makeText(SignUpPatient.this,"Fill in the details!",Toast.LENGTH_LONG).show();
                }else if (dob.isEmpty()){
                    Toast.makeText(SignUpPatient.this,"Fill in the details!",Toast.LENGTH_LONG).show();
                }else if (height.isEmpty()){
                    Toast.makeText(SignUpPatient.this,"Fill in the details!",Toast.LENGTH_LONG).show();
                }else if (weight.isEmpty()){
                    Toast.makeText(SignUpPatient.this,"Fill in the details!",Toast.LENGTH_LONG).show();
                }else if (email.isEmpty()){
                    Toast.makeText(SignUpPatient.this,"Fill in the details!",Toast.LENGTH_LONG).show();
                }else if (password.isEmpty()){
                    Toast.makeText(SignUpPatient.this,"Fill in the details!",Toast.LENGTH_LONG).show();
                }else if (gender.isEmpty()){
                    Toast.makeText(SignUpPatient.this,"Fill in the details!",Toast.LENGTH_LONG).show();
                } else if(safetyCheck.equals("false")){
                    Toast.makeText(SignUpPatient.this,"You have not agreed to the Safety & Precautions!",Toast.LENGTH_LONG).show();
                }else{
                    mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(SignUpPatient.this,"Sign Up Successful!",Toast.LENGTH_LONG).show();
                                fUser = mAuth.getCurrentUser();
                                id = fUser.getUid();
                                //add UserModel to DB
                                UserModel user = new UserModel(name,dob,height,weight,email,password,gender,safetyCheck,id);
                                databaseReference.child(id).setValue(user);
                                mAuth.signOut();
                                startActivity(new Intent(getApplicationContext(),Login.class));
                            }
                        }
                    });

                }
            }
        });

        loginTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpPatient.this, Login.class));
                finish();
            }
        });

        safetyTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                safetyPrecaution.show();
            }
        });

        maleRB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                femaleRB.setChecked(false);
            }
        });
        femaleRB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                maleRB.setChecked(false);
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(SignUpPatient.this, SignUpChooseAccount.class));
        finish();
    }
}