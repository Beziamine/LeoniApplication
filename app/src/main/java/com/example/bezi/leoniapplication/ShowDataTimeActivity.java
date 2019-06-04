package com.example.bezi.leoniapplication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ShowDataTimeActivity extends AppCompatActivity {

    private DatabaseReference SensorRef,SensorRefNotif,NotifRef;

    public static final String TAG = "ShowDataTimeActivity";

    private Model model;

    private ArrayAdapter<Model> adapter;
    private ArrayList<Model> arraylist;

    private ArrayList<String> arrayList2;
    private ArrayAdapter<String> adapter2;

    private ArrayList<Integer> arrayList3;
    private ArrayAdapter<Integer> adapter3;

    private ListView listView;

    Integer itr,nbr,testD;

    private int k,size,size2;
    private String title,message,slad,sposte,scomponent,sdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data_time);

        slad = getIntent().getExtras().get("val1").toString();
        sposte = getIntent().getExtras().get("val2").toString();
        scomponent = getIntent().getExtras().get("val3").toString();
        sdate = getIntent().getExtras().get("val4").toString();

        listView = (ListView) findViewById(R.id.list);

        SensorRef = FirebaseDatabase.getInstance().getReference().child("MyTestData");
        SensorRefNotif = FirebaseDatabase.getInstance().getReference().child("MyTestData");
        NotifRef = FirebaseDatabase.getInstance().getReference();

        arraylist = new ArrayList<>();
        adapter = new ArrayAdapter<Model>(this, android.R.layout.simple_list_item_1,arraylist);

        arrayList2 = new ArrayList<>();
        adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,android.R.id.text1, arrayList2);
        listView.setAdapter(adapter2);

        arrayList3 = new ArrayList<>();
        adapter3 = new ArrayAdapter<Integer>(this, android.R.layout.simple_list_item_1,arrayList3);

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


                    if(lad.equals(slad)&&(poste.equals(sposte)&&(component.equals(scomponent)&&(date.equals(sdate)))))

                    arraylist.add(new Model(date,time,component,variante,Float.valueOf(id),Float.valueOf(kabaweight),Float.valueOf(lad),Float.valueOf(quantity),Float.valueOf(poste)));
                }

                size = arraylist.size();

                for (int i = 0; i < size; i++) {

                    if(arraylist.get(i).getNbre_pieces() <= 0){
                        arrayList3.add(i);
                    }else {

                        arrayList3.add(-1);
                    }

                }

                k =0;

                for (int i = 0; i <size; i++) {

                    if(arrayList3.get(i)!= i){

                        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
                        Date d1 = null;
                        Date d2 = null;

                        try {
                            d1 = sdf.parse(arraylist.get(i-(1*k)).getTime());
                            d2 = sdf.parse(arraylist.get(i).getTime());

                            long millse = d2.getTime() - d1.getTime();
                            long mills = Math.abs(millse);

                            if(mills != 0) {

                                int Hours = (int) (mills / (1000 * 60 * 60));
                                int Mins = (int) (mills / (1000 * 60)) % 60;
                                long Secs = (int) (mills / 1000) % 60;

                                String diff = Hours + " hours - " + Mins + " minutes - " + Secs + " seconds";

                                arrayList2.add("Date : " + arraylist.get(i).getDate() + '\n'
                                        + "LAD : " + arraylist.get(i).getLad().toString() + '\n'
                                        + "Poste : " + arraylist.get(i).getPoste().toString() + '\n'
                                        + "Component : " + arraylist.get(i).getComponent() + '\n'
                                        + "Empty Time : " + diff);

                                adapter2.notifyDataSetChanged();

                                k = 0;

                            }

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                    }else{

                        k++;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
}
}
