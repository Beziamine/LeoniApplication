package com.example.bezi.leoniapplication;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShowStatisticsActivity extends AppCompatActivity {

    private DatabaseReference SensorRef;

    private String slad,sposte,scomponent,sdate;

    Integer itr;

    private LineChart mChart;

    public static final String TAG = "ShowStatisticsActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_statistics);

        mChart = (LineChart)findViewById(R.id.chart);
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(false);



        SensorRef = FirebaseDatabase.getInstance().getReference().child("MyTestData");

        slad = getIntent().getExtras().get("val1").toString();
        sposte = getIntent().getExtras().get("val2").toString();
        scomponent = getIntent().getExtras().get("val3").toString();
        sdate = getIntent().getExtras().get("val6").toString();

        itr = 0;

        SensorRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){

                    ArrayList<Entry> yValues = new ArrayList<>();

                    ArrayList<String> values = new ArrayList<>();

                    for (DataSnapshot childSnapshot: dataSnapshot.getChildren()){

                        final String key = childSnapshot.getKey();


                        String testlad = dataSnapshot.child(key).child("lad").getValue().toString();
                        String testposte = dataSnapshot.child(key).child("poste").getValue().toString();
                        String testcomponent = dataSnapshot.child(key).child("component").getValue().toString();
                        String testDate = dataSnapshot.child(key).child("Date").getValue().toString();


                        if(testlad.equals(slad)&&(testposte.equals(sposte)&&(testcomponent.equals(scomponent))&&(testDate.equals(sdate)))){

                            yValues.add(new Entry(itr,Float.valueOf(dataSnapshot.child(key).child("nbre_pieces").getValue().toString())));

                            values.add(dataSnapshot.child(key).child("Time").getValue().toString());

                            itr ++;

                        }
                    }

                    Log.i(TAG,"size = "+ yValues.size());

                    LineDataSet set1 = new LineDataSet(yValues, scomponent = getIntent().getExtras().get("val3").toString());

                    set1.setFillAlpha(110);
                    set1.setColor(Color.RED);
                    set1.setDrawCircles(true);
                    set1.setLineWidth(3f);
                    set1.setValueTextSize(15f);

                    LineData data = new LineData(set1);
                    mChart.setData(data);

                    XAxis xAxis = mChart.getXAxis();
                    xAxis.setValueFormatter(new MyXAxisValueFormatter(values));
                    xAxis.setGranularity(1);
                    xAxis.setTextSize(10f);

                    YAxis yAxis = mChart.getAxisLeft();
                    yAxis.setTextSize(10f);

                    mChart.getDescription().setText("Variation de la quantité pendant la journée "+sdate);
                    mChart.getDescription().setTextSize(15f);


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });

    }

    public class MyXAxisValueFormatter implements IAxisValueFormatter {
        private ArrayList<String> mValues;
        public MyXAxisValueFormatter(ArrayList<String> values){
            this.mValues = values;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return mValues.get((int) value);
        }
    }
}


