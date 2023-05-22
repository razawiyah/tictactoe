package com.example.exerciseprescription;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.exerciseprescription.class2.AerobicModel;
import com.example.exerciseprescription.class2.StrengthModel;
import com.example.exerciseprescription.class2.WeightModel;
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
import java.util.Arrays;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class UserWeightData extends AppCompatActivity {

    EditText dateET,weightET;
    Button submitBtn2;
    DatabaseReference   databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://exerciseprescription-c1b89-default-rtdb.firebaseio.com/");
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser fUser;
    String id;
//    String timeStamp = String.valueOf(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()));

    ZoneId zoneId = ZoneId.of("Asia/Kuala_Lumpur");
    ZonedDateTime zonedDateTime = ZonedDateTime.now(zoneId);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    String formattedDate = zonedDateTime.format(formatter);

    DateTimeFormatter formatterDay = DateTimeFormatter.ofPattern("EEEE");
    String dayOfWeek = zonedDateTime.format(formatterDay);

    DateTimeFormatter formatterMonth = DateTimeFormatter.ofPattern("MMMM");
    String month = zonedDateTime.format(formatterMonth);

    DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("d");
    String dayDate = zonedDateTime.format(formatterDate);

    Calendar calendar = Calendar.getInstance();
    String dayOfWeekNum = Integer.toString((calendar.get(Calendar.DAY_OF_WEEK))-1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_weight_data);

        dateET = findViewById(R.id.dateET);
        weightET = findViewById(R.id.weightET);
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
                String weight = weightET.getText().toString();

                if (date.isEmpty()){
                    Toast.makeText(UserWeightData.this,"Fill in the details!",Toast.LENGTH_LONG).show();
                }else if (weight.isEmpty()){
                    Toast.makeText(UserWeightData.this,"Fill in the details!",Toast.LENGTH_LONG).show();
                }else{

/*
                    databaseReference.child("WeightD").child(id).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(!snapshot.exists()){
                                String[] days = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
                                for (String day : days) {
                                    WeightModel model = new WeightModel(day, "0");
                                    String dayNum = Integer.toString(Arrays.asList(days).indexOf(day)+1);
                                    databaseReference.child("WeightD").child(id).child(dayNum).setValue(model);
                                }
                            }

                            //by week data
                            String day = dayOfWeek.substring(0,3);
                            WeightModel modelDay = new WeightModel(day,weight);
                            databaseReference.child("WeightD").child(id).child(dayOfWeekNum).setValue(modelDay);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
*/

                    WeightModel model = new WeightModel(date,weight);
                    databaseReference.child("Weight").child(id).child(dateS).setValue(model);

                    databaseReference.child("WeightM").child(id).addListenerForSingleValueEvent(new ValueEventListener() {
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
                                        WeightModel model = new WeightModel(day, "0");

                                        // set the value in the database
                                        databaseReference.child("WeightM").child(id).child(month).child(day).setValue(model);
                                    }
                                }
                            }

                            //by month data
                            WeightModel modelMonth = new WeightModel(dayDate,weight);
                            databaseReference.child("WeightM").child(id).child(month).child(dayDate).setValue(modelMonth);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    databaseReference.child("User").child(id).child("weight").setValue(weight);
                    Toast.makeText(UserWeightData.this,"Data Saved!",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(UserWeightData.this, UserHealthData.class));
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
        startActivity(new Intent(UserWeightData.this, UserHealthData.class));
        finish();
    }
}