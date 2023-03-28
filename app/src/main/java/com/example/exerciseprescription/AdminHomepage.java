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

public class AdminHomepage extends AppCompatActivity {

    CardView exercisePCard,progressCCard;
    View toolbar;
    TextView title;
    ImageView logout,menu;
    public SignOut dialog;

    DrawerLayout drawerLayout;

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