package com.example.bezi.leoniapplication;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ShowDataKabaActivity extends AppCompatActivity {

    private TextView ktime,klad,kposte,kcomponent,kvariante,kquantity,kdate;
    private String slad,sposte,scomponent,squantity,svariante,stime,sdate;

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
        squantity = getIntent().getExtras().get("val4").toString();
        svariante = getIntent().getExtras().get("val5").toString();
        sdate = getIntent().getExtras().get("val6").toString();
        stime = getIntent().getExtras().get("val7").toString();

        klad.setText(slad);
        kposte.setText(sposte);
        kcomponent.setText(scomponent);
        kquantity.setText(squantity);
        kvariante.setText(svariante);
        kdate.setText(sdate);
        ktime.setText(stime);

    }
}


