package com.firstapp.login;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GradeViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade_view);

        TextView textView = findViewById(R.id.view_grade);

        DbHelper db = new DbHelper(this);

        String data = db.getGradeData();
        textView.setText(data);
    }
}