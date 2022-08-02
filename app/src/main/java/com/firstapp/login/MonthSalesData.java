package com.firstapp.login;

public class MonthSalesData {

    String Month;
    double sales;

    public MonthSalesData(String month, double sales) {
        Month = month;
        this.sales = sales;
    }

    public String getMonth() {
        return Month;
    }

    public void setMonth(String month) {
        Month = month;
    }

    public double getSales() {
        return sales;
    }

    public void setSales(double sales) {
        this.sales = sales;
    }
}
