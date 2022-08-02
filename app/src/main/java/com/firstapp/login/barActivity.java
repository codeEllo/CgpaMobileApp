package com.firstapp.login;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;


import java.text.DecimalFormat;
import java.util.ArrayList;

public class barActivity extends AppCompatActivity {

    BarChart barChart;
    ArrayList<BarEntry>  barEntryArrayList;
    ArrayList<String> labelsNames;
    //private database db;

    ArrayList<MonthSalesData> monthSalesDataArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState){
    super.onCreate(savedInstanceState);
        setContentView(R.layout.barchart);
        getSupportActionBar().setTitle("Result Reporting Chart");
        //Assign variable
        barChart = findViewById(R.id.bar_chart);
        barEntryArrayList = new ArrayList<>();
        labelsNames = new ArrayList<>();
        //fillMonthSales();
        addDataToGraph();
        //ArrayList<BarEntry> barEntries = new ArrayList<>();
        /*for(int i=1;i<monthSalesDataArrayList.size();i++){

            String month = monthSalesDataArrayList.get(i).getMonth();
            double sales = monthSalesDataArrayList.get(i).getSales();
            barEntryArrayList.add(new BarEntry(i, (float) sales));
            labelsNames.add(month);
        }*/
        DecimalFormat df = new DecimalFormat("#.##");
        DbHelper db = new DbHelper(this);
        for(int i=1;i<db.GPASEM1().size();i++){
            BarEntry newBarEntry = new BarEntry(i, Float.parseFloat(db.GPASEM1().get(i)));
            barEntryArrayList.add(newBarEntry);
        }
       BarDataSet barDataSet = new BarDataSet(barEntryArrayList, "SEM1, SEM2, SEM3, SEM4, SEM5");
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        //barDataSet.setDrawValue(false);
        Description description = new Description();
        description.setEnabled(true);
        description.setText("GPA All semesters");
        barChart.setDescription(description);

        BarData barData = new BarData(barDataSet);

        barChart.setData(barData);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labelsNames));
        xAxis.setPosition(XAxis.XAxisPosition.TOP);
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(labelsNames.size());
        xAxis.setLabelRotationAngle(270);
        barChart.animateY(2300);
        barChart.invalidate();

    }

   /* private void fillMonthSales(){
        monthSalesDataArrayList.clear();
       monthSalesDataArrayList.add(new MonthSalesData("SEMESTER 1", 4.2));
       monthSalesDataArrayList.add(new MonthSalesData("SEMESTER 1", 4.2));
        monthSalesDataArrayList.add(new MonthSalesData("SEMESTER 2", 3));
        monthSalesDataArrayList.add(new MonthSalesData("SEMESTER 3", 1));
        monthSalesDataArrayList.add(new MonthSalesData("SEMESTER 4", 4));
        monthSalesDataArrayList.add(new MonthSalesData("SEMESTER 5", 4));

    }*/

    private void addDataToGraph(){
        DbHelper db = new DbHelper(this);
        DecimalFormat df = new DecimalFormat("#.##");
        final ArrayList<String> xVals = new ArrayList<String>();
        final ArrayList<String> xdata = db.GPASEM1();

        for(int i=0; i<db.GPASEM1().size();i++){
            xVals.add(xdata.get(i));
        }
    }
}
