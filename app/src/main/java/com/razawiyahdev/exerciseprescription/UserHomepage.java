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
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.razawiyahdev.exerciseprescription.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.Calendar;
import java.util.Locale;

public class UserHomepage extends AppCompatActivity {
    CardView exercisePCard,progressCCard,healthDCard,exerciseDCard;
    View toolbar;
    TextView title;
    ImageView logout,menu;

    public SignOut dialog;
    DrawerLayout drawerLayout;
    LinearLayout homepageBtn,messageBtn,aboutBtn,updatePBtn;
    TextView name,name2,age,dob,height,weight,gender,email;
    String id;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser fUser;

    CalendarView calendarView;

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
//                startActivity(new Intent(UserHomepage.this, UserProgressChart.class));
//                finish();
                featureUnderProgress();
            }
        });

        healthDCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserHomepage.this, UserHealthData.class));
                finish();
//                featureUnderProgress();
            }
        });

        exerciseDCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserHomepage.this, UserExerciseData.class));
                finish();
//                featureUnderProgress();
            }
        });

        fUser = mAuth.getCurrentUser();
        id = fUser.getUid();

        drawerLayout=findViewById(R.id.drawer);
        name = drawerLayout.findViewById(R.id.nameTV);
        name2 = drawerLayout.findViewById(R.id.nameTV2);
        age = drawerLayout.findViewById(R.id.ageTV);
        dob = drawerLayout.findViewById(R.id.dobTV);
        height = drawerLayout.findViewById(R.id.heightTV);
        weight = drawerLayout.findViewById(R.id.weightTV);
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

                Query query = FirebaseDatabase.getInstance().getReference("User").child(id);

                query.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if(task.isSuccessful()){
                            DataSnapshot snapshot = task.getResult();
                            String nameDB = snapshot.child("name").getValue().toString();
                            String genderDB = snapshot.child("gender").getValue().toString();
                            String emailDB = snapshot.child("email").getValue().toString();
                            String dobDB = snapshot.child("dob").getValue().toString();
                            String heightDB = snapshot.child("height").getValue().toString();
                            String weightDB = snapshot.child("weight").getValue().toString();

                            String yearStr = dobDB.substring(dobDB.length() - 4);
                            int yearOB = Integer.parseInt(yearStr);
                            Calendar calendar = Calendar.getInstance();
                            int yearCur = calendar.get(Calendar.YEAR);
                            int ageNum = yearCur-yearOB;
                            String ageDB = Integer.toString(ageNum);

                            name.setText(nameDB);
                            name2.setText(nameDB);
                            gender.setText(genderDB);
                            email.setText(emailDB);
                            age.setText(ageDB);
                            dob.setText(dobDB);
                            height.setText(heightDB);
                            weight.setText(weightDB);


                        }
                    }
                });
            }
        });

        messageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserHomepage.this, UserMessage.class));
                finish();            }
        });

        aboutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserHomepage.this, UserAbout.class));
                finish();
            }
        });

        updatePBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserHomepage.this, UserUpdateProfile.class));
                finish();
            }
        });

        calendarView = findViewById(R.id.calendarView);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                // Convert the selected date to the desired format (e.g., YYYY-MM-DD)
                String selectedDate = String.format(Locale.getDefault(), "%02d%02d%04d", dayOfMonth, month + 1,year);

                // Create an Intent to start the next activity
                Toast.makeText(UserHomepage.this, selectedDate, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(UserHomepage.this, UserListOption.class);
                intent.putExtra("selectedDate", selectedDate);
                startActivity(intent);
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
                finishAffinity();
            }
        });
        builder.setNegativeButton("No", null);
        builder.show();
    }

    public void featureUnderProgress(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Feature is under Progress");
        builder.setNeutralButton("OK",null);
        builder.show();
    }

}