package com.example.hackrice2020.ui.home;

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

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    LineGraphSeries<DataPoint> series;
    public List<DataSample> sample = new ArrayList<>();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        readData();

        GraphView graph = (GraphView) root.findViewById(R.id.graph);
        series = new LineGraphSeries<DataPoint>();
        graph.getGridLabelRenderer().setVerticalAxisTitle("Population (Billion)");
        graph.getGridLabelRenderer().setHorizontalAxisTitle("Month");

        /**
         *
         */
        for (int i = 0 ; i < sample.size(); i++) {
            String date = sample.get(i).getDate();
            double doubleDate = changeDate(date);

            double total_cases = sample.get(i).getTotalCases() / 100000000.0;
            series.appendData(new DataPoint(doubleDate, total_cases), true, sample.size());
        }
        graph.addSeries(series);

        graph.getViewport().setMinX(0.9);
        graph.getViewport().setMaxX(9.5);
        graph.getViewport().setMinY(0);
        graph.getViewport().setMaxY(0.4);

        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setXAxisBoundsManual(true);

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
    private void readData() {
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
                temp.setDate(tokens[2]);
                temp.setTotalCases(Integer.parseInt(tokens[3]));
                sample.add(temp);

                Log.d("My Activity", "Just Created: " + sample);
            }
        } catch (IOException e) {
            Log.wtf("My Activity", "Error reading data on line " + line, e);
            e.printStackTrace();

        }

    }
}