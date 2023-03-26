package com.example.exerciseprescription;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class AdminProgressChartList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_progress_chart_list);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(AdminProgressChartList.this, AdminHomepage.class));
        finish();
    }
}