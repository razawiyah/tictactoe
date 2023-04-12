package com.example.exerciseprescription;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.exerciseprescription.class2.WeightModel;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class UserExerciseChart extends AppCompatActivity {

    BarChart barChart;
    ArrayList<BarEntry> barEntryArrayList;
    ArrayList<String> labelNames;

    ArrayList<WeightModel> weightList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_exercise_chart);

        barChart = findViewById(R.id.barChart);

        //data entry
        barEntryArrayList = new ArrayList<>();
        labelNames = new ArrayList<>();
        fillMonthSales();
        for(int i=0; i< weightList.size(); i++){
            String date = weightList.get(i).getDate();
            float weight = Float.parseFloat(weightList.get(i).getWeight());
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

    private void fillMonthSales(){

        weightList.clear();
        weightList.add(new WeightModel("01/04/2023","70"));
        weightList.add(new WeightModel("02/04/2023","70.5"));
        weightList.add(new WeightModel("03/04/2023","70"));
        weightList.add(new WeightModel("04/04/2023","65.5"));
        weightList.add(new WeightModel("05/04/2023","70"));
        weightList.add(new WeightModel("06/04/2023","75.5"));
        weightList.add(new WeightModel("07/04/2023","50"));
        weightList.add(new WeightModel("08/04/2023","55.5"));

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(UserExerciseChart.this, UserProgressChart.class));
        finish();
    }
}