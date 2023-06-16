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
import com.razawiyahdev.exerciseprescription.class2.HeartRateModel;
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

public class UserHeartRateData extends AppCompatActivity {

    EditText dateET,resthrET,peakhrET;
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
        setContentView(R.layout.activity_user_heart_rate_data);

        dateET = findViewById(R.id.dateET);
        resthrET = findViewById(R.id.resthrET);
        peakhrET = findViewById(R.id.peakhrET);

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
                String resthr = resthrET.getText().toString();
                String peakhr = peakhrET.getText().toString();


                if (date.isEmpty()){
                    Toast.makeText(UserHeartRateData.this,"Fill in the details!",Toast.LENGTH_LONG).show();
                }else if (resthr.isEmpty()){
                    Toast.makeText(UserHeartRateData.this,"Fill in the details!",Toast.LENGTH_LONG).show();
                }else if (peakhr.isEmpty()){
                    Toast.makeText(UserHeartRateData.this,"Fill in the details!",Toast.LENGTH_LONG).show();
                }else{

                    databaseReference.child("HeartRateM").child(id).addListenerForSingleValueEvent(new ValueEventListener() {
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
                                        HeartRateModel model = new HeartRateModel(day, "0","0");

                                        // set the value in the database
                                        databaseReference.child("HeartRateM").child(id).child(month).child(day).setValue(model);
                                    }
                                }
                            }

                            //by month data
                            HeartRateModel modelMonth = new HeartRateModel(dayDate,resthr,peakhr);
                            databaseReference.child("HeartRateM").child(id).child(month).child(dayDate).setValue(modelMonth);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                    HeartRateModel model = new HeartRateModel(date,resthr,peakhr,id);
                    databaseReference.child("HeartRate").child(id).child(dateS).setValue(model);
                    Toast.makeText(UserHeartRateData.this,"Data Saved!",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(UserHeartRateData.this, UserHealthData.class));
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
        startActivity(new Intent(UserHeartRateData.this, UserHealthData.class));
        finish();
    }
}