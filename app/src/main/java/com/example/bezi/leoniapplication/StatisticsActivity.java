package com.example.bezi.leoniapplication;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class StatisticsActivity extends AppCompatActivity {

    private Button btn;
    private EditText LAD,poste,component,Date;

    private ImageButton DatePick;
    private DatePickerDialog datePickerDialog;
    private Calendar calendar;

    private DatabaseReference SensorRef;

    public static final String TAG = "DataKabaActivity";

    Integer itr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        SensorRef = FirebaseDatabase.getInstance().getReference().child("MyTestData");

        btn = (Button)findViewById(R.id.showstat);
        LAD = (EditText)findViewById(R.id.editText);
        poste = (EditText)findViewById(R.id.editText2);
        component = (EditText)findViewById(R.id.editText3);
        Date = (EditText)findViewById(R.id.editText6);

        DatePick = (ImageButton)findViewById(R.id.date_picker);

        DatePick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                calendar = Calendar.getInstance();

                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                datePickerDialog = new DatePickerDialog(StatisticsActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int mYear, int mMonth, int mDay) {

                        if ((mMonth+1<10) && (mDay<10)){
                            Date.setText(mYear + "-0" + (mMonth + 1)+ "-0" + mDay);

                        }else if ((mMonth+1>9) && (mDay<10)){
                            Date.setText(mYear + "-" + (mMonth + 1)+ "-0" + mDay);

                        }else if ((mMonth+1<10) && (mDay>9)){
                            Date.setText(mYear + "-0" + (mMonth + 1)+ "-" + mDay);

                        }else {
                            Date.setText(mYear + "-" + (mMonth + 1)+ "-" + mDay);

                        }

                    }
                },day,month,year);

                datePickerDialog.show();
            }
        });


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(TextUtils.isEmpty(LAD.getText().toString()))
                {
                    Toast.makeText(StatisticsActivity.this, "Please insert the LAD !", Toast.LENGTH_SHORT).show();
                }
                if(TextUtils.isEmpty(poste.getText().toString()))
                {
                    Toast.makeText(StatisticsActivity.this, "Please insert the poste !", Toast.LENGTH_SHORT).show();
                }
                if(TextUtils.isEmpty(component.getText().toString()))
                {
                    Toast.makeText(StatisticsActivity.this, "Please insert the component !", Toast.LENGTH_SHORT).show();
                }
                if(TextUtils.isEmpty(Date.getText().toString()))
                {
                    Toast.makeText(StatisticsActivity.this, "Please insert the Date !", Toast.LENGTH_SHORT).show();
                }

                else
                {

                    itr = 0;

                    SensorRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            if(dataSnapshot.exists()){

                                for (DataSnapshot childSnapshot: dataSnapshot.getChildren()){


                                    itr ++;

                                    final String key = childSnapshot.getKey();


                                    String testlad = dataSnapshot.child(key).child("lad").getValue().toString();
                                    String testposte = dataSnapshot.child(key).child("poste").getValue().toString();
                                    String testcomponent = dataSnapshot.child(key).child("component").getValue().toString();
                                    String testDate = dataSnapshot.child(key).child("Date").getValue().toString();


                                    if(testlad.equals(LAD.getText().toString())&&(testposte.equals(poste.getText().toString())&&(testcomponent.equals(component.getText().toString()))&&(testDate.equals(Date.getText().toString())))){


                                        final Intent i = new Intent(StatisticsActivity.this,ShowStatisticsActivity.class);

                                        i.putExtra("val1",LAD.getText().toString());
                                        i.putExtra("val2",poste.getText().toString());
                                        i.putExtra("val3",component.getText().toString());
                                        i.putExtra("val6",Date.getText().toString());

                                        startActivity(i);
                                        break;


                                    }   else {

                                        if(itr == dataSnapshot.getChildrenCount())

                                            Toast.makeText(StatisticsActivity.this, "Please verify data !", Toast.LENGTH_SHORT).show();

                                    }

                                }
                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }


                    });}}
        });

    }
}
