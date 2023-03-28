package com.example.exerciseprescription;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.exerciseprescription.class2.DoctorEPModel;
import com.example.exerciseprescription.class2.DoctorModel;
import com.example.exerciseprescription.class2.EPmodel;
import com.example.exerciseprescription.class2.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class AdminExercisePrescription extends AppCompatActivity {

    EditText nameET,weekET,durationET,intensityET,aerobicET,strengthET,flexibilityET,noteET;
    Button submitBtn;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://exerciseprescription-c1b89-default-rtdb.firebaseio.com/");
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser fUser;
    String id;
    String timeStamp = String.valueOf(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()));

    Date c = Calendar.getInstance().getTime();

    SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
    String formattedDate = df.format(c);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_exercise_prescription);

        nameET = findViewById(R.id.nameET);
        weekET = findViewById(R.id.weekET);
        durationET= findViewById(R.id.durationET);
        intensityET = findViewById(R.id.intensityET);
        aerobicET = findViewById(R.id.aerobicET);
        strengthET = findViewById(R.id.strengthET);
        flexibilityET = findViewById(R.id.flexibilityET);
        noteET = findViewById(R.id.noteET);
        submitBtn = findViewById(R.id.submitBtn);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameET.getText().toString();
                String week = weekET.getText().toString();
                String duration = durationET.getText().toString();
                String intensity = intensityET.getText().toString();
                String aerobic = aerobicET.getText().toString();
                String strength = strengthET.getText().toString();
                String flexibility = flexibilityET.getText().toString();
                String note = noteET.getText().toString();

                if (name.isEmpty()){
                    Toast.makeText(AdminExercisePrescription.this,"Fill in the details!",Toast.LENGTH_LONG).show();
                }else if (week.isEmpty()){
                    Toast.makeText(AdminExercisePrescription.this,"Fill in the details!",Toast.LENGTH_LONG).show();
                }else if (duration.isEmpty()){
                    Toast.makeText(AdminExercisePrescription.this,"Fill in the details!",Toast.LENGTH_LONG).show();
                }else if (intensity.isEmpty()){
                    Toast.makeText(AdminExercisePrescription.this,"Fill in the details!",Toast.LENGTH_LONG).show();
                }else if (aerobic.isEmpty()){
                    Toast.makeText(AdminExercisePrescription.this,"Fill in the details!",Toast.LENGTH_LONG).show();
                }else if (strength.isEmpty()){
                    Toast.makeText(AdminExercisePrescription.this,"Fill in the details!",Toast.LENGTH_LONG).show();
                }else if (flexibility.isEmpty()){
                    Toast.makeText(AdminExercisePrescription.this,"Fill in the details!",Toast.LENGTH_LONG).show();
                }else if (note.isEmpty()){
                    Toast.makeText(AdminExercisePrescription.this,"Fill in the details!",Toast.LENGTH_LONG).show();
                }else{
                    Query query = FirebaseDatabase.getInstance().getReference("User").orderByChild("name").equalTo(name);

                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){
                                for(DataSnapshot userSnapshot : snapshot.getChildren()){
                                    String ptId = userSnapshot.getKey();

                                    fUser = mAuth.getCurrentUser();
                                    id = fUser.getUid();

                                    EPmodel prescription = new EPmodel(name,week,duration,intensity,aerobic,strength,flexibility,note,ptId,id,timeStamp,formattedDate);
                                    databaseReference.child("ExercisePrescription").child(id).child(ptId).setValue(prescription);

                                    DoctorEPModel model = new DoctorEPModel(ptId,name);
                                    databaseReference.child("DoctorEP").child(id).child(ptId).setValue(model);

                                    Query query2 = databaseReference.child("Doctor").child(id);
                                    query2.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                                            if(task.isSuccessful()){
                                                DataSnapshot snapshot = task.getResult();
                                                String nameDr = snapshot.child("name").getValue().toString();
                                                String emailDr = snapshot.child("email").getValue().toString();

                                                EPmodel prescriptionUser = new EPmodel(name,week,duration,intensity,aerobic,strength,flexibility,note,ptId,id,formattedDate,nameDr,emailDr);
                                                databaseReference.child("UserEP").child(ptId).child(timeStamp).setValue(prescriptionUser);

                                            }

                                        }
                                    });

                                    Toast.makeText(AdminExercisePrescription.this,"Prescription Saved!",Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(AdminExercisePrescription.this, AdminHomepage.class));
                                    finish();

                                }
                            }else{
                                Toast.makeText(AdminExercisePrescription.this,"Patient Doesn't Exist! Please ask patient to register the app!",Toast.LENGTH_LONG).show();
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(AdminExercisePrescription.this, AdminHomepage.class));
        finish();
    }
}