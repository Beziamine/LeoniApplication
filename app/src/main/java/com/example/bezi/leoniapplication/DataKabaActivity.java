package com.example.bezi.leoniapplication;

import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import java.util.HashMap;
import java.util.Map;


public class DataKabaActivity extends AppCompatActivity {

    private Button btn ;
    private EditText LAD , poste , component , Date  ;

    private DatabaseReference SensorRef,SensorRefNotif;

    private ImageButton DatePick;
    private DatePickerDialog datePickerDialog;
    private Calendar calendar;

    public static final String TAG = "DataKabaActivity";

    Integer itr,nbr,testD;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_kaba);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("Chanel_ID","Channel_Name",NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("Channel_Desc");
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        SensorRef = FirebaseDatabase.getInstance().getReference().child("MyTestData");
        SensorRefNotif = FirebaseDatabase.getInstance().getReference().child("MyTestData");

        btn = (Button)findViewById(R.id.btn);
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

                datePickerDialog = new DatePickerDialog(DataKabaActivity.this, new DatePickerDialog.OnDateSetListener() {
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
                    Toast.makeText(DataKabaActivity.this, "Please insert the LAD !", Toast.LENGTH_SHORT).show();
                }
                if(TextUtils.isEmpty(poste.getText().toString()))
                {
                    Toast.makeText(DataKabaActivity.this, "Please insert the poste !", Toast.LENGTH_SHORT).show();
                }
                if(TextUtils.isEmpty(component.getText().toString()))
                {
                    Toast.makeText(DataKabaActivity.this, "Please insert the component !", Toast.LENGTH_SHORT).show();
                }
                if(TextUtils.isEmpty(Date.getText().toString()))
                {
                    Toast.makeText(DataKabaActivity.this, "Please insert the Date !", Toast.LENGTH_SHORT).show();
                }

                else
                {

                    itr = 0;
                    nbr = 0;
                    testD = 0;


                    SensorRef.orderByChild("Date").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            if(dataSnapshot.exists()){


                                for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {


                                    final String key = childSnapshot.getKey();

                                    String testlad = dataSnapshot.child(key).child("lad").getValue().toString();
                                    String testposte = dataSnapshot.child(key).child("poste").getValue().toString();
                                    String testcomponent = dataSnapshot.child(key).child("component").getValue().toString();
                                    String testDate = dataSnapshot.child(key).child("Date").getValue().toString();

                                    if(testlad.equals(LAD.getText().toString())&&(testposte.equals(poste.getText().toString())&&(testcomponent.equals(component.getText().toString()))&&(testDate.equals(Date.getText().toString()))))

                                    nbr++;

                                    }

                                for (DataSnapshot childSnapshot: dataSnapshot.getChildren()){

                                    itr++;

                                    final String key = childSnapshot.getKey();


                                    String testlad = dataSnapshot.child(key).child("lad").getValue().toString();
                                    String testposte = dataSnapshot.child(key).child("poste").getValue().toString();
                                    String testcomponent = dataSnapshot.child(key).child("component").getValue().toString();
                                    String testDate = dataSnapshot.child(key).child("Date").getValue().toString();


                                    if(testlad.equals(LAD.getText().toString())&&(testposte.equals(poste.getText().toString())&&(testcomponent.equals(component.getText().toString()))&&(testDate.equals(Date.getText().toString())))){

                                        testD++;
                                        final Intent i = new Intent(DataKabaActivity.this,ShowDataKabaActivity.class);

                                        i.putExtra("val1",LAD.getText().toString());
                                        i.putExtra("val2",poste.getText().toString());
                                        i.putExtra("val3",component.getText().toString());
                                        i.putExtra("val4",dataSnapshot.child(key).child("nbre_pieces").getValue().toString());
                                        i.putExtra("val5",dataSnapshot.child(key).child("variante").getValue().toString());
                                        i.putExtra("val6",Date.getText().toString());
                                        i.putExtra("val7",dataSnapshot.child(key).child("Time").getValue().toString());
                                        String testTime = dataSnapshot.child(key).child("Time").getValue().toString();

                                       if (Float.valueOf(dataSnapshot.child(key).child("nbre_pieces").getValue().toString())<16 && testD == nbr) {
                                           String title = "Alerte LAD: "+ testlad+ " / Poste: "+ testposte+ " / "+testTime;
                                           String message = testcomponent + " en manque de pièces !!";
                                           createNotification(title, message);
                                       }


                                        if (testD == nbr) {
                                            startActivity(i);
                                            break;
                                        }


                                    }   else {

                                        if(itr == dataSnapshot.getChildrenCount())

                                            Toast.makeText(DataKabaActivity.this, "Please verify data !", Toast.LENGTH_SHORT).show();

                                    }

                                }
                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }


                    });}}
        });



        SensorRefNotif.orderByChild("Date").limitToLast(1).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){


                    for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {

                        final String key = childSnapshot.getKey();

                        String testlad = dataSnapshot.child(key).child("lad").getValue().toString();
                        String testposte = dataSnapshot.child(key).child("poste").getValue().toString();
                        String testcomponent = dataSnapshot.child(key).child("component").getValue().toString();
                        String testDate = dataSnapshot.child(key).child("Date").getValue().toString();
                        String testTime = dataSnapshot.child(key).child("Time").getValue().toString();

                        if (Float.valueOf(dataSnapshot.child(key).child("nbre_pieces").getValue().toString())<16) {
                            String title = "Alerte LAD: "+ testlad+ " / Poste: "+ testposte+ " / "+testTime;
                            String message = testcomponent + " en manque de pièces !!";
                            createNotification(title, message);
                        }
                    }

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });




    }

    public void createNotification(String title, String message) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"Chanel_ID")
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(1, builder.build());

    }



}
