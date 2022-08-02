package com.firstapp.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    EditText email,pwd;
    Button login;
    DbHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //for text
        email = findViewById(R.id.email);
        pwd = findViewById(R.id.pwd);
        login = findViewById(R.id.login);

        DB=new DbHelper(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Email = email.getText().toString();
                String pass = pwd.getText().toString();

                if (Email.equals("") || pwd.equals(""))
                    Toast.makeText(LoginActivity.this, "All fields required.", Toast.LENGTH_SHORT).show();
                else{
                    Boolean checkuserpass = DB.checkusernamepassword(Email,pass);
                    if(checkuserpass == true){
                        Toast.makeText(LoginActivity.this, "Login Successfully.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this,HomepageActivity.class);
                        //ditu tuka ku
                        //String studname = name.get
                        //intent.putExtra("NAME",name);
                        startActivity(intent);
                    }else{
                        Toast.makeText(LoginActivity.this, "Login Failed.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}