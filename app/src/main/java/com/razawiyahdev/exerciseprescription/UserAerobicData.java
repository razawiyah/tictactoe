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
import com.razawiyahdev.exerciseprescription.class2.AerobicModel;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormatSymbols;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class UserAerobicData extends AppCompatActivity {

    EditText dateET,typeOtherET,rpeET,noteET2;
    Button submitBtn2;
    DatabaseReference   databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://exerciseprescription-c1b89-default-rtdb.firebaseio.com/");
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser fUser;
    String id;
    String timeStamp = String.valueOf(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()));

    ZoneId zoneId = ZoneId.of("Asia/Kuala_Lumpur");
    ZonedDateTime zonedDateTime = ZonedDateTime.now(zoneId);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    String formattedDate = zonedDateTime.format(formatter);

    TextInputLayout typeET,intensityET,durationET;
    AutoCompleteTextView typeATV,intensityATV,durationATV;
    ArrayAdapter<String> typeAdapter,intensityAdapter,durationAdapter;
    String[] typeArray = {"Walking/Hiking","Jogging/Running","Cycling","Swimming","Sports","Others"};
    String[] durationArray = {"05 mins","10 mins","15 mins","20 mins","30 mins","45 mins","60 mins"};
    String[] intensityArray = {"Low","Moderate","High"};

    DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("d");
    String dayDate = zonedDateTime.format(formatterDate);

    DateTimeFormatter formatterMonth = DateTimeFormatter.ofPattern("MMMM");
    String month = zonedDateTime.format(formatterMonth);




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_aerobic_data);

        dateET = findViewById(R.id.dateET);
        typeET = findViewById(R.id.typeET);
        typeOtherET = findViewById(R.id.typeOtherET);
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

        typeOtherET.setFocusable(false);
        typeOtherET.setFocusableInTouchMode(false);

        //dropdown type
        typeATV = findViewById(R.id.typeData);
        typeATV.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                // Check if the EditText has focus
                if (hasFocus) {
                    // If the EditText has focus, set the hint to an empty string
                    typeET.setHint("");
                }
            }
        });

        typeAdapter = new ArrayAdapter<String>(UserAerobicData.this, R.layout.dropdown_item,typeArray);
        typeATV.setAdapter(typeAdapter);
        typeATV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();

                //show success selection
                Toast.makeText(UserAerobicData.this, "Type: "+ item, Toast.LENGTH_SHORT).show();

                if(item.equals("Others")){
                    typeOtherET.setFocusable(true);
                    typeOtherET.setFocusableInTouchMode(true);
                    typeOtherET.setBackgroundResource(R.drawable.custom_input_maroon);
                }else {
                    typeOtherET.setFocusable(false);
                    typeOtherET.setFocusableInTouchMode(false);
                }
            }
        });

        //dropdown duration
        durationATV = findViewById(R.id.durationData);
        durationATV.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                // Check if the EditText has focus
                if (hasFocus) {
                    // If the EditText has focus, set the hint to an empty string
                    durationET.setHint("");
                }
            }
        });

        durationAdapter = new ArrayAdapter<String>(UserAerobicData.this, R.layout.dropdown_item,durationArray);
        durationATV.setAdapter(durationAdapter);
        durationATV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();

                //show success selection
                Toast.makeText(UserAerobicData.this, "Duration: "+ item, Toast.LENGTH_SHORT).show();
            }
        });

        //dropdown duration
        intensityATV = findViewById(R.id.intensityData);
        intensityATV.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                // Check if the EditText has focus
                if (hasFocus) {
                    // If the EditText has focus, set the hint to an empty string
                    intensityET.setHint("");
                }
            }
        });

        intensityAdapter = new ArrayAdapter<String>(UserAerobicData.this, R.layout.dropdown_item,intensityArray);
        intensityATV.setAdapter(intensityAdapter);
        intensityATV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();

                //show success selection
                Toast.makeText(UserAerobicData.this, "Intensity: "+ item, Toast.LENGTH_SHORT).show();
            }
        });



        submitBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String type="";
                String date = dateET.getText().toString();
                String dateS = date.replaceAll("[^\\d]", "");
                String intensity = intensityATV.getText().toString();
                String durString = durationATV.getText().toString();
                String duration = durString.substring(0, 2);
                String rpe = rpeET.getText().toString();
                String note = noteET2.getText().toString();

                if((typeOtherET.getText().toString()).isEmpty()){
                    type = typeATV.getText().toString();
                }else {
                    type = typeOtherET.getText().toString();

                }

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

                    databaseReference.child("AerobicM").child(id).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(!snapshot.exists()){
                                for (int m = 1; m <= 12; m++) {
                                    // set the month name
                                    String month = getMonthName(m);

                                    // get the number of days in the month
                                    int daysInMonth = getDaysInMonth(m);

                                    // loop through all days in the month
                                    for (int d = 1; d <= daysInMonth; d++) {
                                        // set the day string with leading zero if necessary
                                        String day = (d < 10) ? "0" + d : "" + d;

                                        // create a new WeightModel with 0 weight value
                                        AerobicModel model = new AerobicModel(day, "0");

                                        // set the value in the database
                                        databaseReference.child("AerobicM").child(id).child(month).child(day).setValue(model);
                                    }
                                }
                            }

                            //by month data
                            AerobicModel modelMonth = new AerobicModel(dayDate,duration);
                            databaseReference.child("AerobicM").child(id).child(month).child(dayDate).setValue(modelMonth);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    AerobicModel model = new AerobicModel(date,type,intensity,duration,rpe,note,id);
                    databaseReference.child("Aerobic").child(id).child(dateS).setValue(model);
                    Toast.makeText(UserAerobicData.this,"Data Saved!",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(UserAerobicData.this, UserExerciseData.class));
                    finish();
                }
            }
        });
    }

    private static String getMonthName(int month) {
        return new DateFormatSymbols().getMonths()[month-1];
    }

    private static int getDaysInMonth(int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, month-1);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(UserAerobicData.this, UserExerciseData.class));
        finish();
    }
}