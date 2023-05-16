package com.example.exerciseprescription;

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

import com.example.exerciseprescription.class2.AerobicModel;
import com.example.exerciseprescription.class2.StrengthModel;
import com.example.exerciseprescription.class2.WeightModel;
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

public class UserStrengthData extends AppCompatActivity {

    EditText dateET,noteET2;
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

    TextInputLayout typeET,durationET,repsetET,intensityET;
    AutoCompleteTextView typeATV,durationATV,repsetATV,intensityATV;
    ArrayAdapter<String> typeAdapter,durationAdapter,repsetAdapter,intensityAdapter;
    String[] typeArray = {"Body weight","Free weight/Dumbell","Elastic band","Machine weight"};
    String[] durationArray = {"05 mins","10 mins","15 mins","20 mins","30 mins","45 mins","60 mins"};
    String[] repsetArray = {"8-10 x 1","8-10 x 2","8-10 x 3","8-10 x 4","8-10 x 5","8-10 x >6"};

    String[] intensityArray = {"Low","Moderate","High"};


    DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("d");
    String dayDate = zonedDateTime.format(formatterDate);

    DateTimeFormatter formatterMonth = DateTimeFormatter.ofPattern("MMMM");
    String month = zonedDateTime.format(formatterMonth);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_strength_data);

        dateET = findViewById(R.id.dateET);
        typeET = findViewById(R.id.typeET);
        intensityET = findViewById(R.id.intensityET);
        repsetET = findViewById(R.id.repsetET);
        durationET = findViewById(R.id.durationET);
        noteET2 = findViewById(R.id.noteET2);
        submitBtn2 = findViewById(R.id.submitBtn2);

        fUser = mAuth.getCurrentUser();
        id = fUser.getUid();

        dateET.setText(formattedDate);
        dateET.setFocusable(false);
        dateET.setFocusableInTouchMode(false);


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

        typeAdapter = new ArrayAdapter<String>(UserStrengthData.this, R.layout.dropdown_item,typeArray);
        typeATV.setAdapter(typeAdapter);
        typeATV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();

                //show success selection
                Toast.makeText(UserStrengthData.this, "Type: "+ item, Toast.LENGTH_SHORT).show();
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

        durationAdapter = new ArrayAdapter<String>(UserStrengthData.this, R.layout.dropdown_item,durationArray);
        durationATV.setAdapter(durationAdapter);
        durationATV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();

                //show success selection
                Toast.makeText(UserStrengthData.this, "Duration: "+ item, Toast.LENGTH_SHORT).show();
            }
        });

        //dropdown repset
        repsetATV = findViewById(R.id.repsetData);
        repsetATV.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                // Check if the EditText has focus
                if (hasFocus) {
                    // If the EditText has focus, set the hint to an empty string
                    repsetET.setHint("");
                }
            }
        });

        repsetAdapter = new ArrayAdapter<String>(UserStrengthData.this, R.layout.dropdown_item,repsetArray);
        repsetATV.setAdapter(repsetAdapter);
        repsetATV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();

                //show success selection
                Toast.makeText(UserStrengthData.this, "Repetitions and Sets: "+ item, Toast.LENGTH_SHORT).show();
            }
        });

        //dropdown intensity
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

        intensityAdapter = new ArrayAdapter<String>(UserStrengthData.this, R.layout.dropdown_item,intensityArray);
        intensityATV.setAdapter(intensityAdapter);
        intensityATV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();

                //show success selection
                Toast.makeText(UserStrengthData.this, "Intensity: "+ item, Toast.LENGTH_SHORT).show();
            }
        });

        submitBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = dateET.getText().toString();
                String dateS = date.replaceAll("[^\\d]", "");
                String type = typeATV.getText().toString();
                String intensity = intensityATV.getText().toString();
                String repset = repsetATV.getText().toString();
                String durString = durationATV.getText().toString();
                String duration = durString.substring(0, 2);
                String note = noteET2.getText().toString();

                if (date.isEmpty()){
                    Toast.makeText(UserStrengthData.this,"Fill in the details!",Toast.LENGTH_LONG).show();
                }else if (type.isEmpty()){
                    Toast.makeText(UserStrengthData.this,"Fill in the details!",Toast.LENGTH_LONG).show();
                }else if (intensity.isEmpty()){
                    Toast.makeText(UserStrengthData.this,"Fill in the details!",Toast.LENGTH_LONG).show();
                }else if (repset.isEmpty()){
                    Toast.makeText(UserStrengthData.this,"Fill in the details!",Toast.LENGTH_LONG).show();
                }else if (duration.isEmpty()){
                    Toast.makeText(UserStrengthData.this,"Fill in the details!",Toast.LENGTH_LONG).show();
                }else if (note.isEmpty()){
                    Toast.makeText(UserStrengthData.this,"Fill in the details!",Toast.LENGTH_LONG).show();
                }else{

                    databaseReference.child("StrengthM").child(id).addListenerForSingleValueEvent(new ValueEventListener() {
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
                                        StrengthModel model = new StrengthModel(day, "0");

                                        // set the value in the database
                                        databaseReference.child("StrengthM").child(id).child(month).child(day).setValue(model);
                                    }
                                }
                            }

                            //by month data
                            StrengthModel modelMonth = new StrengthModel(dayDate,duration);
                            databaseReference.child("StrengthM").child(id).child(month).child(dayDate).setValue(modelMonth);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                    StrengthModel model = new StrengthModel(date,type,intensity,repset,duration,note,id);
                    databaseReference.child("Strength").child(id).child(dateS).setValue(model);
                    Toast.makeText(UserStrengthData.this,"Data Saved!",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(UserStrengthData.this, UserExerciseData.class));
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
        startActivity(new Intent(UserStrengthData.this, UserExerciseData.class));
        finish();
    }
}