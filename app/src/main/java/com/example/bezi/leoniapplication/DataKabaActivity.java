package com.example.bezi.leoniapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DataKabaActivity extends AppCompatActivity {

    private Button btn ;
    private EditText LAD , poste , component ;

    private DatabaseReference SensorRef;

    public static final String TAG = "DataKabaActivity";

    Integer itr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_kaba);

        SensorRef = FirebaseDatabase.getInstance().getReference().child("MyTestData");

        btn = (Button)findViewById(R.id.btn);
        LAD = (EditText)findViewById(R.id.editText);
        poste = (EditText)findViewById(R.id.editText2);
        component = (EditText)findViewById(R.id.editText3);


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
                else
                {

                    itr = 0;

                    SensorRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            if(dataSnapshot.exists()){

                                for (DataSnapshot childSnapshot: dataSnapshot.getChildren()){


                                    itr ++;

                                    String key = childSnapshot.getKey();

                                    String testlad = dataSnapshot.child(key).child("lad").getValue().toString();
                                    String testposte = dataSnapshot.child(key).child("poste").getValue().toString();
                                    String testcomponent = dataSnapshot.child(key).child("component").getValue().toString();

                                    if(testlad.equals(LAD.getText().toString())&&(testposte.equals(poste.getText().toString())&&(testcomponent.equals(component.getText().toString())))){


                                        Intent i = new Intent(DataKabaActivity.this,ShowDataKabaActivity.class);

                                        i.putExtra("val1",LAD.getText().toString());
                                        i.putExtra("val2",poste.getText().toString());
                                        i.putExtra("val3",component.getText().toString());
                                        i.putExtra("val4",dataSnapshot.child(key).child("nbre_pieces").getValue().toString());
                                        i.putExtra("val5",dataSnapshot.child(key).child("variante").getValue().toString());
                                        i.putExtra("val6",dataSnapshot.child(key).child("Date").getValue().toString()+" "+
                                                dataSnapshot.child(key).child("Time").getValue().toString());

                                        startActivity(i);
                                        break;
                                    }

                                    else {

                                        if(itr == dataSnapshot.getChildrenCount())

                                        Toast.makeText(DataKabaActivity.this, "Please verify data !", Toast.LENGTH_SHORT).show();

                                    }



                                }

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });



            }}
        });

    }
}
