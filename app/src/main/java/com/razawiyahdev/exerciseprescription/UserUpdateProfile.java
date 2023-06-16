package com.razawiyahdev.exerciseprescription;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.razawiyahdev.exerciseprescription.R;
import com.razawiyahdev.exerciseprescription.class2.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.Calendar;

public class UserUpdateProfile extends AppCompatActivity {

    View toolbar;
    TextView title;
    ImageView logout,menu;
    public SignOut dialog;

    EditText nameET,dobET,heightET,weightET,emailET,passwordET;
    RadioButton maleRB, femaleRB;
    Button updateBtn;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User");
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser fUser;
    String id,gender,emailDB,passwordDB,safetyCheck;

    DrawerLayout drawerLayout;
    LinearLayout homepageBtn,messageBtn,aboutBtn,updatePBtn;
    TextView name,name2,age,dob,height,weight,genderTV,email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_update_profile);

        toolbar = findViewById(R.id.toolbar);
        title = toolbar.findViewById(R.id.title);
        logout = toolbar.findViewById(R.id.logoutBtn);
        menu = toolbar.findViewById(R.id.menu);

        dialog = new SignOut(this);

        title.setText("I-HeLP | Update Profile");

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

        nameET = findViewById(R.id.nameET);
        dobET = findViewById(R.id.dobET);
        heightET = findViewById(R.id.heightET);
        weightET = findViewById(R.id.weightET);
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
                    String dobDB = snapshot.child("dob").getValue().toString();
                    String heightDB = snapshot.child("height").getValue().toString();
                    String weightDB = snapshot.child("weight").getValue().toString();
                    String safetycheckDB = snapshot.child("safetycheck").getValue().toString();

                    nameET.setText(nameDB);
                    dobET.setText(dobDB);
                    heightET.setText(heightDB);
                    weightET.setText(weightDB);

                    safetyCheck = safetycheckDB;

                    emailET.setText(emailDB);
                    passwordET.setText(passwordDB);

                    if(genderDB.equals("male")){
                        maleRB.setChecked(true);
                    }else if(genderDB.equals("female")){
                        femaleRB.setChecked(true);
                    }
                }
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

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameET.getText().toString();
                String dob = dobET.getText().toString();
                String height = heightET.getText().toString();
                String weight = weightET.getText().toString();
                String email = emailET.getText().toString();
                String password = passwordET.getText().toString();
                gender = " ";

                if(maleRB.isChecked()){
                    gender= "male";
                }else if(femaleRB.isChecked()){
                    gender= "female";
                }

                if (name.isEmpty()){
                    Toast.makeText(UserUpdateProfile.this,"Fill in the details!",Toast.LENGTH_LONG).show();
                }else if (dob.isEmpty()){
                    Toast.makeText(UserUpdateProfile.this,"Fill in the details!",Toast.LENGTH_LONG).show();
                }else if (height.isEmpty()){
                    Toast.makeText(UserUpdateProfile.this,"Fill in the details!",Toast.LENGTH_LONG).show();
                }else if (weight.isEmpty()){
                    Toast.makeText(UserUpdateProfile.this,"Fill in the details!",Toast.LENGTH_LONG).show();
                }else if (email.isEmpty()){
                    Toast.makeText(UserUpdateProfile.this,"Fill in the details!",Toast.LENGTH_LONG).show();
                }else if (password.isEmpty()){
                    Toast.makeText(UserUpdateProfile.this,"Fill in the details!",Toast.LENGTH_LONG).show();
                }else if (gender.isEmpty()){
                    Toast.makeText(UserUpdateProfile.this,"Fill in the details!",Toast.LENGTH_LONG).show();
                }else if(!(email.equals(emailDB)) && !(password.equals(passwordDB))){
                    Toast.makeText(UserUpdateProfile.this,"Email & Password cannot be changed at the same time!",Toast.LENGTH_LONG).show();
                }else{
                    UserModel user = new UserModel(name,dob,height,weight,email,password,gender,safetyCheck,id);
                    databaseReference.child(id).setValue(user);

                    if(!(email.equals(emailDB))){
                        fUser.updateEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                emailDB=email;
                                Toast.makeText(UserUpdateProfile.this, "Email Successfuly Updated", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(UserUpdateProfile.this, "Email Update Failed", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }

                    if(!(password.equals(passwordDB))){
                        fUser.updatePassword(password).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                passwordDB=password;
                                Toast.makeText(UserUpdateProfile.this, "Password Successfuly Updated", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(UserUpdateProfile.this, "Password Update Failed", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    Toast.makeText(UserUpdateProfile.this,"Update Profile Successful!",Toast.LENGTH_LONG).show();
                }
            }
        });

        drawerLayout=findViewById(R.id.drawer);
        name = drawerLayout.findViewById(R.id.nameTV);
        name2 = drawerLayout.findViewById(R.id.nameTV2);
        age = drawerLayout.findViewById(R.id.ageTV);
        dob = drawerLayout.findViewById(R.id.dobTV);
        height = drawerLayout.findViewById(R.id.heightTV);
        weight = drawerLayout.findViewById(R.id.weightTV);
        genderTV = drawerLayout.findViewById(R.id.genderTV);
        email = drawerLayout.findViewById(R.id.emailTV);

        homepageBtn = drawerLayout.findViewById(R.id.homepageBtn);
        messageBtn = drawerLayout.findViewById(R.id.messageBtn);
        aboutBtn = drawerLayout.findViewById(R.id.aboutBtn);
        updatePBtn = drawerLayout.findViewById(R.id.updatePBtn);

        updatePBtn.setBackgroundColor(ContextCompat.getColor(this,R.color.teal));



        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDrawer(drawerLayout);

                Query query2 = FirebaseDatabase.getInstance().getReference("User").child(id);

                query2.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if(task.isSuccessful()){
                            DataSnapshot snapshot = task.getResult();
                            String nameDB = snapshot.child("name").getValue().toString();
                            String genderDB = snapshot.child("gender").getValue().toString();
                            String emailDB = snapshot.child("email").getValue().toString();
                            String dobDB = snapshot.child("dob").getValue().toString();
                            String heightDB = snapshot.child("height").getValue().toString();
                            String weightDB = snapshot.child("weight").getValue().toString();

                            String yearStr = dobDB.substring(dobDB.length() - 4);
                            int yearOB = Integer.parseInt(yearStr);
                            Calendar calendar = Calendar.getInstance();
                            int yearCur = calendar.get(Calendar.YEAR);
                            int ageNum = yearCur-yearOB;
                            String ageDB = Integer.toString(ageNum);

                            name.setText(nameDB);
                            name2.setText(nameDB);
                            genderTV.setText(genderDB);
                            email.setText(emailDB);
                            age.setText(ageDB);
                            dob.setText(dobDB);
                            height.setText(heightDB);
                            weight.setText(weightDB);


                        }
                    }
                });
            }
        });

        messageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserUpdateProfile.this, UserMessage.class));
                finish();            }
        });

        aboutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserUpdateProfile.this, UserAbout.class));
                finish();
            }
        });

        homepageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserUpdateProfile.this, UserHomepage.class));
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(UserUpdateProfile.this, UserHomepage.class));
        finish();
    }

    public static void openDrawer(DrawerLayout drawerLayout) {

        drawerLayout.openDrawer(GravityCompat.START);

    }

    public void featureUnderProgress(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Feature is under Progress");
        builder.setNeutralButton("OK",null);
        builder.show();
    }
}