package com.razawiyahdev.exerciseprescription;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.razawiyahdev.exerciseprescription.R;
import com.razawiyahdev.exerciseprescription.class2.DoctorEPModel;
import com.razawiyahdev.exerciseprescription.class2.EPmodel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
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
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class AdminExercisePrescription extends AppCompatActivity {

    EditText weekET,durationET,intensityET,aerobicET,strengthET,flexibilityET,noteET;
    Button submitBtn;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://exerciseprescription-c1b89-default-rtdb.firebaseio.com/");
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser fUser;
    String id;
    String timeStamp = String.valueOf(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()));

    ZoneId zoneId = ZoneId.of("Asia/Kuala_Lumpur");
    ZonedDateTime zonedDateTime = ZonedDateTime.now(zoneId);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    String formattedDate = zonedDateTime.format(formatter);

    List<String> ptName = new ArrayList<>();

    TextInputLayout nameET;
    AutoCompleteTextView nameATV;
    ArrayAdapter<String> adapterItems;


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

        //set userType
        Query queryName = databaseReference.child("User");
        queryName.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    String name = dataSnapshot.child("name").getValue().toString();
                    ptName.add(name);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //dropdown user type
        nameATV = findViewById(R.id.userType);
        adapterItems = new ArrayAdapter<String>(AdminExercisePrescription.this, R.layout.dropdown_item,ptName);
        nameATV.setAdapter(adapterItems);

        nameATV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();

                //show success selection
                Toast.makeText(AdminExercisePrescription.this, "User Type: "+ item, Toast.LENGTH_SHORT).show();
            }
        });

/*
        nameATV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                String truncatedName = truncateText(item, 20);  // Adjust the length as needed

                // Show success selection with truncated name
                Toast.makeText(AdminExercisePrescription.this, "User Type: " + truncatedName, Toast.LENGTH_SHORT).show();
            }
        });
*/

        nameATV.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                // Check if the EditText has focus
                if (hasFocus) {
                    // If the EditText has focus, set the hint to an empty string
                    nameET.setHint("");
                }
            }
        });


        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameATV.getText().toString();
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

    private String truncateText(String text, int maxLength) {
        if (text.length() > maxLength) {
            return text.substring(0, maxLength - 3) + "...";  // Truncate and add ellipsis
        }
        return text;
    }

}