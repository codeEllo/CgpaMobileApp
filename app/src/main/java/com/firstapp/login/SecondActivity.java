package com.firstapp.login; //for view student list

import androidx.appcompat.app.AppCompatActivity;

        	import android.os.Bundle;
	import android.widget.TextView;

        	public class SecondActivity extends AppCompatActivity {


        	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
        	        super.onCreate(savedInstanceState);
        	        setContentView(R.layout.activity_second);

        	        TextView textView = findViewById(R.id.view_data);

        	        DbHelper db = new DbHelper(this);

        	        String data = db.getData();
        	        textView.setText(data);
					//getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        	    }
	}