package com.firstapp.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    EditText sid, name, email, pwd;
    Button signup, login;
    DbHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //for text
        sid = findViewById(R.id.sid);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        pwd = findViewById(R.id.pwd);
        //for button
        signup = findViewById(R.id.signup);
        login = findViewById(R.id.login);

        DB=new DbHelper(this);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String studid = sid.getText().toString();
                String studname = name.getText().toString();
                String idemail = email.getText().toString();
                String pass = pwd.getText().toString();


                //check if all the field is filled by the user
                if(TextUtils.isEmpty(studid) || TextUtils.isEmpty(studname) || TextUtils.isEmpty(idemail) || TextUtils.isEmpty(pass))
                    Toast.makeText(RegisterActivity.this, "All fields required.", Toast.LENGTH_SHORT).show();
                else{
                        Boolean checkuser = DB.checkusername(studid);
                        if (checkuser == false) {
                            Boolean insert = DB.insertStudent(studname,idemail,pass);
                            if(insert==true){
                                Toast.makeText(RegisterActivity.this, "Registered Successfully.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(RegisterActivity.this, "Registration Failed.", Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(RegisterActivity.this, "User already exists", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}