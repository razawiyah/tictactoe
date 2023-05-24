package com.example.exerciseprescription;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class UserDataView extends AppCompatActivity {

    String selectedDate,mode;

    String id;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser fUser;

    TextView title,date;

    LinearLayout textContainer;

    TextView title2,title3,title4,title5,title6,data2,data3,data4,data5,data6;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://exerciseprescription-c1b89-default-rtdb.firebaseio.com/");

    String formattedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data_view);

        selectedDate = getIntent().getStringExtra("selectedDate");
        mode = getIntent().getStringExtra("mode");

        fUser = mAuth.getCurrentUser();
        id = fUser.getUid();

        title = findViewById(R.id.title);
        date = findViewById(R.id.date);

        title.setText(mode+" Data");
        formattedDate = selectedDate.substring(0, 2) + "-" + selectedDate.substring(2, 4) + "-" + selectedDate.substring(4);
        date.setText(formattedDate);

        textContainer = findViewById(R.id.textContainer);

        title2 = findViewById(R.id.title2);
        title3 = findViewById(R.id.title3);
        title4 = findViewById(R.id.title4);
        title5 = findViewById(R.id.title5);
        title6 = findViewById(R.id.title6);

        data2 = findViewById(R.id.data2);
        data3 = findViewById(R.id.data3);
        data4 = findViewById(R.id.data4);
        data5 = findViewById(R.id.data5);
        data6 = findViewById(R.id.data6);

        Query query= databaseReference.child(mode).child(id).child(selectedDate);

        if(mode.equals("Aerobic")){
            query.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if(task.isSuccessful()){
                        DataSnapshot snapshot = task.getResult();

                        String type = snapshot.child("type").getValue().toString();
                        String intensity = snapshot.child("intensity").getValue().toString();
                        String duration = snapshot.child("duration").getValue().toString();
                        String rpe = snapshot.child("rpe").getValue().toString();
                        String note = snapshot.child("note").getValue().toString();

                        title2.setText("Type :");
                        title3.setText("Intensity :");
                        title4.setText("Duration :");
                        title5.setText("RPE :");
                        title6.setText("Note :");

                        data2.setText(type);
                        data3.setText(intensity);
                        data4.setText(duration);
                        data5.setText(rpe);
                        data6.setText(note);
                    }
                }
            });
        }else if(mode.equals("Strength")){
            query.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if(task.isSuccessful()){
                        DataSnapshot snapshot = task.getResult();

                        String type = snapshot.child("type").getValue().toString();
                        String intensity = snapshot.child("intensity").getValue().toString();
                        String duration = snapshot.child("duration").getValue().toString();
                        String repset = snapshot.child("repset").getValue().toString();
                        String note = snapshot.child("note").getValue().toString();

                        title2.setText("Type :");
                        title3.setText("Intensity :");
                        title4.setText("Duration :");
                        title5.setText("Repetition :\nand Sets");
                        title6.setText("Note :");

                        data2.setText(type);
                        data3.setText(intensity);
                        data4.setText(duration);
                        data5.setText(repset);
                        data6.setText(note);
                    }
                }
            });

        }else if(mode.equals("Flexibility")){
            query.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if(task.isSuccessful()){
                        DataSnapshot snapshot = task.getResult();

                        String type = snapshot.child("type").getValue().toString();
                        String intensity = snapshot.child("intensity").getValue().toString();
                        String duration = snapshot.child("duration").getValue().toString();
                        String repset = snapshot.child("repset").getValue().toString();
                        String note = snapshot.child("note").getValue().toString();

                        title2.setText("Type :");
                        title3.setText("Intensity :");
                        title4.setText("Duration :");
                        title5.setText("Repetition :\nand Sets");
                        title6.setText("Note :");

                        data2.setText(type);
                        data3.setText(intensity);
                        data4.setText(duration);
                        data5.setText(repset);
                        data6.setText(note);
                    }
                }
            });

        }else if(mode.equals("BloodPressure")){
            query.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if(task.isSuccessful()){
                        DataSnapshot snapshot = task.getResult();

                        String systolic = snapshot.child("systolic").getValue().toString();
                        String diastolic = snapshot.child("diastolic").getValue().toString();

                        title2.setText("Blood pressure :\n(Systolic) (mmHg)");
                        title3.setText("Blood pressure :\n(Diastolic) (mmHg)");
                        title4.setVisibility(View.INVISIBLE);
                        title5.setVisibility(View.INVISIBLE);
                        title6.setVisibility(View.INVISIBLE);

                        data2.setText(systolic);
                        data3.setText(diastolic);
                        data4.setVisibility(View.INVISIBLE);
                        data5.setVisibility(View.INVISIBLE);
                        data6.setVisibility(View.INVISIBLE);
                    }
                }
            });

        }else if(mode.equals("HeartRate")){
            query.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if(task.isSuccessful()){
                        DataSnapshot snapshot = task.getResult();

                        String resthr = snapshot.child("resthr").getValue().toString();
                        String peakhr = snapshot.child("peakhr").getValue().toString();


                        title2.setText("Resting Heart :\nRate (bpm)");
                        title3.setText("Peak Exercise :\nHeart Rate (bpm)");
                        title4.setVisibility(View.INVISIBLE);
                        title5.setVisibility(View.INVISIBLE);
                        title6.setVisibility(View.INVISIBLE);

                        data2.setText(resthr);
                        data3.setText(peakhr);
                        data4.setVisibility(View.INVISIBLE);
                        data5.setVisibility(View.INVISIBLE);
                        data6.setVisibility(View.INVISIBLE);
                    }
                }
            });

        }else if(mode.equals("Weight")){
            query.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if(task.isSuccessful()){
                        DataSnapshot snapshot = task.getResult();

                        if (snapshot != null && snapshot.exists()) {
                            String weight = snapshot.child("weight").getValue().toString();

                            title2.setText("Weight :");
                            title3.setVisibility(View.INVISIBLE);
                            title4.setVisibility(View.INVISIBLE);
                            title5.setVisibility(View.INVISIBLE);
                            title6.setVisibility(View.INVISIBLE);

                            data2.setText(weight);
                            data3.setVisibility(View.INVISIBLE);
                            data4.setVisibility(View.INVISIBLE);
                            data5.setVisibility(View.INVISIBLE);
                            data6.setVisibility(View.INVISIBLE);
                        }
                    }
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(UserDataView.this, UserListOption.class));
        finish();
    }
}