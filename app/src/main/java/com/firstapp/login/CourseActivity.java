package com.firstapp.login;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CourseActivity extends AppCompatActivity implements View.OnClickListener{

    DbHelper db;

    EditText editCourseId,editCourseCode ,editCourseName ,editCourseCredit;
    Button buttonInsert, buttonView, buttonDelete,buttonUpdate, buttonSearch;

    String CourseId;
    String courseCode;
    String courseName;
    String courseCredit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        getSupportActionBar().setTitle("List of Courses");
        editCourseId = findViewById(R.id.edit_courseId);
        editCourseCode = findViewById(R.id.edit_courseCode);
        editCourseName = findViewById(R.id.edit_courseName);
        editCourseCredit = findViewById(R.id.edit_courseCredit);

        buttonInsert = findViewById(R.id.button_insert);
        buttonDelete = findViewById(R.id.button_delete);
        buttonUpdate = findViewById(R.id.button_update);
        buttonSearch = findViewById(R.id.button_search);


        buttonInsert.setOnClickListener(this);
        buttonDelete.setOnClickListener(this);
        buttonUpdate.setOnClickListener(this);
        buttonSearch.setOnClickListener(this);

        db=new DbHelper(this);

        TextView textView = findViewById(R.id.view_data);

        DbHelper db = new DbHelper(this);

        String data = db.getCourseData();
        textView.setText(data);
    }
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.button_insert:
                courseCode=editCourseCode.getText().toString();
                courseName=editCourseName.getText().toString();
                courseCredit=editCourseCredit.getText().toString();
                if(courseCode.equals("") | courseName.equals("") | courseCredit.equals("")){
                    Toast.makeText(this, "Please fill the Fields", Toast.LENGTH_SHORT).show();
                }else {
                    db.insertCourse(courseCode,courseName,courseCredit);
                    editCourseId.setText("");
                    editCourseCode.setText("");
                    editCourseName.setText("");
                    editCourseCredit.setText("");
                    Toast.makeText(this, "saved successfully", Toast.LENGTH_SHORT).show();
                }recreate();
                break;

            case R.id.button_delete:
                CourseId = editCourseId.getText().toString();
                if(CourseId.equals("")){
                    Toast.makeText(this, "Please fill the Id", Toast.LENGTH_SHORT).show();
                }else {
                    long l = Long.parseLong(CourseId);
                    db.deleteCourse(l);
                    editCourseId.setText("");
                    editCourseCode.setText("");
                    editCourseName.setText("");
                    editCourseCredit.setText("");
                    Toast.makeText(this, "deleted successfully", Toast.LENGTH_SHORT).show();
                }recreate();
                break;

            case R.id.button_update:
                CourseId=editCourseId.getText().toString().trim();
                courseCode=editCourseCode.getText().toString().trim();
                courseName=editCourseName.getText().toString();
                courseCredit=editCourseCredit.getText().toString();
                if(CourseId.equals("") | courseCode.equals("") | courseName.equals("") | courseCredit.equals("")) {
                    Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                }else {
                    long l= Long.parseLong(CourseId);
                    db.updateCourse(l,courseCode,courseName,courseCredit);
                    editCourseId.setText("");
                    editCourseCode.setText("");
                    editCourseName.setText("");
                    editCourseCredit.setText("");
                    Toast.makeText(this, "updated successfully", Toast.LENGTH_SHORT).show();
                }recreate();
                break;
            case R.id.button_search:
                CourseId=editCourseId.getText().toString().trim();
                if(CourseId.equals("")){
                    Toast.makeText(this, "Please Fill the Id", Toast.LENGTH_SHORT).show();
                }else {
                    try {
                        long l1= Long.parseLong(CourseId);
                        courseCode=db.getCodeCourse(l1);
                        courseName=db.getCodeName(l1);
                        courseCredit=db.getCodeCredit(l1);

                        editCourseCode.setText(courseCode);
                        editCourseName.setText(courseName);
                        editCourseCredit.setText(courseCredit);
                        Toast.makeText(this, "searched successfully", Toast.LENGTH_SHORT).show();

                    }
                    catch (Exception e)
                    {
                        Toast.makeText(this, "Id is not Available", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + v.getId());
        }
    }
}
