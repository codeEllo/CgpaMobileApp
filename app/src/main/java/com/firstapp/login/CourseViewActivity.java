package com.firstapp.login;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CourseViewActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listcourse);

        TextView textView = findViewById(R.id.view_data);

        DbHelper db = new DbHelper(this);

        String data = db.getCourseData();
        textView.setText(data);
    }
}
