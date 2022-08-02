package com.firstapp.login;

import android.content.Intent;
import android.net.wifi.hotspot2.pps.HomeSp;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;


public class HomepageActivity extends AppCompatActivity implements
NavigationView.OnNavigationItemSelectedListener{

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Button btnCalculate,btnreport;
    List<Pojo2> list2;

    MyAdapter2 myAdapter2;

    @Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        btnCalculate = (Button) findViewById(R.id.btnCalculate);
        btnreport = (Button) findViewById(R.id.btnreport);
        toolbar = findViewById(R.id.toolbar);

        drawerLayout = findViewById(R.id.drawer);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout,toolbar,
                R.string.open, R.string.close);

        //drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;

                //STUDENT ACTIVITY
                intent = new Intent(HomepageActivity.this, RegisteredCourseView.class);
                startActivity(intent);
            }
        });

        btnreport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;

                //STUDENT ACTIVITY
                intent = new Intent(HomepageActivity.this, barActivity.class);
                startActivity(intent);
            }
        });

        TextView textView = findViewById(R.id.title);
        DbHelper db = new DbHelper(this);

        String data = db.getStudent();
        textView.setText(data);
    }

    private void listShow() {
        list2 = new ArrayList<>();
        list2.add(new Pojo2(R.drawable.ic_launcher_background, "Wifi","This is wifi icon"));
        list2.add(new Pojo2(R.drawable.ic_launcher_background, "Bluetooth","This is wifi icon"));
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.navi_home:
                Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, HomepageActivity.class));
            break;
            case R.id.navi_studentDetail:
                Toast.makeText(this, "Student Detail", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.navi_registeredCourse:
                Toast.makeText(this, "Registered course", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, RegisteredCourse.class));
                break;
            case R.id.navi_courseDetail:
                Toast.makeText(this, "courseDetail", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, CourseActivity.class));
                break;
            case R.id.navi_gradeDetail:
                Toast.makeText(this, "grade detail", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, GradeActivity.class));
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}