package com.razawiyahdev.exerciseprescription;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.razawiyahdev.exerciseprescription.R;
import com.razawiyahdev.exerciseprescription.class2.AerobicModel;
import com.razawiyahdev.exerciseprescription.class2.BloodPressureModel;
import com.razawiyahdev.exerciseprescription.class2.FlexibilityModel;
import com.razawiyahdev.exerciseprescription.class2.HeartRateModel;
import com.razawiyahdev.exerciseprescription.class2.StrengthModel;
import com.razawiyahdev.exerciseprescription.class2.WeightModel;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.stream.Stream;

public class AdminChart extends AppCompatActivity {

    BarChart barChart;
    ArrayList<BarEntry> barEntryArrayList;
    ArrayList<String> labelNames;

    ArrayList<WeightModel> weightList = new ArrayList<>();
    ArrayList<BloodPressureModel> bpList = new ArrayList<>();
    ArrayList<HeartRateModel> hrList = new ArrayList<>();

    ArrayList<StrengthModel> strengthList = new ArrayList<>();

    ArrayList<FlexibilityModel> flexList = new ArrayList<>();

    ArrayList<AerobicModel> aerobicList = new ArrayList<>();


    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://exerciseprescription-c1b89-default-rtdb.firebaseio.com/");
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    FirebaseUser fUser;
    String id,queryPath,queryPath2,queryId,queryId2;
    Query query;

    TextView chartTitle;

    int[] barColors;

    ZoneId zoneId = ZoneId.of("Asia/Kuala_Lumpur");
    ZonedDateTime zonedDateTime = ZonedDateTime.now(zoneId);
    DateTimeFormatter formatterMonth = DateTimeFormatter.ofPattern("MMMM");
    String month = zonedDateTime.format(formatterMonth);
    int monthNum = zonedDateTime.getMonthValue();

    DateTimeFormatter formatterYear = DateTimeFormatter.ofPattern("yyyy");
    String year = zonedDateTime.format(formatterYear);

    Button monthBtn,yearBtn;

    public static final String PATIENT_ID = "PATIENTID";

    boolean isYearButtonClicked = false;

    String[] monthLabels,dayLabels;
    ArrayList<BarEntry> systolicEntryArrayList = new ArrayList<>();
    ArrayList<BarEntry> diastolicEntryArrayList = new ArrayList<>();
    private IndexAxisValueFormatter xAxisFormatter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_chart);

        barChart = findViewById(R.id.barChart);
        chartTitle = findViewById(R.id.chartTitle);

        monthBtn = findViewById(R.id.monthBtn);
        yearBtn = findViewById(R.id.yearBtn);

        barColors = new int[] {
                ContextCompat.getColor(this, R.color.white)};

