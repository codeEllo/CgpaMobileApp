package com.firstapp.login;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.View;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisteredCourse extends AppCompatActivity implements View.OnClickListener{

    DbHelper db;

    EditText EditRegisteredCourseID ,EditstudentID ,Editsem ,EditcodeID,EditgradeID;
    Button buttonInsert, buttonView, buttonDelete,buttonUpdate, buttonSearch, buttonAddGrade;

    String rcID, studentID, sem, codeID, gradeID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeredcourse);
        getSupportActionBar().setTitle("Registered Courses & Results");
        EditRegisteredCourseID = findViewById(R.id.RegisteredCourseID);
        EditstudentID = findViewById(R.id.studentID);
        Editsem = findViewById(R.id.sem);
        EditcodeID = findViewById(R.id.codeID);
        EditgradeID = findViewById(R.id.gradeID);
        registerForContextMenu(EditgradeID);

        buttonInsert = findViewById(R.id.button_insert);
        buttonDelete = findViewById(R.id.button_delete);
        buttonUpdate = findViewById(R.id.button_update);
        buttonSearch = findViewById(R.id.button_search);
        //buttonAddGrade = findViewById(R.id.button_addGrade);


        EditcodeID.setOnClickListener(this);
        buttonInsert.setOnClickListener(this);
        buttonDelete.setOnClickListener(this);
        buttonUpdate.setOnClickListener(this);
        buttonSearch.setOnClickListener(this);
        //buttonAddGrade.setOnClickListener(this);

        db=new DbHelper(this);
        TextView textView = findViewById(R.id.view_data);

        DbHelper db = new DbHelper(this);

        String data = db.getRegisteredCourse();
        textView.setText(data);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Grade List");
        getMenuInflater().inflate(R.menu.grademenu,menu);
    }

    public void refresh(View view){          //refresh is onClick name given to the button
        onRestart();
    }
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        super.onRestart();
        switch (v.getId()){

            case R.id.button_insert:
                studentID=EditstudentID.getText().toString();
                sem=Editsem.getText().toString();
                codeID=EditcodeID.getText().toString();
                gradeID=EditgradeID.getText().toString();
                if(studentID.equals("") | sem.equals("") | codeID.equals("")| gradeID.equals("")){
                    Toast.makeText(this, "Please fill the Fields", Toast.LENGTH_SHORT).show();
                }else {
                    db.insertRegisteredCourse(studentID,sem,codeID,gradeID);
                    EditRegisteredCourseID.setText("");
                    EditstudentID.setText("");
                    Editsem.setText("");
                    EditcodeID.setText("");
                    EditgradeID.setText("");
                    Toast.makeText(this, "saved successfully", Toast.LENGTH_SHORT).show();
                }recreate();
                break;
            case R.id.button_delete:
                rcID = EditRegisteredCourseID.getText().toString();
                if(rcID.equals("")){
                    Toast.makeText(this, "Please fill the Id", Toast.LENGTH_SHORT).show();
                }else {
                    long l = Long.parseLong(rcID);
                    db.deleteCourse(l);
                    EditRegisteredCourseID.setText("");
                    EditstudentID.setText("");
                    Editsem.setText("");
                    EditcodeID.setText("");
                    EditgradeID.setText("");
                    Toast.makeText(this, "deleted successfully", Toast.LENGTH_SHORT).show();
                }recreate();
                break;

            case R.id.button_update:
                rcID=EditRegisteredCourseID.getText().toString().trim();
                studentID=EditstudentID.getText().toString().trim();
                sem=Editsem.getText().toString();
                codeID=EditcodeID.getText().toString();
                gradeID=EditgradeID.getText().toString();
                if(rcID.equals("") | studentID.equals("") | sem.equals("") | codeID.equals("")| gradeID.equals("")) {
                    Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                }else {
                    long l= Long.parseLong(rcID);
                    db.updateRCourse(l,studentID,sem,codeID,gradeID);
                    EditRegisteredCourseID.setText("");
                    EditstudentID.setText("");
                    Editsem.setText("");
                    EditcodeID.setText("");
                    EditgradeID.setText("");
                    Toast.makeText(this, "updated successfully", Toast.LENGTH_SHORT).show();
                }recreate();
                break;
            case R.id.button_search:
                rcID=EditRegisteredCourseID.getText().toString().trim();
                if(rcID.equals("")){
                    Toast.makeText(this, "Please Fill the Id", Toast.LENGTH_SHORT).show();
                }else {
                    try {
                        long l1= Long.parseLong(rcID);
                        studentID=db.getRcStudentID(l1);
                        sem=db.getRcSem(l1);
                        codeID=db.getRcCodeID(l1);
                        gradeID=db.getRcGradeID(l1);

                        EditstudentID.setText(studentID);
                        Editsem.setText(sem);
                        EditcodeID.setText(codeID);
                        EditgradeID.setText(gradeID);
                        Toast.makeText(this, "searched successfully", Toast.LENGTH_SHORT).show();

                    }
                    catch (Exception e)
                    {
                        Toast.makeText(this, "Id is not Available", Toast.LENGTH_SHORT).show();
                    }
                }
                break;

        }
        //new MyDialogFragment().show(getSupportFragmentManager(),"MyDialogFragment");
    }
}
