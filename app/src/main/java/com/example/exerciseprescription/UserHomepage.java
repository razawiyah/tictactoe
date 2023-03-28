package com.example.exerciseprescription;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class UserHomepage extends AppCompatActivity {
    CardView exercisePCard,progressCCard,healthDCard,exerciseDCard;
    View toolbar;
    TextView title;
    ImageView logout,menu;

    public SignOut dialog;
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_homepage);

        exercisePCard = findViewById(R.id.exercisePCard);
        progressCCard = findViewById(R.id.progressCCard);
        healthDCard = findViewById(R.id.healthDCard);
        exerciseDCard = findViewById(R.id.exerciseDCard);

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
                startActivity(new Intent(UserHomepage.this, UserExercisePrescription.class));
                finish();
            }
        });

        progressCCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserHomepage.this, UserProgressChart.class));
                finish();
            }
        });

        healthDCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserHomepage.this, UserHealthData.class));
                finish();
            }
        });

        exerciseDCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserHomepage.this, UserExerciseData.class));
                finish();
            }
        });

        drawerLayout=findViewById(R.id.drawer);

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDrawer(drawerLayout);
            }
        });

    }

    public static void openDrawer(DrawerLayout drawerLayout) {

        drawerLayout.openDrawer(GravityCompat.START);

    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit the app?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton("No", null);
        builder.show();
    }
}