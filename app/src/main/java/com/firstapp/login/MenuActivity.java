package com.firstapp.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


public class MenuActivity extends AppCompatActivity {

    //Declares btnNext as type of Button
    Button btnCalculate,btnGrade,btnCourseInfo,btnRCourse;
    //TextView textView, textView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);//xml layout for homepage screen

        //Sets "GPA GRADING SYSTEM" as label in the App Bar / Action Bar
        getSupportActionBar().setTitle("GPA GRADING SYSTEM");

        btnCalculate = (Button) findViewById(R.id.btnCalculate);
        btnCourseInfo = (Button) findViewById(R.id.btnCourseInfo);
        btnGrade = (Button) findViewById(R.id.btnGrade);
        btnRCourse = (Button) findViewById(R.id.btnRCourse);

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;

                //STUDENT ACTIVITY
                intent = new Intent(MenuActivity.this, MainActivity.class);
                startActivity(intent);
                //finish();
            }
        });

        btnCourseInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;

                //COURSE ACTIVITY
                intent = new Intent(MenuActivity.this, CourseActivity.class);
                startActivity(intent);
                //finish();
            }});


        btnGrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;

                //grade ACTIVITY
                    intent = new Intent(MenuActivity.this, GradeActivity.class);
                startActivity(intent);
                //finish();
            }});

        btnRCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;

                //registeredCourse ACTIVITY
                intent = new Intent(MenuActivity.this, RegisteredCourse.class);
                startActivity(intent);
                //finish();
            }});


}
}
