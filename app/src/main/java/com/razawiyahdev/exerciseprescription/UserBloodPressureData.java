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
import com.razawiyahdev.exerciseprescription.class2.BloodPressureModel;
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

public class UserBloodPressureData extends AppCompatActivity {

    EditText dateET,systolicET,diastolicET;
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

    DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("d");
    String dayDate = zonedDateTime.format(formatterDate);

    DateTimeFormatter formatterMonth = DateTimeFormatter.ofPattern("MMMM");
    String month = zonedDateTime.format(formatterMonth);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_blood_pressure_data);

        dateET = findViewById(R.id.dateET);
        systolicET = findViewById(R.id.systolicET);
        diastolicET = findViewById(R.id.diastolicET);

        submitBtn2 = findViewById(R.id.submitBtn2);

        fUser = mAuth.getCurrentUser();
        id = fUser.getUid();

        dateET.setText(formattedDate);
        dateET.setFocusable(false);
        dateET.setFocusableInTouchMode(false);

        submitBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = dateET.getText().toString();
                String dateS = date.replaceAll("[^\\d]", "");
                String systolic = systolicET.getText().toString();
                String diastolic = diastolicET.getText().toString();

                if (date.isEmpty()){
                    Toast.makeText(UserBloodPressureData.this,"Fill in the details!",Toast.LENGTH_LONG).show();
                }else if (systolic.isEmpty()){
                    Toast.makeText(UserBloodPressureData.this,"Fill in the details!",Toast.LENGTH_LONG).show();
                }else if (diastolic.isEmpty()){
                    Toast.makeText(UserBloodPressureData.this,"Fill in the details!",Toast.LENGTH_LONG).show();
                }else{

                    databaseReference.child("BloodPressureM").child(id).addListenerForSingleValueEvent(new ValueEventListener() {
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
                                        BloodPressureModel model = new BloodPressureModel(day, "0","0");

                                        // set the value in the database
                                        databaseReference.child("BloodPressureM").child(id).child(month).child(day).setValue(model);
                                    }
                                }
                            }

                            //by month data
                            BloodPressureModel modelMonth = new BloodPressureModel(dayDate,systolic,diastolic);
                            databaseReference.child("BloodPressureM").child(id).child(month).child(dayDate).setValue(modelMonth);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    BloodPressureModel model = new BloodPressureModel(date,systolic,diastolic,id);
                    databaseReference.child("BloodPressure").child(id).child(dateS).setValue(model);
                    Toast.makeText(UserBloodPressureData.this,"Data Saved!",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(UserBloodPressureData.this, UserHealthData.class));
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
        startActivity(new Intent(UserBloodPressureData.this, UserHealthData.class));
        finish();
    }
}