package com.razawiyahdev.exerciseprescription;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.razawiyahdev.exerciseprescription.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class AdminHomepage extends AppCompatActivity {

    CardView exercisePCard,progressCCard;
    View toolbar;
    TextView title;
    ImageView logout,menu;
    public SignOut dialog;

    DrawerLayout drawerLayout;
    LinearLayout homepageBtn,messageBtn,aboutBtn,updatePBtn;
    TextView drName,drName2,gender,email;
    String id;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser fUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_homepage);

        exercisePCard = findViewById(R.id.exercisePCard);
        progressCCard = findViewById(R.id.progressCCard);

        toolbar = findViewById(R.id.toolbar);
        title = toolbar.findViewById(R.id.title);
        logout = toolbar.findViewById(R.id.logoutBtn);
        menu = toolbar.findViewById(R.id.menu);

        dialog = new SignOut(this);

        title.setText("I-HeLP | HomePage");

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

        exercisePCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminHomepage.this, AdminExercisePrescription.class));
                finish();
            }
        });

        progressCCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminHomepage.this, AdminProgressChartList.class));
                finish();
            }
        });

        fUser = mAuth.getCurrentUser();
        id = fUser.getUid();

        drawerLayout=findViewById(R.id.drawer);
        drName = drawerLayout.findViewById(R.id.nameTV);
        drName2 = drawerLayout.findViewById(R.id.nameTV2);
        gender = drawerLayout.findViewById(R.id.genderTV);
        email = drawerLayout.findViewById(R.id.emailTV);

        homepageBtn = drawerLayout.findViewById(R.id.homepageBtn);
        messageBtn = drawerLayout.findViewById(R.id.messageBtn);
        aboutBtn = drawerLayout.findViewById(R.id.aboutBtn);
        updatePBtn = drawerLayout.findViewById(R.id.updatePBtn);

        homepageBtn.setBackgroundColor(ContextCompat.getColor(this,R.color.teal));

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDrawer(drawerLayout);

                Query query = FirebaseDatabase.getInstance().getReference("Doctor").child(id);

                query.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if(task.isSuccessful()){
                            DataSnapshot snapshot = task.getResult();
                            String name = snapshot.child("name").getValue().toString();
                            String genderDB = snapshot.child("gender").getValue().toString();
                            String emailDB = snapshot.child("email").getValue().toString();

                            drName.setText(name);
                            drName2.setText(name);
                            gender.setText(genderDB);
                            email.setText(emailDB);

                        }
                    }
                });
            }
        });

        messageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminHomepage.this, AdminMessage.class));
                finish();            }
        });

        aboutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminHomepage.this, AdminAbout.class));
                finish();
            }
        });

        updatePBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminHomepage.this, AdminUpdateProfile.class));
                finish();
            }
        });
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

    public static void openDrawer(DrawerLayout drawerLayout) {

        drawerLayout.openDrawer(GravityCompat.START);

    }

    public void featureUnderProgress(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Feature is under Progress");
        builder.setNeutralButton("OK",null);
        builder.show();
    }
}