package com.example.exerciseprescription;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class UserListOption extends AppCompatActivity {

    CardView userWeightBtn,userHRBtn,userBPBtn,aerobicBtn,flexBtn,strengthBtn;

    String selectedDate,formattedDate;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://exerciseprescription-c1b89-default-rtdb.firebaseio.com/");
    String mode;

    String id;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser fUser;
    Query query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list_option);

        fUser = mAuth.getCurrentUser();
        id = fUser.getUid();

        selectedDate = getIntent().getStringExtra("selectedDate");
        formattedDate = selectedDate.substring(0, 2) + "-" + selectedDate.substring(2, 4) + "-" + selectedDate.substring(4);


        userWeightBtn = findViewById(R.id.userWeightBtn);
        userHRBtn = findViewById(R.id.userHRBtn);
        userBPBtn = findViewById(R.id.userBPBtn);

        aerobicBtn = findViewById(R.id.aerobicBtn);
        flexBtn = findViewById(R.id.flexBtn);
        strengthBtn = findViewById(R.id.strengthBtn);

        userWeightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mode = "Weight";
                query= databaseReference.child(mode).child(id).child(selectedDate);

                query.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if(task.isSuccessful()){
                            DataSnapshot snapshot = task.getResult();

                            if (snapshot != null && snapshot.exists()) {
                                Intent intent = new Intent(UserListOption.this, UserListOption.class);
                                intent.putExtra("selectedDate", selectedDate);
                                intent.putExtra("mode", "Weight");
                                startActivity(intent);
                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(UserListOption.this);
                                builder.setMessage("No Weight Data For "+formattedDate)
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                // Handle OK button click if needed
                                            }
                                        });
                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }


                        }else{
                            AlertDialog.Builder builder = new AlertDialog.Builder(UserListOption.this);
                            builder.setMessage("No Weight Data For "+formattedDate)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            // Handle OK button click if needed
                                        }
                                    });
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                    }
                });
            }
        });

        userHRBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mode = "HeartRate";
                query= databaseReference.child(mode).child(id).child(selectedDate);

                query.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if(task.isSuccessful()){
                            DataSnapshot snapshot = task.getResult();

                            if (snapshot != null && snapshot.exists()) {
                                Intent intent = new Intent(UserListOption.this, UserDataView.class);
                                intent.putExtra("selectedDate", selectedDate);
                                intent.putExtra("mode", "HeartRate");
                                startActivity(intent);
                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(UserListOption.this);
                                builder.setMessage("No Heart Rate Data For "+formattedDate)
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                // Handle OK button click if needed
                                            }
                                        });
                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }


                        }else{
                            AlertDialog.Builder builder = new AlertDialog.Builder(UserListOption.this);
                            builder.setMessage("No Heart Rate Data For "+formattedDate)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            // Handle OK button click if needed
                                        }
                                    });
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                    }
                });

                            }
        });

        userBPBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mode = "BloodPressure";
                query= databaseReference.child(mode).child(id).child(selectedDate);

                query.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if(task.isSuccessful()){
                            DataSnapshot snapshot = task.getResult();

                            if (snapshot != null && snapshot.exists()) {
                                Intent intent = new Intent(UserListOption.this, UserDataView.class);
                                intent.putExtra("selectedDate", selectedDate);
                                intent.putExtra("mode", "BloodPressure");
                                startActivity(intent);
                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(UserListOption.this);
                                builder.setMessage("No Blood Pressure Data For "+formattedDate)
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                // Handle OK button click if needed
                                            }
                                        });
                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }


                        }else{
                            AlertDialog.Builder builder = new AlertDialog.Builder(UserListOption.this);
                            builder.setMessage("No Blood Pressure Data For "+formattedDate)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            // Handle OK button click if needed
                                        }
                                    });
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                    }
                });

                            }
        });

        aerobicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mode = "Aerobic";
                query= databaseReference.child(mode).child(id).child(selectedDate);

                query.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if(task.isSuccessful()){
                            DataSnapshot snapshot = task.getResult();

                            if (snapshot != null && snapshot.exists()) {
                                Intent intent = new Intent(UserListOption.this, UserDataView.class);
                                intent.putExtra("selectedDate", selectedDate);
                                intent.putExtra("mode", "Aerobic");
                                startActivity(intent);
                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(UserListOption.this);
                                builder.setMessage("No Aerobic Data For "+formattedDate)
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                // Handle OK button click if needed
                                            }
                                        });
                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }


                        }else{
                            AlertDialog.Builder builder = new AlertDialog.Builder(UserListOption.this);
                            builder.setMessage("No Aerobic Data For "+formattedDate)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            // Handle OK button click if needed
                                        }
                                    });
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                    }
                });


            }
        });

        flexBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mode = "Flexibility";
                query= databaseReference.child(mode).child(id).child(selectedDate);

                query.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if(task.isSuccessful()){
                            DataSnapshot snapshot = task.getResult();

                            if (snapshot != null && snapshot.exists()) {
                                Intent intent = new Intent(UserListOption.this, UserDataView.class);
                                intent.putExtra("selectedDate", selectedDate);
                                intent.putExtra("mode", "Flexibility");
                                startActivity(intent);
                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(UserListOption.this);
                                builder.setMessage("No Flexibility Data For "+formattedDate)
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                // Handle OK button click if needed
                                            }
                                        });
                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }


                        }else{
                            AlertDialog.Builder builder = new AlertDialog.Builder(UserListOption.this);
                            builder.setMessage("No Flexibility Data For "+formattedDate)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            // Handle OK button click if needed
                                        }
                                    });
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                    }
                });

                            }
        });

        strengthBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mode = "Strength";
                query= databaseReference.child(mode).child(id).child(selectedDate);

                query.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if(task.isSuccessful()){
                            DataSnapshot snapshot = task.getResult();

                            if (snapshot != null && snapshot.exists()) {
                                Intent intent = new Intent(UserListOption.this, UserDataView.class);
                                intent.putExtra("selectedDate", selectedDate);
                                intent.putExtra("mode", "Strength");
                                startActivity(intent);
                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(UserListOption.this);
                                builder.setMessage("No Strength Data For "+formattedDate)
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                // Handle OK button click if needed
                                            }
                                        });
                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }


                        }else{
                            AlertDialog.Builder builder = new AlertDialog.Builder(UserListOption.this);
                            builder.setMessage("No Strength Data For "+formattedDate)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            // Handle OK button click if needed
                                        }
                                    });
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                    }
                });

                            }
        });
    }
}