package com.example.hackrice2020.ui.trends;

import android.util.Log;

public class CovidSample {
    private int newCases;
    private String date;
    private String month;
    private double datenum;
    private String day;
    private double temp;

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public int getTests() {
        return tests;
    }

    public void setTests(int tests) {
        this.tests = tests;
    }

    private int pos;
    private int tests;


    public int getMonth ()
    {
        return Integer.parseInt(month);
    }


    public double getDatenum() {
        return datenum;
    }


    public int getNewCases() {
        return newCases;
    }

    public void setNewCases(int newCases) {
        this.newCases = newCases;
    }



    public void setDate(String date) {
        this.date = date;
        month = date.substring(4,6);
        day = date.substring(6,8);
        temp = Double.parseDouble(day);
        if (month.equals("02"))
            temp = (temp / 29) * 99;
        else if (month.equals("01") || month.equals("03") || month.equals("05") || month.equals("07")|| month.equals("08") || month.equals("10") || month.equals("12"))
            temp = (temp/31) * 99;
        else
            temp = (temp/30) *99;
        temp = Math.round(temp * 100.0) / 100.0;
        day = Double.toString(temp);
        for (int i = 0; i < day.length(); i++)
        {
            if (day.substring(i,i+1).equals("."))
            {
                day = day.substring(0,i)+day.substring(i+1);
                if (i == 1)
                    day = "0" + day;
            }
        }
        datenum = Double.parseDouble(month+"." + day);
    }

    public void setDate2(String date) {
        this.date = date;
        month = date.substring(5,7);
        day = date.substring(8,10);
        temp = Double.parseDouble(day);
        if (month.equals("02"))
            temp = (temp / 29) * 99;
        else if (month.equals("01") || month.equals("03") || month.equals("05") || month.equals("07")|| month.equals("08") || month.equals("10") || month.equals("12"))
            temp = (temp/31) * 99;
        else
            temp = (temp/30) *99;
        temp = Math.round(temp * 100.0) / 100.0;
        day = Double.toString(temp);
        for (int i = 0; i < day.length(); i++)
        {
            if (day.substring(i,i+1).equals("."))
            {
                day = day.substring(0,i)+day.substring(i+1);
                if (i == 1)
                    day = "0" + day;
            }
        }
        Log.i("Error", "Day is: " + day);
        datenum = Double.parseDouble(month+"." + day);
    }
}
