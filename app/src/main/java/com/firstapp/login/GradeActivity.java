package com.firstapp.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

    public class GradeActivity extends AppCompatActivity implements View.OnClickListener{


    DbHelper db;

    EditText editGradeId ,editGradeName ,editGradePoint;
    Button buttonInsert, buttonView, buttonDelete,buttonUpdate, buttonSearch, buttonBack;

    String gradeId;
    String gradeName;
    String gradePoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade);
        getSupportActionBar().setTitle("Grades");
        editGradeId = findViewById(R.id.grade_id);
        editGradeName = findViewById(R.id.grade_name);
        editGradePoint = findViewById(R.id.grade_point);

        buttonInsert = findViewById(R.id.button_insert);
        buttonDelete = findViewById(R.id.button_delete);
        buttonUpdate = findViewById(R.id.button_update);
        buttonSearch = findViewById(R.id.button_search);



        buttonInsert.setOnClickListener(this);
        buttonDelete.setOnClickListener(this);
        buttonUpdate.setOnClickListener(this);
        buttonSearch.setOnClickListener(this);

        db=new DbHelper(this);

        TextView textView = findViewById(R.id.view_grade);

        DbHelper db = new DbHelper(this);

        String data = db.getGradeData();
        textView.setText(data);

    }
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.button_insert:
                gradeName=editGradeName.getText().toString();
                gradePoint=editGradePoint.getText().toString();
                if(gradeName.equals("") | gradePoint.equals("")){
                    Toast.makeText(this, "Please fill the Fields", Toast.LENGTH_SHORT).show();
                }else {
                    db.insertGrade(gradeName,gradePoint);
                    editGradeId.setText("");
                    editGradeName.setText("");
                    editGradePoint.setText("");
                    Toast.makeText(this, "saved successfully", Toast.LENGTH_SHORT).show();
                }
                recreate();
                break;

            case R.id.button_delete:
                gradeId = editGradeId.getText().toString();
                if(gradeId.equals("")){
                    Toast.makeText(this, "Please fill the Id", Toast.LENGTH_SHORT).show();
                }else {
                    long l = Long.parseLong(gradeId);
                    db.deleteGrade(l);
                    editGradeId.setText("");
                    editGradeName.setText("");
                    editGradePoint.setText("");
                    Toast.makeText(this, "deleted successfully", Toast.LENGTH_SHORT).show();
                }recreate();
                break;

            case R.id.button_update:
                gradeId=editGradeId.getText().toString().trim();
                gradeName=editGradeName.getText().toString();
                gradePoint=editGradePoint.getText().toString();
                if(gradeId.equals("") | gradeName.equals("") | gradePoint.equals("")) {
                    Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                }else {
                    long l= Long.parseLong(gradeId);
                    db.updateGrade(l,gradeName,gradePoint);
                    editGradeId.setText("");
                    editGradeName.setText("");
                    editGradePoint.setText("");
                    Toast.makeText(this, "updated successfully", Toast.LENGTH_SHORT).show();
                }recreate();
                break;

            case R.id.button_search:
                gradeId=editGradeId.getText().toString().trim();
                if(gradeId.equals("")){
                    Toast.makeText(this, "Please Fill the Id", Toast.LENGTH_SHORT).show();
                }else {
                    try {
                        long l1= Long.parseLong(gradeId);
                        gradeName=db.getGrade(l1);
                        gradePoint=db.getPoint(l1);

                        editGradeName.setText(gradeName);
                        editGradePoint.setText(gradePoint);
                        Toast.makeText(this, "Searched Successfully", Toast.LENGTH_SHORT).show();

                    }
                    catch (Exception e)
                    {
                        Toast.makeText(this, "Id is not Available", Toast.LENGTH_SHORT).show();
                    }
                }
                break;

       }
    }
}
