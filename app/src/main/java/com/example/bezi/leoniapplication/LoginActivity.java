package com.example.bezi.leoniapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private EditText et1,et2 ;
    private Button b ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et1=(EditText)findViewById(R.id.user);
        et2=(EditText)findViewById(R.id.pass);
        b=(Button)findViewById(R.id.b);

    }


    public void onlogin (View view) {

        String username = et1.getText().toString();
        String password = et2.getText().toString();

        if (username.equals("hela")&& password.equals("leoni")){
            Toast.makeText(getBaseContext(),"Login Successfully !",Toast.LENGTH_LONG).show();
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        }
        else if (username.equals("")|| password.equals("")){
            Toast.makeText(getBaseContext(),"Enter both username and password !",Toast.LENGTH_LONG).show();

        }
        else {
            Toast.makeText(getBaseContext(),"Wrong data entered !",Toast.LENGTH_LONG).show();
        }
    }
}
