package com.example.exerciseprescription;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.exerciseprescription.class2.AerobicModel;
import com.example.exerciseprescription.class2.BloodPressureModel;
import com.example.exerciseprescription.class2.FlexibilityModel;
import com.example.exerciseprescription.class2.HeartRateModel;
import com.example.exerciseprescription.class2.StrengthModel;
import com.example.exerciseprescription.class2.WeightModel;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.stream.Stream;

public class ChartExample extends AppCompatActivity {

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
    String id,queryPath,queryPath2;
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

    Button weekBtn,monthBtn,yearBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_example);

        barChart = findViewById(R.id.barChart);
        chartTitle = findViewById(R.id.chartTitle);

        weekBtn = findViewById(R.id.weekBtn);
        monthBtn = findViewById(R.id.monthBtn);
        yearBtn = findViewById(R.id.yearBtn);

        barColors = new int[] {
                ContextCompat.getColor(this, R.color.maroon)};

        fUser = mAuth.getCurrentUser();
        id = fUser.getUid();

        //data entry
        barEntryArrayList = new ArrayList<>();
        labelNames = new ArrayList<>();

        Intent intent = getIntent();
        queryPath  = intent.getStringExtra(UserExerciseChart.CHART_TYPE);

        Intent intent2 = getIntent();
        queryPath2  = intent2.getStringExtra(UserHealthChart.CHART_TYPE);

        if(queryPath.equals("weight")){
            weightChartMonth();
        }else if(queryPath.equals("bloodPressure")){
            bloodPressureChartMonth();
            chartTitle.setText("Blood Pressure Chart");
        }else if(queryPath.equals("heartRate")){
            heartRateChartMonth();
            chartTitle.setText("Heart Rate Chart");
        }else if(queryPath.equals("aerobic")){
            aerobicChartMonth();
            chartTitle.setText("Aerobic Chart");
        }else if(queryPath.equals("flexibility")){
            flexChartMonth();
            chartTitle.setText("Flexibility Chart");
        }else if(queryPath.equals("strength")){
            strengthChartMonth();
            chartTitle.setText("Strengthening Chart");
        }
    }

    public void weightChart(){
        query = databaseReference.child("WeightM").child(id).child(month);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                weightList.clear();

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

                    barEntryArrayList.add(new BarEntry(i, weight));
//                    labelNames.add(date);
                }

                String lastDate = Integer.toString(getDaysInMonth(monthNum));
                BarDataSet barDataSet = new BarDataSet(barEntryArrayList,"01-"+lastDate+" "+month+" "+year);
                barDataSet.setColors(ColorTemplate.createColors(barColors));

                boolean hasNonZeroValue = weightList.stream()
                        .map(WeightModel::getWeight)
                        .map(Float::parseFloat)
                        .anyMatch(w -> w != 0);

                barDataSet.setDrawValues(hasNonZeroValue);

                /*for(int i=0; i< weightList.size(); i++){
                    float weightF = Float.parseFloat(weightList.get(i).getWeight());

                    if(weightF==0){
                        barDataSet.setDrawValues(false);
                    }
                }*/

                //description
                barChart.getDescription().setEnabled(false);


                BarData barData = new BarData(barDataSet);
                barChart.setData(barData);

                YAxis leftAxis = barChart.getAxisLeft();
//                leftAxis.setDrawAxisLine(false);
                leftAxis.setDrawGridLines(false);
//                leftAxis.setDrawLabels(false);
                leftAxis.setAxisMinimum(0f);
                leftAxis.setAxisMaximum(100f);

                YAxis rightAxis = barChart.getAxisRight();
                rightAxis.setDrawAxisLine(false);
                rightAxis.setDrawGridLines(false);
                rightAxis.setDrawLabels(false);

                XAxis xAxis = barChart.getXAxis();
//                xAxis.setValueFormatter(new IndexAxisValueFormatter(labelNames));
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis.setDrawGridLines(false);
//                xAxis.setDrawAxisLine(false);
//                xAxis.setGranularity(1f);
//                xAxis.setLabelCount(labelNames.size());
//                xAxis.setLabelRotationAngle(0);

