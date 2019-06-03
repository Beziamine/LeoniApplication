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
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class DataKabaActivity extends AppCompatActivity {

    private Button btn ;
    private EditText LAD , poste , component , Date  ;

    private DatabaseReference SensorRef,SensorRefNotif,NotifRef;

    private ImageButton DatePick;
    private DatePickerDialog datePickerDialog;
    private Calendar calendar;

    public static final String TAG = "DataKabaActivity";

    private Model model;
    private ArrayAdapter<Model> adapter;
    private ArrayList<Model> arraylist;

    Integer itr,nbr,testD;

    private int k,size;

    private String title,message;



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
        NotifRef = FirebaseDatabase.getInstance().getReference();

        //model = new Model();
        arraylist = new ArrayList<>();
        adapter = new ArrayAdapter<Model>(this, android.R.layout.simple_list_item_1, arraylist);

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


        SensorRef.orderByChild("Date").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {

                    final String key = childSnapshot.getKey();

                    String lad = dataSnapshot.child(key).child("lad").getValue().toString();
                    String poste = dataSnapshot.child(key).child("poste").getValue().toString();
                    String component = dataSnapshot.child(key).child("component").getValue().toString();
                    String date = dataSnapshot.child(key).child("Date").getValue().toString();
                    String time = dataSnapshot.child(key).child("Time").getValue().toString();
                    String id = dataSnapshot.child(key).child("ID").getValue().toString();
                    String quantity = dataSnapshot.child(key).child("nbre_pieces").getValue().toString();
                    String variante = dataSnapshot.child(key).child("variante").getValue().toString();
                    String kabaweight = dataSnapshot.child(key).child("kabaweight").getValue().toString();

                    arraylist.add(new Model(date,time,component,variante,Float.valueOf(id),Float.valueOf(kabaweight),Float.valueOf(lad),Float.valueOf(quantity),Float.valueOf(poste)));
                }

                size = arraylist.size();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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
                    int i = 0;
                    boolean find = false;

                    while (i < size && !find ){

                        if(Date.getText().toString().equals(arraylist.get(i).getDate()) && component.getText().toString().equals(arraylist.get(i).getComponent())
                                && (Float.valueOf(LAD.getText().toString())).toString().equals(arraylist.get(i).getLad().toString())
                                && (Float.valueOf(poste.getText().toString())).toString().equals(arraylist.get(i).getPoste().toString())) {

                            k = i;
                            find = true;

                        } else

                            i++;
                    }

                    if(find){

                        final Intent intent = new Intent(DataKabaActivity.this, ShowDataKabaActivity.class);

                        intent.putExtra("val1", LAD.getText().toString());
                        intent.putExtra("val2", poste.getText().toString());
                        intent.putExtra("val3", component.getText().toString());
                        intent.putExtra("val4", Date.getText().toString());
                        startActivity(intent);

                    } else {

                        Toast.makeText(DataKabaActivity.this, "Please verify data !", Toast.LENGTH_SHORT).show();
                    }

                }}

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

                        if (Float.valueOf(dataSnapshot.child(key).child("nbre_pieces").getValue().toString())<10) {

                            title = "Alerte LAD: "+ testlad+ " / Poste: "+ testposte+ " / "+testTime;
                            message = testcomponent + " en manque de pièces !!";
                            NotifRef.child("Notification").setValue("true");

                       } else

                            NotifRef.child("Notification").setValue("false");

                    }

                }
                
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });

        NotifRef.child("Notification").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){

                    String test = dataSnapshot.getValue().toString();

                    if(test.equals("true"))
                        createNotification(title, message);

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
