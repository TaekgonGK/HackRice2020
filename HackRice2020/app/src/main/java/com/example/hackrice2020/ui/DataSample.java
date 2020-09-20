package com.example.hackrice2020.ui;

public class DataSample {
    private String date;
    private int total_cases;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTotalCases() {
        return total_cases;
    }

    public void setTotalCases(int totalCases) {
        this.total_cases = totalCases;
    }

    @Override
    public String toString() {
        return "DataSample{" +
                "date='" + date + '\'' +
                ", totalCases=" + total_cases +
                '}';
    }
}
