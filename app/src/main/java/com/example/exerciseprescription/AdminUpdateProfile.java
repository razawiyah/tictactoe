package com.example.exerciseprescription;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.exerciseprescription.class2.DoctorModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class AdminUpdateProfile extends AppCompatActivity {

    View toolbar;
    TextView title;
    ImageView logout;
    public SignOut dialog;

    EditText nameET,emailET,passwordET;
    RadioButton maleRB, femaleRB;
    Button updateBtn;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Doctor");
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser fUser;
    String id,gender,emailDB,passwordDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_update_profile);

        toolbar = findViewById(R.id.toolbar);
        title = toolbar.findViewById(R.id.title);
        logout = toolbar.findViewById(R.id.logoutBtn);

        dialog = new SignOut(this);

        title.setText("I-HeLP | Update Profile");

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

        nameET = findViewById(R.id.nameET);
        emailET = findViewById(R.id.emailET);
        passwordET = findViewById(R.id.passwordET);
        maleRB = findViewById(R.id.maleRB);
        femaleRB = findViewById(R.id.femaleRB);
        updateBtn = findViewById(R.id.updateBtn);

        fUser = mAuth.getCurrentUser();
        id = fUser.getUid();

        Query query = databaseReference.child(id);

        query.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    DataSnapshot snapshot = task.getResult();
                    String nameDB = snapshot.child("name").getValue().toString();
                    emailDB = snapshot.child("email").getValue().toString();
                    String genderDB = snapshot.child("gender").getValue().toString();
                    passwordDB = snapshot.child("password").getValue().toString();

                    nameET.setText(nameDB);
                    emailET.setText(emailDB);
                    passwordET.setText(passwordDB);

                    if(genderDB.equals("male")){
                        maleRB.setSelected(true);
                    }else if(genderDB.equals("female")){
                        femaleRB.setSelected(true);
                    }
                }
            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameET.getText().toString();
                String email = emailET.getText().toString();
                String password = passwordET.getText().toString();
                gender = " ";

                if(maleRB.isChecked()){
                    gender= "male";
                }else if(femaleRB.isChecked()){
                    gender= "female";
                }

                if (name.isEmpty()){
                    Toast.makeText(AdminUpdateProfile.this,"Fill in the details!",Toast.LENGTH_LONG).show();
                }else if (email.isEmpty()){
                    Toast.makeText(AdminUpdateProfile.this,"Fill in the details!",Toast.LENGTH_LONG).show();
                }else if (password.isEmpty()){
                    Toast.makeText(AdminUpdateProfile.this,"Fill in the details!",Toast.LENGTH_LONG).show();
                }else if (gender.isEmpty()){
                    Toast.makeText(AdminUpdateProfile.this,"Fill in the details!",Toast.LENGTH_LONG).show();
                }else{
                    DoctorModel user = new DoctorModel(name,email,password,gender,id);
                    databaseReference.child(id).setValue(user);

                    if(!(email.equals(emailDB))){
                        fUser.updateEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(AdminUpdateProfile.this, "Email Successfuly Updated", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(AdminUpdateProfile.this, "Email Update Failed", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }

                    if(!(password.equals(passwordDB))){
                        fUser.updatePassword(password).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(AdminUpdateProfile.this, "Password Successfuly Updated", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(AdminUpdateProfile.this, "Password Update Failed", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    Toast.makeText(AdminUpdateProfile.this,"Update Profile Successful!",Toast.LENGTH_LONG).show();

                }
            }
        });
    }
}