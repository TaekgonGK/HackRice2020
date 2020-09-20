package com.example.hackrice2020.ui.history;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import com.jjoe64.graphview.GraphView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.hackrice2020.R;
import com.example.hackrice2020.ui.DataSample;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import com.example.hackrice2020.R;

public class HistoryFragment extends Fragment {

    private HistoryViewModel historyViewModel;
    LineGraphSeries<DataPoint> total_cases_series;
    LineGraphSeries<DataPoint> total_deaths_series;
    public List<DataSample> total_cases_sample = new ArrayList<>();
    public List<DataSample> total_deaths_sample = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        historyViewModel =
                ViewModelProviders.of(this).get(HistoryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_history, container, false);
        final TextView textView = root.findViewById(R.id.text_history);
        historyViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        readData(2, 3, total_cases_sample);
        readData(2, 6, total_deaths_sample);

        GraphView total_cases_graph = (GraphView) root.findViewById(R.id.graph1);
        GraphView total_deaths_graph = (GraphView) root.findViewById(R.id.graph2);
        total_cases_series = new LineGraphSeries<DataPoint>();
        total_deaths_series = new LineGraphSeries<DataPoint>();
        total_cases_graph.getGridLabelRenderer().setVerticalAxisTitle("Population (Billion)");
        total_cases_graph.getGridLabelRenderer().setHorizontalAxisTitle("Month");
        total_deaths_graph.getGridLabelRenderer().setVerticalAxisTitle("Population (10M)");
        total_deaths_graph.getGridLabelRenderer().setHorizontalAxisTitle("Month");

        for (int i = 0 ; i < total_cases_sample.size(); i++) {
            String date = total_cases_sample.get(i).getDate();
            double doubleDate = changeDate(date);
            double total_cases = total_cases_sample.get(i).getTotalCases() / 100000000.0;
            total_cases_series.appendData(new DataPoint(doubleDate, total_cases),
                    true, total_cases_sample.size());

            double total_deaths = total_deaths_sample.get(i).getTotalCases() / 1000000.0;
            total_deaths_series.appendData(new DataPoint(doubleDate, total_deaths),
                    true, total_deaths_sample.size());


        }
        total_cases_graph.addSeries(total_cases_series);
        total_cases_graph.getViewport().setMinX(0.9);
        total_cases_graph.getViewport().setMaxX(9.5);
        total_cases_graph.getViewport().setMinY(0);
        total_cases_graph.getViewport().setMaxY(0.4);
        total_cases_graph.getViewport().setYAxisBoundsManual(true);
        total_cases_graph.getViewport().setXAxisBoundsManual(true);

        total_deaths_graph.addSeries(total_deaths_series);
        total_deaths_graph.getViewport().setMinX(0.9);
        total_deaths_graph.getViewport().setMaxX(9.5);
        total_deaths_graph.getViewport().setMinY(0);
        total_deaths_graph.getViewport().setMaxY(1.0);
        total_deaths_graph.getViewport().setYAxisBoundsManual(true);
        total_deaths_graph.getViewport().setXAxisBoundsManual(true);

        return root;
    }
    private static double changeDate (String date) {
        int month = Integer.parseInt(date.substring(5,7));
        int day = Integer.parseInt(date.substring(8,10));

        double datenum;
        if (date.substring(0,4).equals("2019")) {
            return 1.0;
        }


        if (month == 2)
        {
            datenum = month + day/29.0;
        } else if (month == 4 || month == 6 || month == 9 || month == 11) {
            datenum = month + day/30.0;
        } else {
            datenum = month + day/31.0;
        }

        return datenum;
    }
    /**
     * @source https://www.youtube.com/watch?v=i-TqNzUryn8
     */
    private void readData(int date, int data, List  list) {
        InputStream is = getResources().openRawResource(R.raw.data2);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8"))
        );

        String line = "";
        try {
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                Log.d("My Activity", "Line: " + line);
                //split by commas
                String[] tokens = line.split(",");

                //read by data
                DataSample temp = new DataSample();
                temp.setDate(tokens[date]);
                temp.setTotalCases(Integer.parseInt(tokens[data]));
                list.add(temp);

            }
        } catch (IOException e) {
            Log.wtf("My Activity", "Error reading data on line " + line, e);
            e.printStackTrace();

        }

    }
}