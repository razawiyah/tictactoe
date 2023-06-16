package com.razawiyahdev.exerciseprescription;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.razawiyahdev.exerciseprescription.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class MainActivity extends AppCompatActivity {
    Button getStartBtn;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser fUser;
    String id;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fUser = mAuth.getCurrentUser();

        if (fUser != null) {
            //have to make if else patient or doctor
            id = fUser.getUid();
            Query patientQ = FirebaseDatabase.getInstance().getReference("Doctor").child(id);
            patientQ.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if(task.getResult().exists()){
                        startActivity(new Intent(MainActivity.this, AdminHomepage.class));
                        finish();
                    }else{
                        startActivity(new Intent(MainActivity.this, UserHomepage.class));
                        finish();
                    }
                }
            });
        }
        else{
            setContentView(R.layout.activity_main);

            getStartBtn = findViewById(R.id.getStartBtn);
        }




    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit the app?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finishAffinity();
            }
        });
        builder.setNegativeButton("No", null);
        builder.show();
    }

    public void getStart(View view) {
        startActivity(new Intent(MainActivity.this, Login.class));
        finish();
    }
}