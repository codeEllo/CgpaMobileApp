package com.firstapp.login;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class RegisteredCourseView extends AppCompatActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeredcourseview);

        TextView textView = findViewById(R.id.view_data);

        DbHelper db = new DbHelper(this);

        String data = db.calcCPA();
        textView.setText(data);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}