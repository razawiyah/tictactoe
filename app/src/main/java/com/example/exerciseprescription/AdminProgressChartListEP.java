package com.example.exerciseprescription;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.exerciseprescription.class2.DoctorEPModel;
import com.example.exerciseprescription.class2.EPmodel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminProgressChartListEP extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<EPmodel> list;
    RecyclerAdapter3 adapter;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("ExercisePrescription");
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser fUser;
    String id,patientId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_progress_chart_list_ep);

        recyclerView = findViewById(R.id.recyclerView);
        list = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerAdapter3(this,list);
        recyclerView.setAdapter(adapter);

        fUser = mAuth.getCurrentUser();
        id = fUser.getUid();

        Intent intent = getIntent();
        if(!((RecyclerAdapter.PATIENT_ID).isEmpty())){
            patientId  = intent.getStringExtra(RecyclerAdapter.PATIENT_ID);
        }else if(!((AdminExercisePrescriptionModify.PATIENT_ID).isEmpty())){
            patientId  = intent.getStringExtra(AdminExercisePrescriptionModify.PATIENT_ID);
        }else if(!((AdminExerciseChart.PATIENT_ID).isEmpty())){
            patientId  = intent.getStringExtra(AdminExerciseChart.PATIENT_ID);
        }else if(!((AdminHealthChart.PATIENT_ID).isEmpty())){
            patientId  = intent.getStringExtra(AdminHealthChart.PATIENT_ID);
        }

        Query query = databaseReference.child(id).child(patientId);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    EPmodel model =dataSnapshot.getValue(EPmodel.class);
                    list.add(model);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(AdminProgressChartListEP.this, AdminProgressChartList.class));
        finish();
    }
}