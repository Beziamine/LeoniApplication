package com.example.bezi.leoniapplication;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ShowDataKabaActivity extends AppCompatActivity {

    private TextView ktime,klad,kposte,kcomponent,kvariante,kquantity,kdate;
    private String slad,sposte,scomponent,squantity,svariante,stime,sdate;
    Integer itr,nbr,testD;

    private DatabaseReference SensorRef;

    public static final String TAG = "ShowDataKabaActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data_kaba);

        SensorRef = FirebaseDatabase.getInstance().getReference().child("MyTestData");

        kdate = (TextView)findViewById(R.id.date);
        ktime = (TextView)findViewById(R.id.time);
        klad = (TextView)findViewById(R.id.lad);
        kposte = (TextView)findViewById(R.id.poste);
        kcomponent = (TextView)findViewById(R.id.component);
        kvariante = (TextView)findViewById(R.id.variante);
        kquantity = (TextView)findViewById(R.id.quantity);

        slad = getIntent().getExtras().get("val1").toString();
        sposte = getIntent().getExtras().get("val2").toString();
        scomponent = getIntent().getExtras().get("val3").toString();
        sdate = getIntent().getExtras().get("val4").toString();

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

                        if(testlad.equals(slad)&&(testposte.equals(sposte)&&(testcomponent.equals(scomponent)&&(testDate.equals(sdate)))))

                            nbr++;
                    }


                    for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {

                        itr++;

                        final String key = childSnapshot.getKey();

                        String testlad = dataSnapshot.child(key).child("lad").getValue().toString();
                        String testposte = dataSnapshot.child(key).child("poste").getValue().toString();
                        String testcomponent = dataSnapshot.child(key).child("component").getValue().toString();
                        String testDate = dataSnapshot.child(key).child("Date").getValue().toString();

                        if(testlad.equals(slad)&&(testposte.equals(sposte)&&(testcomponent.equals(scomponent)&&(testDate.equals(sdate))))){

                            klad.setText(dataSnapshot.child(key).child("lad").getValue().toString());
                            kposte.setText(dataSnapshot.child(key).child("poste").getValue().toString());
                            kcomponent.setText(dataSnapshot.child(key).child("component").getValue().toString());
                            kquantity.setText(dataSnapshot.child(key).child("nbre_pieces").getValue().toString());
                            kvariante.setText(dataSnapshot.child(key).child("variante").getValue().toString());
                            kdate.setText(dataSnapshot.child(key).child("Date").getValue().toString());
                            ktime.setText(dataSnapshot.child(key).child("Time").getValue().toString());

                        }
                    }
                }

            }



            @Override

            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

    }
}