//                barChart.animateY(2000);
                barChart.invalidate();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void weightChartDay2(){
        query = databaseReference.child("WeightD").child(id);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                weightList.clear();

                for (DataSnapshot ds : snapshot.getChildren()) {
                    WeightModel weight = ds.getValue(WeightModel.class);
                    weightList.add(new WeightModel(weight.getDate(),weight.getWeight()));
                }

                for(int i=0; i< weightList.size(); i++){
                    String date = weightList.get(i).getDate();
                    float weight = Float.parseFloat(weightList.get(i).getWeight());

                    barEntryArrayList.add(new BarEntry(i,weight));
                    labelNames.add(date);
                }


                BarDataSet barDataSet = new BarDataSet(barEntryArrayList,"Daily Weight");
                barDataSet.setColors(ColorTemplate.createColors(barColors));

                //description
                barChart.getDescription().setEnabled(false);

                BarData barData = new BarData(barDataSet);
                barChart.setData(barData);

                YAxis leftAxis = barChart.getAxisLeft();
                leftAxis.setDrawGridLines(false);
                leftAxis.setAxisMinimum(0f);
                leftAxis.setAxisMaximum(100f);

                YAxis rightAxis = barChart.getAxisRight();
                rightAxis.setDrawAxisLine(false);
                rightAxis.setDrawGridLines(false);
                rightAxis.setDrawLabels(false);

                XAxis xAxis = barChart.getXAxis();
                xAxis.setValueFormatter(new IndexAxisValueFormatter(labelNames));

                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis.setDrawGridLines(false);
                xAxis.setDrawAxisLine(false);
//                xAxis.setGranularity(1f);
//                xAxis.setLabelCount(labelNames.size());
//                xAxis.setLabelRotationAngle(0);
//                barChart.animateY(2000);
                barChart.invalidate();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void weightChartDay(){
        query = databaseReference.child("WeightM").child(id).child(month);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                weightList.clear();

                for (DataSnapshot ds : snapshot.getChildren()) {
                    WeightModel weight = ds.getValue(WeightModel.class);
                    weightList.add(new WeightModel(weight.getDate(),weight.getWeight()));
                }

                ArrayList<BarEntry> barEntryArrayList = new ArrayList<>();
                ArrayList<String> labelNames = new ArrayList<>();

                for(int i=0; i< weightList.size(); i++){
                    String date = weightList.get(i).getDate();
                    float weight = Float.parseFloat(weightList.get(i).getWeight());

                    // add a new BarEntry for each day of the week
                    Calendar cal = Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    try {
                        cal.setTime(sdf.parse(date));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK) - 1;
                    if (dayOfWeek < 1) {
                        dayOfWeek += 7;
                    }
                    barEntryArrayList.add(new BarEntry(dayOfWeek, weight));

                    // add the day of the week as the label
                    SimpleDateFormat sdfLabel = new SimpleDateFormat("EEE", Locale.getDefault());
                    labelNames.add(sdfLabel.format(cal.getTime()));
                }

                BarDataSet barDataSet = new BarDataSet(barEntryArrayList,"01-"+getDaysInMonth(monthNum)+" "+month+" "+year);
                barDataSet.setColors(ColorTemplate.createColors(barColors));

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

                YAxis leftAxis = barChart.getAxisLeft();
                leftAxis.setDrawGridLines(false);
                leftAxis.setAxisMinimum(0f);
                leftAxis.setAxisMaximum(100f);

                YAxis rightAxis = barChart.getAxisRight();
                rightAxis.setDrawAxisLine(false);
                rightAxis.setDrawGridLines(false);
                rightAxis.setDrawLabels(false);

                XAxis xAxis = barChart.getXAxis();
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis.setDrawGridLines(false);
                xAxis.setValueFormatter(new IndexAxisValueFormatter(labelNames));

                barChart.invalidate();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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

                for(int i=0; i< weightList.size(); i++){
                    String date = weightList.get(i).getDate();
                    float weight = Float.parseFloat(weightList.get(i).getWeight());

                    barEntryArrayList.add(new BarEntry(i, weight));
//                    labelNames.add(date);
                }

                String lastDate = Integer.toString(getDaysInMonth(monthNum));
                BarDataSet barDataSet = new BarDataSet(barEntryArrayList,"01-"+lastDate+" "+month+" "+year);
                barDataSet.setColors(ColorTemplate.createColors(barColors));

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

                YAxis leftAxis = barChart.getAxisLeft();
                leftAxis.setDrawGridLines(false);
                leftAxis.setAxisMinimum(0f);
                leftAxis.setAxisMaximum(100f);

                YAxis rightAxis = barChart.getAxisRight();
                rightAxis.setDrawAxisLine(false);
                rightAxis.setDrawGridLines(false);
                rightAxis.setDrawLabels(false);

                XAxis xAxis = barChart.getXAxis();
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis.setDrawGridLines(false);

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
                    aerobicList.add(new AerobicModel(weight.getDate(),weight.getDuration()));
                }

                for(int i=0; i< aerobicList.size(); i++){
                    String date = aerobicList.get(i).getDate();
                    int weight = Integer.parseInt(aerobicList.get(i).getDuration());

                    barEntryArrayList.add(new BarEntry(i, weight));
//                    labelNames.add(date);
                }

                String lastDate = Integer.toString(getDaysInMonth(monthNum));
                BarDataSet barDataSet = new BarDataSet(barEntryArrayList,"01-"+lastDate+" "+month+" "+year);
                barDataSet.setColors(ColorTemplate.createColors(barColors));

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

                YAxis leftAxis = barChart.getAxisLeft();
                leftAxis.setDrawGridLines(false);
                leftAxis.setAxisMinimum(0f);
                leftAxis.setAxisMaximum(70f);

                YAxis rightAxis = barChart.getAxisRight();
                rightAxis.setDrawAxisLine(false);
                rightAxis.setDrawGridLines(false);
                rightAxis.setDrawLabels(false);

                XAxis xAxis = barChart.getXAxis();
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis.setDrawGridLines(false);

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

                for(int i=0; i< flexList.size(); i++){
                    String date = flexList.get(i).getDate();
                    int weight = Integer.parseInt(flexList.get(i).getDuration());

                    barEntryArrayList.add(new BarEntry(i, weight));
//                    labelNames.add(date);
                }

                String lastDate = Integer.toString(getDaysInMonth(monthNum));
                BarDataSet barDataSet = new BarDataSet(barEntryArrayList,"01-"+lastDate+" "+month+" "+year);
                barDataSet.setColors(ColorTemplate.createColors(barColors));

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

                YAxis leftAxis = barChart.getAxisLeft();
                leftAxis.setDrawGridLines(false);
                leftAxis.setAxisMinimum(0f);
                leftAxis.setAxisMaximum(10f);

                YAxis rightAxis = barChart.getAxisRight();
                rightAxis.setDrawAxisLine(false);
                rightAxis.setDrawGridLines(false);
                rightAxis.setDrawLabels(false);

                XAxis xAxis = barChart.getXAxis();
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis.setDrawGridLines(false);

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

                for(int i=0; i< strengthList.size(); i++){
                    String date = strengthList.get(i).getDate();
                    int weight = Integer.parseInt(strengthList.get(i).getDuration());

                    barEntryArrayList.add(new BarEntry(i, weight));
//                    labelNames.add(date);
                }

                String lastDate = Integer.toString(getDaysInMonth(monthNum));
                BarDataSet barDataSet = new BarDataSet(barEntryArrayList,"01-"+lastDate+" "+month+" "+year);
                barDataSet.setColors(ColorTemplate.createColors(barColors));

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

                YAxis leftAxis = barChart.getAxisLeft();
                leftAxis.setDrawGridLines(false);
                leftAxis.setAxisMinimum(0f);
                leftAxis.setAxisMaximum(70f);

                YAxis rightAxis = barChart.getAxisRight();
                rightAxis.setDrawAxisLine(false);
                rightAxis.setDrawGridLines(false);
                rightAxis.setDrawLabels(false);

                XAxis xAxis = barChart.getXAxis();
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis.setDrawGridLines(false);

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

                ArrayList<BarEntry> systolicEntryArrayList = new ArrayList<>();
                ArrayList<BarEntry> diastolicEntryArrayList = new ArrayList<>();

                for(int i=0; i< bpList.size(); i++){
                    String date = bpList.get(i).getDate();
                    float systolic = Float.parseFloat(bpList.get(i).getSystolic());
                    float diastolic = Float.parseFloat(bpList.get(i).getDiastolic());

                    systolicEntryArrayList.add(new BarEntry(i, systolic));
                    diastolicEntryArrayList.add(new BarEntry(i, diastolic));
                }

                String lastDate = Integer.toString(getDaysInMonth(monthNum));
                BarDataSet systolicBarDataSet = new BarDataSet(systolicEntryArrayList,"Systolic");
                systolicBarDataSet.setColor(Color.BLUE);
                systolicBarDataSet.setValueTextColor(Color.BLUE);
                BarDataSet diastolicBarDataSet = new BarDataSet(diastolicEntryArrayList,"Diastolic");
                diastolicBarDataSet.setColor(Color.RED);
                diastolicBarDataSet.setValueTextColor(Color.RED);

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

                YAxis leftAxis = barChart.getAxisLeft();
                leftAxis.setDrawGridLines(false);
                leftAxis.setAxisMinimum(0f);
                leftAxis.setAxisMaximum(200f); // set maximum y-axis value as needed

                YAxis rightAxis = barChart.getAxisRight();
                rightAxis.setDrawAxisLine(false);
                rightAxis.setDrawGridLines(false);
                rightAxis.setDrawLabels(false);

                XAxis xAxis = barChart.getXAxis();
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis.setDrawGridLines(false);

                barChart.invalidate();
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

                ArrayList<BarEntry> systolicEntryArrayList = new ArrayList<>();
                ArrayList<BarEntry> diastolicEntryArrayList = new ArrayList<>();

                for(int i=0; i< hrList.size(); i++){
                    String date = hrList.get(i).getDate();
                    float systolic = Float.parseFloat(hrList.get(i).getResthr());
                    float diastolic = Float.parseFloat(hrList.get(i).getPeakhr());

                    systolicEntryArrayList.add(new BarEntry(i, systolic));
                    diastolicEntryArrayList.add(new BarEntry(i, diastolic));
                }

                String lastDate = Integer.toString(getDaysInMonth(monthNum));
                BarDataSet systolicBarDataSet = new BarDataSet(systolicEntryArrayList,"Rest Heart Rate");
                systolicBarDataSet.setColor(Color.BLUE);
                systolicBarDataSet.setValueTextColor(Color.BLUE);
                BarDataSet diastolicBarDataSet = new BarDataSet(diastolicEntryArrayList,"Peak Heart Rate");
                diastolicBarDataSet.setColor(Color.RED);
                diastolicBarDataSet.setValueTextColor(Color.RED);

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

                YAxis leftAxis = barChart.getAxisLeft();
                leftAxis.setDrawGridLines(false);
                leftAxis.setAxisMinimum(0f);
                leftAxis.setAxisMaximum(200f); // set maximum y-axis value as needed

                YAxis rightAxis = barChart.getAxisRight();
                rightAxis.setDrawAxisLine(false);
                rightAxis.setDrawGridLines(false);
                rightAxis.setDrawLabels(false);

                XAxis xAxis = barChart.getXAxis();
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis.setDrawGridLines(false);

                barChart.invalidate();
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

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ChartExample.this, UserProgressChart.class));
        finish();
    }
}