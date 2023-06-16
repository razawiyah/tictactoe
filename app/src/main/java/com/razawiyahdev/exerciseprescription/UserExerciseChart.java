package com.razawiyahdev.exerciseprescription;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.razawiyahdev.exerciseprescription.R;

public class UserExerciseChart extends AppCompatActivity {

    /*BarChart barChart;
    ArrayList<BarEntry> barEntryArrayList;
    ArrayList<String> labelNames;

    ArrayList<WeightModel> weightList = new ArrayList<>();

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://exerciseprescription-c1b89-default-rtdb.firebaseio.com/");
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    FirebaseUser fUser;
    String id;*/

    CardView userWeightBtn,userHRBtn,userBPBtn;

    public static final String CHART_TYPE = "CHARTTYPE";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_exercise_chart);

        /*barChart = findViewById(R.id.barChart);

        //data entry
        barEntryArrayList = new ArrayList<>();
        labelNames = new ArrayList<>();
//        fillMonthSales();

        fUser = mAuth.getCurrentUser();
        id = fUser.getUid();

//        weightList.clear();

        Query query = databaseReference.child("Weight").child(id);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    WeightModel weight = ds.getValue(WeightModel.class);
                    weightList.add(new WeightModel(weight.getDate(),weight.getWeight()));
                    Log.w(TAG, "Data : date="+weight.getDate() + ", weight="+weight.getWeight());
                    Log.w(TAG, "list size = "+weightList.size());
                }

                for(int i=0; i< weightList.size(); i++){
                    String date = weightList.get(i).getDate();
                    float weight = Float.parseFloat(weightList.get(i).getWeight());
                    Log.w(TAG, "Loop Data : date="+date + ", weight="+weight);

                    barEntryArrayList.add(new BarEntry(i,weight));
                    labelNames.add(date);
                }


                BarDataSet barDataSet = new BarDataSet(barEntryArrayList,"Daily Weight");
                barDataSet.setColors(ColorTemplate.PASTEL_COLORS);
                Description description = new Description();
                description.setText("Date");
                barChart.setDescription(description);
                BarData barData = new BarData(barDataSet);
                barChart.setData(barData);

                XAxis xAxis = barChart.getXAxis();
                xAxis.setValueFormatter(new IndexAxisValueFormatter(labelNames));

                xAxis.setPosition(XAxis.XAxisPosition.TOP);
                xAxis.setDrawGridLines(false);
                xAxis.setDrawAxisLine(false);
                xAxis.setGranularity(1f);
                xAxis.setLabelCount(labelNames.size());
                xAxis.setLabelRotationAngle(270);
                barChart.animateY(2000);
                barChart.invalidate();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/

        userWeightBtn = findViewById(R.id.userWeightBtn);
        userHRBtn = findViewById(R.id.userHRBtn);
        userBPBtn = findViewById(R.id.userBPBtn);

        Intent intent2 = new Intent(UserExerciseChart.this,ChartExample.class);


        userWeightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent2.putExtra(CHART_TYPE, "weight");
                startActivity(intent2);
                finish();
            }
        });

        userHRBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent2.putExtra(CHART_TYPE, "heartRate");
                startActivity(intent2);
                finish();
            }
        });

        userBPBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent2.putExtra(CHART_TYPE, "bloodPressure");
                startActivity(intent2);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(UserExerciseChart.this, UserProgressChart.class));
        finish();
    }
}