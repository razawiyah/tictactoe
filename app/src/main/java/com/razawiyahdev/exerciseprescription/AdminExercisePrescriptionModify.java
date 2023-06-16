package com.razawiyahdev.exerciseprescription;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.razawiyahdev.exerciseprescription.R;
import com.razawiyahdev.exerciseprescription.class2.DoctorEPModel;
import com.razawiyahdev.exerciseprescription.class2.EPmodel;
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

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class AdminExercisePrescriptionModify extends AppCompatActivity {

    EditText nameET,weekET,durationET,intensityET,aerobicET,strengthET,flexibilityET,noteET;
    Button submitBtn;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://exerciseprescription-c1b89-default-rtdb.firebaseio.com/");
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser fUser;
    String id,patientId,timeStamp,date;

    public static final String PATIENT_ID = "PATIENTID";

    ZoneId zoneId = ZoneId.of("Asia/Kuala_Lumpur");
    ZonedDateTime zonedDateTime = ZonedDateTime.now(zoneId);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    String formattedDate = zonedDateTime.format(formatter);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_exercise_prescription_modify);

        nameET = findViewById(R.id.nameET);
        weekET = findViewById(R.id.weekET);
        durationET= findViewById(R.id.durationET);
        intensityET = findViewById(R.id.intensityET);
        aerobicET = findViewById(R.id.aerobicET);
        strengthET = findViewById(R.id.strengthET);
        flexibilityET = findViewById(R.id.flexibilityET);
        noteET = findViewById(R.id.noteET);
        submitBtn = findViewById(R.id.submitBtn);

        fUser = mAuth.getCurrentUser();
        id = fUser.getUid();

        Intent intent = getIntent();
        patientId  = intent.getStringExtra(RecyclerAdapter3.PATIENT_ID);
        timeStamp = intent.getStringExtra(RecyclerAdapter3.TIMESTAMP);

        nameET.setFocusable(false);
        nameET.setFocusableInTouchMode(false);

        Query query=FirebaseDatabase.getInstance().getReference("ExercisePrescription").child(id).child(patientId).child(timeStamp);

        query.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    DataSnapshot snapshot = task.getResult();
                    String name = snapshot.child("name").getValue().toString();
                    String week = snapshot.child("week").getValue().toString();
                    String duration = snapshot.child("duration").getValue().toString();
                    String intensity = snapshot.child("intensity").getValue().toString();
                    String aerobic = snapshot.child("aerobic").getValue().toString();
                    String strength = snapshot.child("strength").getValue().toString();
                    String flexibility = snapshot.child("flexibility").getValue().toString();
                    String note = snapshot.child("note").getValue().toString();
                    String timeStampOld = snapshot.child("timeStamp").getValue().toString();
                    String dateOld = snapshot.child("date").getValue().toString();

                    nameET.setText(name);
                    weekET.setText(week);
                    durationET.setText(duration);
                    intensityET.setText(intensity);
                    aerobicET.setText(aerobic);
                    strengthET.setText(strength);
                    flexibilityET.setText(flexibility);
                    noteET.setText(note);
                    timeStamp = timeStampOld;
                    date = dateOld;
                }
            }
        });

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
                    Toast.makeText(AdminExercisePrescriptionModify.this,"Fill in the details!",Toast.LENGTH_LONG).show();
                }else if (week.isEmpty()){
                    Toast.makeText(AdminExercisePrescriptionModify.this,"Fill in the details!",Toast.LENGTH_LONG).show();
                }else if (duration.isEmpty()){
                    Toast.makeText(AdminExercisePrescriptionModify.this,"Fill in the details!",Toast.LENGTH_LONG).show();
                }else if (intensity.isEmpty()){
                    Toast.makeText(AdminExercisePrescriptionModify.this,"Fill in the details!",Toast.LENGTH_LONG).show();
                }else if (aerobic.isEmpty()){
                    Toast.makeText(AdminExercisePrescriptionModify.this,"Fill in the details!",Toast.LENGTH_LONG).show();
                }else if (strength.isEmpty()){
                    Toast.makeText(AdminExercisePrescriptionModify.this,"Fill in the details!",Toast.LENGTH_LONG).show();
                }else if (flexibility.isEmpty()){
                    Toast.makeText(AdminExercisePrescriptionModify.this,"Fill in the details!",Toast.LENGTH_LONG).show();
                }else if (note.isEmpty()){
                    Toast.makeText(AdminExercisePrescriptionModify.this,"Fill in the details!",Toast.LENGTH_LONG).show();
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
                                    databaseReference.child("ExercisePrescription").child(id).child(ptId).child(timeStamp).setValue(prescription);

                                    Query queryDocEP = databaseReference.child("DoctorEP").child(id).child(ptId);

                                    queryDocEP.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            if (!(snapshot.exists())) {
                                                DoctorEPModel model = new DoctorEPModel(ptId,name);
                                                databaseReference.child("DoctorEP").child(id).child(ptId).setValue(model);
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });



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

                                    Toast.makeText(AdminExercisePrescriptionModify.this,"Prescription Saved!",Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(AdminExercisePrescriptionModify.this, AdminHomepage.class));
                                    finish();

                                }
                            }else{
                                Toast.makeText(AdminExercisePrescriptionModify.this,"Patient Doesn't Exist! Please ask patient to register the app!",Toast.LENGTH_LONG).show();
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
        Intent intent2 = new Intent(AdminExercisePrescriptionModify.this,AdminProgressChartOption.class);
        intent2.putExtra(PATIENT_ID,patientId);
        startActivity(intent2);
    }
}