//        fUser = mAuth.getCurrentUser();
//        id = fUser.getUid();

        //data entry
        barEntryArrayList = new ArrayList<>();
        labelNames = new ArrayList<>();

        Intent intent3 = getIntent();
        queryId  = intent3.getStringExtra(AdminExerciseChart.PATIENT_ID);

        Intent intent4 = getIntent();
        queryId2  = intent4.getStringExtra(AdminHealthChart.PATIENT_ID);

        if(!(queryId.isEmpty())){
            id = queryId;
        }else {
            id = queryId2;
        }

        Intent intent = getIntent();
        queryPath  = intent.getStringExtra(AdminExerciseChart.CHART_TYPE);

        Intent intent2 = getIntent();
        queryPath2  = intent2.getStringExtra(AdminHealthChart.CHART_TYPE);

        handleChartDisplay(queryPath);

        yearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isYearButtonClicked = true;
                handleChartDisplay(queryPath);
                monthBtn.setTextColor(ContextCompat.getColor(AdminChart.this, R.color.white));
                monthBtn.setBackgroundResource(R.drawable.custom_button_rec_white_line);
                yearBtn.setTextColor(ContextCompat.getColor(AdminChart.this, R.color.maroon));
                yearBtn.setBackgroundResource(R.drawable.custom_button_rec_white);
            }
        });

        monthBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isYearButtonClicked = false;
                handleChartDisplay(queryPath);
                yearBtn.setTextColor(ContextCompat.getColor(AdminChart.this, R.color.white));
                yearBtn.setBackgroundResource(R.drawable.custom_button_rec_white_line);
                monthBtn.setTextColor(ContextCompat.getColor(AdminChart.this, R.color.maroon));
                monthBtn.setBackgroundResource(R.drawable.custom_button_rec_white);
            }
        });

    }

    public void weightChartMonth(){
        query = databaseReference.child("WeightM").child(id).child(month);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                weightList.clear();

                for (DataSnapshot ds : snapshot.getChildren()) {
                    WeightModel weight = ds.getValue(WeightModel.class);
                    weightList.add(new WeightModel(weight.getDate(),weight.getWeight()));
                }

                float maxBarValue = 0f;
                for(int i=0; i< weightList.size(); i++){
                    float weight = Float.parseFloat(weightList.get(i).getWeight());

                    barEntryArrayList.add(new BarEntry(i, weight));
                    float yValue = weight;
                    if (yValue > maxBarValue) {
                        maxBarValue = yValue;
                    }
                }

                String lastDate = Integer.toString(getDaysInMonth(monthNum));
                BarDataSet barDataSet = new BarDataSet(barEntryArrayList,"01-"+lastDate+" "+month+" "+year);
                barDataSet.setColors(ColorTemplate.createColors(barColors));
                barDataSet.setValueTextColor(Color.WHITE);

                boolean hasNonZeroValue = weightList.stream()
                        .map(WeightModel::getWeight)
                        .map(Float::parseFloat)
                        .anyMatch(w -> w != 0);

                barDataSet.setValueFormatter(new ValueFormatter() {
                    @Override
                    public String getFormattedValue(float value) {
                        if (value == 0) {
                            return "";
                        } else {
                            return String.valueOf(value);
                        }
                    }
                });

                barDataSet.setDrawValues(hasNonZeroValue);

                //description
                barChart.getDescription().setEnabled(false);


                BarData barData = new BarData(barDataSet);
                barChart.setData(barData);
                Legend legend = barChart.getLegend();
                legend.setTextColor(Color.WHITE);

                YAxis leftAxis = barChart.getAxisLeft();
                leftAxis.setDrawGridLines(false);
                leftAxis.setAxisMinimum(0f);
                float axisMaximum = Math.max(maxBarValue, maxBarValue+5f); // Set a minimum margin of 10f above the highest value
                leftAxis.setAxisMaximum(axisMaximum);
                leftAxis.setTextColor(Color.WHITE);

                YAxis rightAxis = barChart.getAxisRight();
                rightAxis.setDrawAxisLine(false);
                rightAxis.setDrawGridLines(false);
                rightAxis.setDrawLabels(false);

                initializeChartWithDailyLabels();

                barChart.invalidate();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void weightChartYear(){

        query = databaseReference.child("WeightM").child(id);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                weightList.clear();

                for (DataSnapshot monthSnapshot : snapshot.getChildren()) {
                    String month = monthSnapshot.getKey();
                    float totalWeight = 0f;
                    int entryCount = 0;

                    for (DataSnapshot ds : monthSnapshot.getChildren()) {
                        WeightModel weight = ds.getValue(WeightModel.class);
                        weightList.add(new WeightModel(weight.getDate(), weight.getWeight()));
                        totalWeight += Float.parseFloat(weight.getWeight());
                        entryCount++;
                    }

                    DecimalFormat decimalFormat = new DecimalFormat("#.00");
                    float averageWeight = Float.parseFloat(decimalFormat.format(totalWeight / entryCount));

                    // Add the average weight value for the month to the barEntryArrayList
                    barEntryArrayList.add(new BarEntry(getMonthIndex(month), averageWeight));
                }
                // Rest of the code...

                Collections.sort(barEntryArrayList, new Comparator<BarEntry>() {
                    @Override
                    public int compare(BarEntry entry1, BarEntry entry2) {
                        // Compare the X values of the BarEntry objects
                        return Float.compare(entry1.getX(), entry2.getX());
                    }
                });

                // Loop through the sorted barEntryArrayList
                float maxBarValue = 0f;
                for (int i = 0; i < barEntryArrayList.size(); i++) {
                    BarEntry entry = barEntryArrayList.get(i);
                    entry.setX(i);
                    entry.setY(entry.getY()); // Divide the value by 100 for scaling purposes

                    float yValue = entry.getY();
                    if (yValue > maxBarValue) {
                        maxBarValue = yValue;
                    }
                }


                // Set data and invalidate the chart
                BarDataSet barDataSet = new BarDataSet(barEntryArrayList, "Average Weight");
                barDataSet.setColors(ColorTemplate.createColors(barColors));
                barDataSet.setValueFormatter(new DefaultValueFormatter(2));
                barDataSet.setValueTextSize(12f);
                barDataSet.setValueTextColor(Color.WHITE);


                boolean hasNonZeroValue = weightList.stream()
                        .map(WeightModel::getWeight)
                        .map(Float::parseFloat)
                        .anyMatch(w -> w != 0);

                barDataSet.setValueFormatter(new ValueFormatter() {
                    @Override
                    public String getFormattedValue(float value) {
                        if (value == 0) {
                            return "";
                        } else {
                            return String.valueOf(value);
                        }
                    }
                });

                barDataSet.setDrawValues(hasNonZeroValue);

                //description
                barChart.getDescription().setEnabled(false);


                BarData barData = new BarData(barDataSet);
                barChart.setData(barData);
                Legend legend = barChart.getLegend();
                legend.setTextColor(Color.WHITE);

                YAxis leftAxis = barChart.getAxisLeft();
                leftAxis.setDrawGridLines(false);
                leftAxis.setAxisMinimum(0f);
                float axisMaximum = Math.max(maxBarValue, maxBarValue+5f); // Set a minimum margin of 10f above the highest value
                leftAxis.setAxisMaximum(axisMaximum);
                leftAxis.setTextColor(Color.WHITE);

                YAxis rightAxis = barChart.getAxisRight();
                rightAxis.setDrawAxisLine(false);
                rightAxis.setDrawGridLines(false);
                rightAxis.setDrawLabels(false);

                initializeChartWithMonthlyLabels();

                barChart.invalidate();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void aerobicChartMonth(){
        query = databaseReference.child("AerobicM").child(id).child(month);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                aerobicList.clear();

                for (DataSnapshot ds : snapshot.getChildren()) {
                    AerobicModel weight = ds.getValue(AerobicModel.class);
                    aerobicList.add(new AerobicModel(weight.getDate(), weight.getDuration()));
                }

                float maxBarValue = 0f;
                for(int i=0; i< aerobicList.size(); i++){
                    String date = aerobicList.get(i).getDate();
                    int weight = Integer.parseInt(aerobicList.get(i).getDuration());

                    barEntryArrayList.add(new BarEntry(i, weight));
                    float yValue = weight;
                    if (yValue > maxBarValue) {
                        maxBarValue = yValue;
                    }
                }

                String lastDate = Integer.toString(getDaysInMonth(monthNum));
                BarDataSet barDataSet = new BarDataSet(barEntryArrayList,"01-"+lastDate+" "+month+" "+year);
                barDataSet.setColors(ColorTemplate.createColors(barColors));
                barDataSet.setValueTextColor(Color.WHITE);

                boolean hasNonZeroValue = aerobicList.stream()
                        .map(AerobicModel::getDuration)
                        .map(Float::parseFloat)
                        .anyMatch(w -> w != 0);

                barDataSet.setValueFormatter(new ValueFormatter() {
                    @Override
                    public String getFormattedValue(float value) {
                        if (value == 0) {
                            return "";
                        } else {
                            return String.valueOf(value);
                        }
                    }
                });

                barDataSet.setDrawValues(hasNonZeroValue);

                //description
                barChart.getDescription().setEnabled(false);


                BarData barData = new BarData(barDataSet);
                barChart.setData(barData);
                Legend legend = barChart.getLegend();
                legend.setTextColor(Color.WHITE);

                YAxis leftAxis = barChart.getAxisLeft();
                leftAxis.setDrawGridLines(false);
                leftAxis.setAxisMinimum(0f);
                float axisMaximum = Math.max(maxBarValue, maxBarValue+5f); // Set a minimum margin of 10f above the highest value
                leftAxis.setAxisMaximum(axisMaximum);
                leftAxis.setTextColor(Color.WHITE);

                YAxis rightAxis = barChart.getAxisRight();
                rightAxis.setDrawAxisLine(false);
                rightAxis.setDrawGridLines(false);
                rightAxis.setDrawLabels(false);

                initializeChartWithDailyLabels();

                barChart.invalidate();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void aerobicChartYear(){

        query = databaseReference.child("AerobicM").child(id);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                aerobicList.clear();

                for (DataSnapshot monthSnapshot : snapshot.getChildren()) {
                    String month = monthSnapshot.getKey();
                    float totalWeight = 0f;

                    for (DataSnapshot ds : monthSnapshot.getChildren()) {
                        AerobicModel weight = ds.getValue(AerobicModel.class);
                        aerobicList.add(new AerobicModel(weight.getDate(), weight.getDuration()));
                        totalWeight += Float.parseFloat(weight.getDuration());
                    }

                    // Add the average weight value for the month to the barEntryArrayList
                    barEntryArrayList.add(new BarEntry(getMonthIndex(month), totalWeight));
                }
                // Rest of the code...
                Collections.sort(barEntryArrayList, new Comparator<BarEntry>() {
                    @Override
                    public int compare(BarEntry entry1, BarEntry entry2) {
                        // Compare the X values of the BarEntry objects
                        return Float.compare(entry1.getX(), entry2.getX());
                    }
                });

                // Loop through the sorted barEntryArrayList
                float maxBarValue = 0f;

                for (int i = 0; i < barEntryArrayList.size(); i++) {
                    BarEntry entry = barEntryArrayList.get(i);
                    entry.setX(i);
                    entry.setY(entry.getY()); // Divide the value by 100 for scaling purposes

                    float yValue = entry.getY();
                    if (yValue > maxBarValue) {
                        maxBarValue = yValue;
                    }
                }

                // Set data and invalidate the chart
                BarDataSet barDataSet = new BarDataSet(barEntryArrayList, "Total Duration");
                barDataSet.setColors(ColorTemplate.createColors(barColors));
                barDataSet.setValueFormatter(new DefaultValueFormatter(2));
                barDataSet.setValueTextSize(12f);
                barDataSet.setValueTextColor(Color.WHITE);

                boolean hasNonZeroValue = aerobicList.stream()
                        .map(AerobicModel::getDuration)
                        .map(Float::parseFloat)
                        .anyMatch(w -> w != 0);

                barDataSet.setValueFormatter(new ValueFormatter() {
                    @Override
                    public String getFormattedValue(float value) {
                        if (value == 0) {
                            return "";
                        } else {
                            return String.valueOf(value);
                        }
                    }
                });

                barDataSet.setDrawValues(hasNonZeroValue);

                //description
                barChart.getDescription().setEnabled(false);


                BarData barData = new BarData(barDataSet);
                barChart.setData(barData);
                Legend legend = barChart.getLegend();
                legend.setTextColor(Color.WHITE);

                YAxis leftAxis = barChart.getAxisLeft();
                leftAxis.setDrawGridLines(false);
                leftAxis.setAxisMinimum(0f);
                float axisMaximum = Math.max(maxBarValue, maxBarValue+5f); // Set a minimum margin of 10f above the highest value
                leftAxis.setAxisMaximum(axisMaximum);
                leftAxis.setTextColor(Color.WHITE);

                YAxis rightAxis = barChart.getAxisRight();
                rightAxis.setDrawAxisLine(false);
                rightAxis.setDrawGridLines(false);
                rightAxis.setDrawLabels(false);

                initializeChartWithMonthlyLabels();

                barChart.invalidate();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public void flexChartMonth(){
        query = databaseReference.child("FlexibilityM").child(id).child(month);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                flexList.clear();

                for (DataSnapshot ds : snapshot.getChildren()) {
                    FlexibilityModel weight = ds.getValue(FlexibilityModel.class);
                    flexList.add(new FlexibilityModel(weight.getDate(),weight.getDuration()));
                }

                float maxBarValue = 0f;
                for(int i=0; i< flexList.size(); i++){
                    String date = flexList.get(i).getDate();
                    int weight = Integer.parseInt(flexList.get(i).getDuration());

                    barEntryArrayList.add(new BarEntry(i, weight));
                    float yValue = weight;
                    if (yValue > maxBarValue) {
                        maxBarValue = yValue;
                    }
                }

                String lastDate = Integer.toString(getDaysInMonth(monthNum));
                BarDataSet barDataSet = new BarDataSet(barEntryArrayList,"01-"+lastDate+" "+month+" "+year);
                barDataSet.setColors(ColorTemplate.createColors(barColors));
                barDataSet.setValueTextColor(Color.WHITE);

                boolean hasNonZeroValue = flexList.stream()
                        .map(FlexibilityModel::getDuration)
                        .map(Float::parseFloat)
                        .anyMatch(w -> w != 0);

                barDataSet.setValueFormatter(new ValueFormatter() {
                    @Override
                    public String getFormattedValue(float value) {
                        if (value == 0) {
                            return "";
                        } else {
                            return String.valueOf(value);
                        }
                    }
                });

                barDataSet.setDrawValues(hasNonZeroValue);

                //description
                barChart.getDescription().setEnabled(false);


                BarData barData = new BarData(barDataSet);
                barChart.setData(barData);
                Legend legend = barChart.getLegend();
                legend.setTextColor(Color.WHITE);

                YAxis leftAxis = barChart.getAxisLeft();
                leftAxis.setDrawGridLines(false);
                leftAxis.setAxisMinimum(0f);
                float axisMaximum = Math.max(maxBarValue, maxBarValue+5f); // Set a minimum margin of 10f above the highest value
                leftAxis.setAxisMaximum(axisMaximum);
                leftAxis.setTextColor(Color.WHITE);

                YAxis rightAxis = barChart.getAxisRight();
                rightAxis.setDrawAxisLine(false);
                rightAxis.setDrawGridLines(false);
                rightAxis.setDrawLabels(false);

                initializeChartWithDailyLabels();
                barChart.invalidate();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void flexChartYear(){

        query = databaseReference.child("FlexibilityM").child(id);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                flexList.clear();

                for (DataSnapshot monthSnapshot : snapshot.getChildren()) {
                    String month = monthSnapshot.getKey();
                    float totalWeight = 0f;

                    for (DataSnapshot ds : monthSnapshot.getChildren()) {
                        FlexibilityModel weight = ds.getValue(FlexibilityModel.class);
                        flexList.add(new FlexibilityModel(weight.getDate(), weight.getDuration()));
                        totalWeight += Float.parseFloat(weight.getDuration());
                    }

                    // Add the average weight value for the month to the barEntryArrayList
                    barEntryArrayList.add(new BarEntry(getMonthIndex(month), totalWeight));
                }
                // Rest of the code...
                Collections.sort(barEntryArrayList, new Comparator<BarEntry>() {
                    @Override
                    public int compare(BarEntry entry1, BarEntry entry2) {
                        // Compare the X values of the BarEntry objects
                        return Float.compare(entry1.getX(), entry2.getX());
                    }
                });

                // Loop through the sorted barEntryArrayList
                float maxBarValue = 0f;
                for (int i = 0; i < barEntryArrayList.size(); i++) {
                    BarEntry entry = barEntryArrayList.get(i);
                    entry.setX(i);
                    entry.setY(entry.getY()); // Divide the value by 100 for scaling purposes

                    float yValue = entry.getY();
                    if (yValue > maxBarValue) {
                        maxBarValue = yValue;
                    }
                }

                // Set data and invalidate the chart
                BarDataSet barDataSet = new BarDataSet(barEntryArrayList, "Total Duration");
                barDataSet.setColors(ColorTemplate.createColors(barColors));
                barDataSet.setValueFormatter(new DefaultValueFormatter(2));
                barDataSet.setValueTextSize(12f);
                barDataSet.setValueTextColor(Color.WHITE);

                boolean hasNonZeroValue = flexList.stream()
                        .map(FlexibilityModel::getDuration)
                        .map(Float::parseFloat)
                        .anyMatch(w -> w != 0);

                barDataSet.setValueFormatter(new ValueFormatter() {
                    @Override
                    public String getFormattedValue(float value) {
                        if (value == 0) {
                            return "";
                        } else {
                            return String.valueOf(value);
                        }
                    }
                });

                barDataSet.setDrawValues(hasNonZeroValue);

                //description
                barChart.getDescription().setEnabled(false);


                BarData barData = new BarData(barDataSet);
                barChart.setData(barData);
                Legend legend = barChart.getLegend();
                legend.setTextColor(Color.WHITE);

                YAxis leftAxis = barChart.getAxisLeft();
                leftAxis.setDrawGridLines(false);
                leftAxis.setAxisMinimum(0f);
                float axisMaximum = Math.max(maxBarValue, maxBarValue+5f); // Set a minimum margin of 10f above the highest value
                leftAxis.setAxisMaximum(axisMaximum);
                leftAxis.setTextColor(Color.WHITE);

                YAxis rightAxis = barChart.getAxisRight();
                rightAxis.setDrawAxisLine(false);
                rightAxis.setDrawGridLines(false);
                rightAxis.setDrawLabels(false);

                initializeChartWithMonthlyLabels();

                barChart.invalidate();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public void strengthChartMonth(){
        query = databaseReference.child("StrengthM").child(id).child(month);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                strengthList.clear();

                for (DataSnapshot ds : snapshot.getChildren()) {
                    StrengthModel weight = ds.getValue(StrengthModel.class);
                    strengthList.add(new StrengthModel(weight.getDate(),weight.getDuration()));
                }

                float maxBarValue = 0f;
                for(int i=0; i< strengthList.size(); i++){
                    String date = strengthList.get(i).getDate();
                    int weight = Integer.parseInt(strengthList.get(i).getDuration());

                    barEntryArrayList.add(new BarEntry(i, weight));
                    float yValue = weight;
                    if (yValue > maxBarValue) {
                        maxBarValue = yValue;
                    }
                }

                String lastDate = Integer.toString(getDaysInMonth(monthNum));
                BarDataSet barDataSet = new BarDataSet(barEntryArrayList,"01-"+lastDate+" "+month+" "+year);
                barDataSet.setColors(ColorTemplate.createColors(barColors));
                barDataSet.setValueTextColor(Color.WHITE);

                boolean hasNonZeroValue = strengthList.stream()
                        .map(StrengthModel::getDuration)
                        .map(Float::parseFloat)
                        .anyMatch(w -> w != 0);

                barDataSet.setValueFormatter(new ValueFormatter() {
                    @Override
                    public String getFormattedValue(float value) {
                        if (value == 0) {
                            return "";
                        } else {
                            return String.valueOf(value);
                        }
                    }
                });

                barDataSet.setDrawValues(hasNonZeroValue);

                //description
                barChart.getDescription().setEnabled(false);


                BarData barData = new BarData(barDataSet);
                barChart.setData(barData);
                Legend legend = barChart.getLegend();
                legend.setTextColor(Color.WHITE);

                YAxis leftAxis = barChart.getAxisLeft();
                leftAxis.setDrawGridLines(false);
                leftAxis.setAxisMinimum(0f);
                float axisMaximum = Math.max(maxBarValue, maxBarValue+5f); // Set a minimum margin of 10f above the highest value
                leftAxis.setAxisMaximum(axisMaximum);
                leftAxis.setTextColor(Color.WHITE);

                YAxis rightAxis = barChart.getAxisRight();
                rightAxis.setDrawAxisLine(false);
                rightAxis.setDrawGridLines(false);
                rightAxis.setDrawLabels(false);

                initializeChartWithDailyLabels();

                barChart.invalidate();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void strengthChartYear(){
        query = databaseReference.child("StrengthM").child(id);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                strengthList.clear();

                for (DataSnapshot monthSnapshot : snapshot.getChildren()) {
                    String month = monthSnapshot.getKey();
                    float totalWeight = 0f;

                    for (DataSnapshot ds : monthSnapshot.getChildren()) {
                        StrengthModel weight = ds.getValue(StrengthModel.class);
                        strengthList.add(new StrengthModel(weight.getDate(), weight.getDuration()));
                        totalWeight += Float.parseFloat(weight.getDuration());
                    }

                    // Add the average weight value for the month to the barEntryArrayList
                    barEntryArrayList.add(new BarEntry(getMonthIndex(month), totalWeight));
                }
                // Rest of the code...
                Collections.sort(barEntryArrayList, new Comparator<BarEntry>() {
                    @Override
                    public int compare(BarEntry entry1, BarEntry entry2) {
                        // Compare the X values of the BarEntry objects
                        return Float.compare(entry1.getX(), entry2.getX());
                    }
                });

                // Loop through the sorted barEntryArrayList
                float maxBarValue = 0f;
                for (int i = 0; i < barEntryArrayList.size(); i++) {
                    BarEntry entry = barEntryArrayList.get(i);
                    entry.setX(i);
                    entry.setY(entry.getY()); // Divide the value by 100 for scaling purposes

                    float yValue = entry.getY();
                    if (yValue > maxBarValue) {
                        maxBarValue = yValue;
                    }
                }

                // Set data and invalidate the chart
                BarDataSet barDataSet = new BarDataSet(barEntryArrayList, "Total Duration");
                barDataSet.setColors(ColorTemplate.createColors(barColors));
                barDataSet.setValueFormatter(new DefaultValueFormatter(2));
                barDataSet.setValueTextSize(12f);
                barDataSet.setValueTextColor(Color.WHITE);

                boolean hasNonZeroValue = strengthList.stream()
                        .map(StrengthModel::getDuration)
                        .map(Float::parseFloat)
                        .anyMatch(w -> w != 0);

                barDataSet.setValueFormatter(new ValueFormatter() {
                    @Override
                    public String getFormattedValue(float value) {
                        if (value == 0) {
                            return "";
                        } else {
                            return String.valueOf(value);
                        }
                    }
                });

                barDataSet.setDrawValues(hasNonZeroValue);

                //description
                barChart.getDescription().setEnabled(false);


                BarData barData = new BarData(barDataSet);
                barChart.setData(barData);

                Legend legend = barChart.getLegend();
                legend.setTextColor(Color.WHITE);


                YAxis leftAxis = barChart.getAxisLeft();
                leftAxis.setDrawGridLines(false);
                leftAxis.setAxisMinimum(0f);
                float axisMaximum = Math.max(maxBarValue, maxBarValue+5f); // Set a minimum margin of 10f above the highest value
                leftAxis.setAxisMaximum(axisMaximum);
                leftAxis.setTextColor(Color.WHITE);

                YAxis rightAxis = barChart.getAxisRight();
                rightAxis.setDrawAxisLine(false);
                rightAxis.setDrawGridLines(false);
                rightAxis.setDrawLabels(false);

                initializeChartWithMonthlyLabels();

                barChart.invalidate();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void bloodPressureChartMonth(){
        query = databaseReference.child("BloodPressureM").child(id).child(month);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                bpList.clear();

                for (DataSnapshot ds : snapshot.getChildren()) {
                    BloodPressureModel bp = ds.getValue(BloodPressureModel.class);
                    bpList.add(new BloodPressureModel(bp.getDate(), bp.getSystolic(), bp.getDiastolic()));
                }

                float maxBarValue = 0f;
                for(int i=0; i< bpList.size(); i++){
                    String date = bpList.get(i).getDate();
                    float systolic = Float.parseFloat(bpList.get(i).getSystolic());
                    float diastolic = Float.parseFloat(bpList.get(i).getDiastolic());

                    systolicEntryArrayList.add(new BarEntry(i, systolic));
                    diastolicEntryArrayList.add(new BarEntry(i, diastolic));

                    // Update maxBarValue if necessary
                    if (systolic > maxBarValue) {
                        maxBarValue = systolic;
                    }
                    if (diastolic > maxBarValue) {
                        maxBarValue = diastolic;
                    }
                }

//                String lastDate = Integer.toString(getDaysInMonth(monthNum));
                BarDataSet systolicBarDataSet = new BarDataSet(systolicEntryArrayList,"Systolic");
                systolicBarDataSet.setColor(Color.BLUE);
                systolicBarDataSet.setValueTextColor(Color.BLUE);

                BarDataSet diastolicBarDataSet = new BarDataSet(diastolicEntryArrayList,"Diastolic");
                diastolicBarDataSet.setColor(Color.WHITE);
                diastolicBarDataSet.setValueTextColor(Color.WHITE);

                boolean hasNonZeroValue = bpList.stream()
                        .flatMap(bp -> Stream.of(bp.getSystolic(), bp.getDiastolic()))
                        .map(Float::parseFloat)
                        .anyMatch(w -> w != 0);

                systolicBarDataSet.setValueFormatter(new ValueFormatter() {
                    @Override
                    public String getFormattedValue(float value) {
                        if (value == 0.0f) {
                            return "";
                        } else {
                            return String.valueOf(value);
                        }
                    }
                });

                diastolicBarDataSet.setValueFormatter(new ValueFormatter() {
                    @Override
                    public String getFormattedValue(float value) {
                        if (value == 0.0f) {
                            return "";
                        } else {
                            return String.valueOf(value);
                        }
                    }
                });


                systolicBarDataSet.setDrawValues(hasNonZeroValue);
                diastolicBarDataSet.setDrawValues(hasNonZeroValue);

                //description
                barChart.getDescription().setEnabled(false);

                BarData barData = new BarData(systolicBarDataSet, diastolicBarDataSet);
                float groupSpace = 0.3f;
                float barSpace = 0.02f;
                float barWidth = 0.33f;
                barData.setBarWidth(barWidth);

                barChart.setData(barData);
                barChart.groupBars(0f, groupSpace, barSpace);

                Legend legend = barChart.getLegend();
                legend.setTextColor(Color.WHITE);


                YAxis leftAxis = barChart.getAxisLeft();
                leftAxis.setDrawGridLines(false);
                leftAxis.setAxisMinimum(0f);
                float axisMaximum = Math.max(maxBarValue, maxBarValue+5f); // Set a minimum margin of 10f above the highest value
                leftAxis.setAxisMaximum(axisMaximum);
                leftAxis.setTextColor(Color.WHITE);

                YAxis rightAxis = barChart.getAxisRight();
                rightAxis.setDrawAxisLine(false);
                rightAxis.setDrawGridLines(false);
                rightAxis.setDrawLabels(false);

                initializeChartWithDailyLabels();


                barChart.invalidate();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public void bloodPressureChartYear() {
        query = databaseReference.child("BloodPressureM").child(id);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                bpList.clear();

                for (DataSnapshot monthSnapshot : snapshot.getChildren()) {
                    // Get month data
                    String month = monthSnapshot.getKey();
                    float totalSystolic = 0f;
                    float totalDiastolic = 0f;
                    int entryCount = 0;

                    for (DataSnapshot ds : monthSnapshot.getChildren()) {
                        BloodPressureModel bp = ds.getValue(BloodPressureModel.class);
                        bpList.add(new BloodPressureModel(bp.getDate(), bp.getSystolic(), bp.getDiastolic()));
                        totalSystolic += Float.parseFloat(bp.getSystolic());
                        totalDiastolic += Float.parseFloat(bp.getDiastolic());
                        entryCount++;
                    }

                    DecimalFormat decimalFormat = new DecimalFormat("#.00");
                    // Calculate average systolic and diastolic values for the month
                    float averageSystolic = Float.parseFloat(decimalFormat.format(totalSystolic / entryCount));
                    float averageDiastolic = Float.parseFloat(decimalFormat.format(totalDiastolic / entryCount));

                    // Add the average values to the barEntryArrayList
                    systolicEntryArrayList.add(new BarEntry(getMonthIndex(month), averageSystolic));
                    diastolicEntryArrayList.add(new BarEntry(getMonthIndex(month), averageDiastolic));
                }

                ArrayList<BarEntry> combinedEntryArrayList = new ArrayList<>();
                combinedEntryArrayList.addAll(systolicEntryArrayList);
                combinedEntryArrayList.addAll(diastolicEntryArrayList);

                Collections.sort(combinedEntryArrayList, new Comparator<BarEntry>() {
                    @Override
                    public int compare(BarEntry entry1, BarEntry entry2) {
                        return Float.compare(entry1.getX(), entry2.getX());
                    }
                });

                // Separate the sorted entries back into systolicEntryArrayList and diastolicEntryArrayList
                systolicEntryArrayList.clear();
                diastolicEntryArrayList.clear();

                /*for (BarEntry entry : combinedEntryArrayList) {
                    float xValue = entry.getX();
                    float yValue = entry.getY();

                    if (systolicEntryArrayList.size() < diastolicEntryArrayList.size()) {
                        systolicEntryArrayList.add(new BarEntry(xValue, yValue));
                    } else {
                        diastolicEntryArrayList.add(new BarEntry(xValue, yValue));
                    }
                }*/

                // Calculate the maximum bar value
                float maxBarValue = 0f;
                for (BarEntry entry : combinedEntryArrayList) {
                    float yValue = entry.getY();
                    if (yValue > maxBarValue) {
                        maxBarValue = yValue;
                    }
                }

                // Update systolicEntryArrayList and diastolicEntryArrayList with maximum value
                for (BarEntry entry : combinedEntryArrayList) {
                    float xValue = entry.getX();
                    float yValue = entry.getY();

                    if (systolicEntryArrayList.size() < diastolicEntryArrayList.size()) {
                        systolicEntryArrayList.add(new BarEntry(xValue, yValue));
                        if (yValue > maxBarValue) {
                            maxBarValue = yValue;
                        }
                    } else {
                        diastolicEntryArrayList.add(new BarEntry(xValue, yValue));
                        if (yValue > maxBarValue) {
                            maxBarValue = yValue;
                        }
                    }
                }

                // Create two BarDataSet objects for systolic and diastolic values
                BarDataSet systolicBarDataSet = new BarDataSet(systolicEntryArrayList,"Average Systolic");
                systolicBarDataSet.setColor(Color.BLUE);
                systolicBarDataSet.setValueTextColor(Color.BLUE);


                BarDataSet diastolicBarDataSet = new BarDataSet(diastolicEntryArrayList,"Average Diastolic");
                diastolicBarDataSet.setColor(Color.WHITE);
                diastolicBarDataSet.setValueTextColor(Color.WHITE);

                // Set custom label formatter for the y-axis values
                systolicBarDataSet.setValueFormatter(new DefaultValueFormatter(2));
                diastolicBarDataSet.setValueFormatter(new DefaultValueFormatter(2));

                // Combine the two BarDataSet objects into a BarData object
                BarData barData = new BarData(systolicBarDataSet, diastolicBarDataSet);

                float barWidth = 0.35f; // Width of each bar
                float barSpace = 0.0f; // Space between bars within a group
                float groupSpace = 0.4f; // Space between bar groups

                barData.setBarWidth(barWidth);
                barChart.setData(barData);

                // Calculate the total width of the group including spacing
                float groupWidth = (barWidth + barSpace) * 2;
                float start = -groupWidth / 2;

                barChart.groupBars(-1, groupSpace, barSpace);

                //exclude 0.0
                boolean hasNonZeroValue = bpList.stream()
                        .flatMap(bp -> Stream.of(bp.getSystolic(), bp.getDiastolic()))
                        .map(Float::parseFloat)
                        .anyMatch(w -> w != 0);

                systolicBarDataSet.setValueFormatter(new ValueFormatter() {
                    @Override
                    public String getFormattedValue(float value) {
                        if (value == 0.0f) {
                            return "";
                        } else {
                            return String.valueOf(value);
                        }
                    }
                });

                diastolicBarDataSet.setValueFormatter(new ValueFormatter() {
                    @Override
                    public String getFormattedValue(float value) {
                        if (value == 0.0f) {
                            return "";
                        } else {
                            return String.valueOf(value);
                        }
                    }
                });

                systolicBarDataSet.setDrawValues(hasNonZeroValue);
                diastolicBarDataSet.setDrawValues(hasNonZeroValue);

                barChart.setData(barData);
                barChart.invalidate();
                barChart.getDescription().setEnabled(false);

                YAxis leftAxis = barChart.getAxisLeft();
                leftAxis.setDrawGridLines(false);
                leftAxis.setAxisMinimum(0f);
                float axisMaximum = Math.max(maxBarValue, maxBarValue+5f); // Set a minimum margin of 10f above the highest value
                leftAxis.setAxisMaximum(axisMaximum);
                leftAxis.setTextColor(Color.WHITE);

                YAxis rightAxis = barChart.getAxisRight();
                rightAxis.setDrawAxisLine(false);
                rightAxis.setDrawGridLines(false);
                rightAxis.setDrawLabels(false);

                initializeChartWithMonthlyLabels();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void heartRateChartMonth(){
        query = databaseReference.child("HeartRateM").child(id).child(month);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                hrList.clear();

                for (DataSnapshot ds : snapshot.getChildren()) {
                    HeartRateModel bp = ds.getValue(HeartRateModel.class);
                    hrList.add(new HeartRateModel(bp.getDate(), bp.getResthr(), bp.getPeakhr()));
                }

                float maxBarValue = 0f;
                for(int i=0; i< hrList.size(); i++){
                    String date = hrList.get(i).getDate();
                    float systolic = Float.parseFloat(hrList.get(i).getResthr());
                    float diastolic = Float.parseFloat(hrList.get(i).getPeakhr());

                    systolicEntryArrayList.add(new BarEntry(i, systolic));
                    diastolicEntryArrayList.add(new BarEntry(i, diastolic));

                    if (systolic > maxBarValue) {
                        maxBarValue = systolic;
                    }
                    if (diastolic > maxBarValue) {
                        maxBarValue = diastolic;
                    }
                }

//                String lastDate = Integer.toString(getDaysInMonth(monthNum));
                BarDataSet systolicBarDataSet = new BarDataSet(systolicEntryArrayList,"Rest Heart Rate");
                systolicBarDataSet.setColor(Color.BLUE);
                systolicBarDataSet.setValueTextColor(Color.BLUE);

                BarDataSet diastolicBarDataSet = new BarDataSet(diastolicEntryArrayList,"Peak Heart Rate");
                diastolicBarDataSet.setColor(Color.WHITE);
                diastolicBarDataSet.setValueTextColor(Color.WHITE);


                boolean hasNonZeroValue = hrList.stream()
                        .flatMap(bp -> Stream.of(bp.getResthr(), bp.getPeakhr()))
                        .map(Float::parseFloat)
                        .anyMatch(w -> w != 0);

                systolicBarDataSet.setValueFormatter(new ValueFormatter() {
                    @Override
                    public String getFormattedValue(float value) {
                        if (value == 0.0f) {
                            return "";
                        } else {
                            return String.valueOf(value);
                        }
                    }
                });

                diastolicBarDataSet.setValueFormatter(new ValueFormatter() {
                    @Override
                    public String getFormattedValue(float value) {
                        if (value == 0.0f) {
                            return "";
                        } else {
                            return String.valueOf(value);
                        }
                    }
                });

                systolicBarDataSet.setDrawValues(hasNonZeroValue);
                diastolicBarDataSet.setDrawValues(hasNonZeroValue);

                //description
                barChart.getDescription().setEnabled(false);

                BarData barData = new BarData(systolicBarDataSet, diastolicBarDataSet);
                float groupSpace = 0.3f;
                float barSpace = 0.02f;
                float barWidth = 0.33f;
                barData.setBarWidth(barWidth);

                barChart.setData(barData);
                barChart.groupBars(0f, groupSpace, barSpace);

                Legend legend = barChart.getLegend();
                legend.setTextColor(Color.WHITE);


                YAxis leftAxis = barChart.getAxisLeft();
                leftAxis.setDrawGridLines(false);
                leftAxis.setAxisMinimum(0f);
                float axisMaximum = Math.max(maxBarValue, maxBarValue+5f); // Set a minimum margin of 10f above the highest value
                leftAxis.setAxisMaximum(axisMaximum);
                leftAxis.setTextColor(Color.WHITE);

                YAxis rightAxis = barChart.getAxisRight();
                rightAxis.setDrawAxisLine(false);
                rightAxis.setDrawGridLines(false);
                rightAxis.setDrawLabels(false);

                initializeChartWithDailyLabels();

                barChart.invalidate();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void heartRateChartYear() {
        query = databaseReference.child("HeartRateM").child(id);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                hrList.clear();

                for (DataSnapshot monthSnapshot : snapshot.getChildren()) {
                    // Get month data
                    String month = monthSnapshot.getKey();
                    float totalSystolic = 0f;
                    float totalDiastolic = 0f;
                    int entryCount = 0;

                    for (DataSnapshot ds : monthSnapshot.getChildren()) {
                        HeartRateModel bp = ds.getValue(HeartRateModel.class);
                        hrList.add(new HeartRateModel(bp.getDate(), bp.getResthr(), bp.getPeakhr()));
                        totalSystolic += Float.parseFloat(bp.getResthr());
                        totalDiastolic += Float.parseFloat(bp.getPeakhr());
                        entryCount++;
                    }

                    DecimalFormat decimalFormat = new DecimalFormat("#.00");
                    // Calculate average systolic and diastolic values for the month
                    float averageSystolic = Float.parseFloat(decimalFormat.format(totalSystolic / entryCount));
                    float averageDiastolic = Float.parseFloat(decimalFormat.format(totalDiastolic / entryCount));

                    // Add the average values to the barEntryArrayList
                    systolicEntryArrayList.add(new BarEntry(getMonthIndex(month), averageSystolic));
                    diastolicEntryArrayList.add(new BarEntry(getMonthIndex(month), averageDiastolic));
                }

                ArrayList<BarEntry> combinedEntryArrayList = new ArrayList<>();
                combinedEntryArrayList.addAll(systolicEntryArrayList);
                combinedEntryArrayList.addAll(diastolicEntryArrayList);

                Collections.sort(combinedEntryArrayList, new Comparator<BarEntry>() {
                    @Override
                    public int compare(BarEntry entry1, BarEntry entry2) {
                        return Float.compare(entry1.getX(), entry2.getX());
                    }
                });

                // Separate the sorted entries back into systolicEntryArrayList and diastolicEntryArrayList
                systolicEntryArrayList.clear();
                diastolicEntryArrayList.clear();

                // Calculate the maximum bar value
                float maxBarValue = 0f;
                for (BarEntry entry : combinedEntryArrayList) {
                    float yValue = entry.getY();
                    if (yValue > maxBarValue) {
                        maxBarValue = yValue;
                    }
                }

                // Update systolicEntryArrayList and diastolicEntryArrayList with maximum value
                for (BarEntry entry : combinedEntryArrayList) {
                    float xValue = entry.getX();
                    float yValue = entry.getY();

                    if (systolicEntryArrayList.size() < diastolicEntryArrayList.size()) {
                        systolicEntryArrayList.add(new BarEntry(xValue, yValue));
                        if (yValue > maxBarValue) {
                            maxBarValue = yValue;
                        }
                    } else {
                        diastolicEntryArrayList.add(new BarEntry(xValue, yValue));
                        if (yValue > maxBarValue) {
                            maxBarValue = yValue;
                        }
                    }
                }

                // Create two BarDataSet objects for systolic and diastolic values
                BarDataSet systolicBarDataSet = new BarDataSet(systolicEntryArrayList,"Average Rest Heart Rate");
                systolicBarDataSet.setColor(Color.BLUE);
                systolicBarDataSet.setValueTextColor(Color.BLUE);


                BarDataSet diastolicBarDataSet = new BarDataSet(diastolicEntryArrayList,"Average Peak Heart Rate");
                diastolicBarDataSet.setColor(Color.WHITE);
                diastolicBarDataSet.setValueTextColor(Color.WHITE);


                // Set custom label formatter for the y-axis values
                systolicBarDataSet.setValueFormatter(new DefaultValueFormatter(2));
                diastolicBarDataSet.setValueFormatter(new DefaultValueFormatter(2));

                // Combine the two BarDataSet objects into a BarData object
                BarData barData = new BarData(systolicBarDataSet, diastolicBarDataSet);

                float barWidth = 0.35f; // Width of each bar
                float barSpace = 0.0f; // Space between bars within a group
                float groupSpace = 0.4f; // Space between bar groups

                barData.setBarWidth(barWidth);
                barChart.setData(barData);

                // Calculate the total width of the group including spacing
                float groupWidth = (barWidth + barSpace) * 2;
                float start = -groupWidth / 2;

                barChart.groupBars(-1, groupSpace, barSpace);

                //exclude 0.0
                boolean hasNonZeroValue = hrList.stream()
                        .flatMap(bp -> Stream.of(bp.getResthr(), bp.getPeakhr()))
                        .map(Float::parseFloat)
                        .anyMatch(w -> w != 0);

                systolicBarDataSet.setValueFormatter(new ValueFormatter() {
                    @Override
                    public String getFormattedValue(float value) {
                        if (value == 0.0f) {
                            return "";
                        } else {
                            return String.valueOf(value);
                        }
                    }
                });

                diastolicBarDataSet.setValueFormatter(new ValueFormatter() {
                    @Override
                    public String getFormattedValue(float value) {
                        if (value == 0.0f) {
                            return "";
                        } else {
                            return String.valueOf(value);
                        }
                    }
                });

                systolicBarDataSet.setDrawValues(hasNonZeroValue);
                diastolicBarDataSet.setDrawValues(hasNonZeroValue);

                barChart.setData(barData);
                barChart.invalidate();
                barChart.getDescription().setEnabled(false);

                YAxis leftAxis = barChart.getAxisLeft();
                leftAxis.setDrawGridLines(false);
                leftAxis.setAxisMinimum(0f);
                float axisMaximum = Math.max(maxBarValue, maxBarValue+5f); // Set a minimum margin of 10f above the highest value
                leftAxis.setAxisMaximum(axisMaximum);
                leftAxis.setTextColor(Color.WHITE);

                YAxis rightAxis = barChart.getAxisRight();
                rightAxis.setDrawAxisLine(false);
                rightAxis.setDrawGridLines(false);
                rightAxis.setDrawLabels(false);

                initializeChartWithMonthlyLabels();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private static int getDaysInMonth(int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, month-1);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    private void handleChartDisplay(String queryPath) {
        barEntryArrayList.clear();
        systolicEntryArrayList.clear();
        diastolicEntryArrayList.clear();
        barChart.clear();

        if(queryPath.equals("weight")){
            if (isYearButtonClicked) {
                weightChartYear();
            } else {
                weightChartMonth();
            }
        }else if(queryPath.equals("bloodPressure")){
            chartTitle.setText("Blood Pressure Chart");

            if (isYearButtonClicked) {
                bloodPressureChartYear();

            } else {
                bloodPressureChartMonth();
            }
        }else if(queryPath.equals("heartRate")){
            chartTitle.setText("Heart Rate Chart");

            if (isYearButtonClicked) {
                heartRateChartYear();

            } else {
                heartRateChartMonth();
            }
        }else if(queryPath.equals("aerobic")){
            chartTitle.setText("Aerobic Chart");

            if (isYearButtonClicked) {
                aerobicChartYear();
            } else {
                aerobicChartMonth();
            }
        }else if(queryPath.equals("flexibility")){
            chartTitle.setText("Flexibility Chart");

            if (isYearButtonClicked) {
                flexChartYear();
            } else {
                flexChartMonth();
            }
        }else if(queryPath.equals("strength")){
            chartTitle.setText("Strengthening Chart");

            if (isYearButtonClicked) {
                strengthChartYear();
            } else {
                strengthChartMonth();
            }
        }
    }

    private int getMonthIndex(String month) {
        switch (month) {
            case "January":
                return 0;
            case "February":
                return 1;
            case "March":
                return 2;
            case "April":
                return 3;
            case "May":
                return 4;
            case "June":
                return 5;
            case "July":
                return 6;
            case "August":
                return 7;
            case "September":
                return 8;
            case "October":
                return 9;
            case "November":
                return 10;
            case "December":
                return 11;
            // Add cases for other months
            default:
                return 0;
        }
    }

    private void initializeChartWithDailyLabels() {
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);

        int currentYear = zonedDateTime.getYear();
        int currentMonth = zonedDateTime.getMonthValue();

        YearMonth yearMonth = YearMonth.of(currentYear, currentMonth);
        int daysInMonth = yearMonth.lengthOfMonth();

        dayLabels = new String[daysInMonth];
        for (int i = 0; i < daysInMonth; i++) {
            if ((i + 1) % 5 == 0) {
                if (daysInMonth == 31 && i == 29) {
                    dayLabels[i] = "31"; // Show "31" if it ends with 31

                } else {
                    dayLabels[i] = String.valueOf(i + 1);

                }
            } else if (i == daysInMonth - 1 && (daysInMonth == 28 || daysInMonth == 29)) {
                dayLabels[i] = String.valueOf(i + 1); // Show the last day if it ends with 28 or 29
            } else {
                dayLabels[i] = ""; // Make other numbers invisible
            }
        }

        xAxisFormatter = new IndexAxisValueFormatter(dayLabels);
        xAxis.setValueFormatter(xAxisFormatter);
        xAxis.setLabelRotationAngle(0f);
        xAxis.setLabelCount(daysInMonth);
        xAxis.setTextSize(12f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setTextColor(Color.WHITE);

    }

    private void initializeChartWithMonthlyLabels() {
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);

        monthLabels = new String[]{"Jan", "Feb", "Mar","Apr", "May", "Jun","Jul", "Aug", "Sep","Oct", "Nov", "Dec" };


        xAxisFormatter = new IndexAxisValueFormatter(monthLabels);
        xAxis.setValueFormatter(xAxisFormatter);
        xAxis.setLabelRotationAngle(0f);
        xAxis.setLabelCount(monthLabels.length);
        xAxis.setTextSize(12f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setTextColor(Color.WHITE);

    }

    @Override
    public void onBackPressed() {
        Intent intent2 = new Intent(AdminChart.this,AdminProgressChartOption.class);
        intent2.putExtra(PATIENT_ID,id);
        startActivity(intent2);
        finish();
    }
}