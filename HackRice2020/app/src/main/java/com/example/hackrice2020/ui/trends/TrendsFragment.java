package com.example.hackrice2020.ui.trends;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.hackrice2020.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import com.github.mikephil.charting.components.YAxis;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class TrendsFragment extends Fragment {

    private TrendsViewModel trendsViewModel;
    private List<CovidSample> covidSamples= new ArrayList<>();
    LineChart lineChart;

    private List <CovidSample> covidSamples2 = new ArrayList<>();
    LineGraphSeries<DataPoint> series;
    LineChart lineChart2;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        trendsViewModel =
                ViewModelProviders.of(this).get(TrendsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_trends, container, false);

        // FIRST GRAPH

        lineChart = (LineChart) root.findViewById(R.id.lineChart);

        readCovidData();
        readCovidData2();

        double xC, yT, yP;
        ArrayList<Entry> yAXESTests = new ArrayList<>();
        ArrayList<Entry> yAXESPos = new ArrayList<>();

        for (int i = 0; i < covidSamples.size() - 1; i++) {
            xC = covidSamples.get(i).getDatenum();
            yT = covidSamples.get(i).getTests();
            yP = covidSamples.get(i).getPos();
            float xCmod = Float.parseFloat(String.valueOf(xC));
            float yTmod = Float.parseFloat(String.valueOf(yT))/1000;
            float yPmod = Float.parseFloat(String.valueOf(yP))/1000;
            yAXESTests.add(new Entry(xCmod, yTmod));
            yAXESPos.add(new Entry(xCmod, yPmod));
        }
        ArrayList<ILineDataSet> lineDataSets = new ArrayList<>();

        LineDataSet lineDataSet1 = new LineDataSet(yAXESTests, "Daily Testing");
        lineDataSet1.setDrawCircles(false);
        lineDataSet1.setColor(Color.BLACK);

        LineDataSet lineDataSet2 = new LineDataSet(yAXESPos, "Positive Results");
        lineDataSet2.setDrawCircles(false);
        lineDataSet2.setColor(Color.RED);

        lineDataSets.add(lineDataSet1);
        lineDataSets.add(lineDataSet2);

        lineChart.setData(new LineData(lineDataSets));
        lineChart.getDescription().setText("2020 (Month) x People (Thousands)");

        // SECOND GRAPH

        lineChart2 = (LineChart) root.findViewById(R.id.lineChart2);

        ArrayList<ILineDataSet> lineDataSets2 = new ArrayList<>();

        double xC2,yD;
        ArrayList <Entry> yAXESCases = new ArrayList<>();
        ArrayList <Entry> yAXESUn = new ArrayList<>();
        ArrayList<Integer> unemployment = new ArrayList<>();
        unemployment.add(7140);
        unemployment.add(23078);
        unemployment.add(20985);
        unemployment.add(17750);
        unemployment.add(16338);
        unemployment.add(13550);

        for (int i = 0;i < covidSamples2.size(); i++)
        {
            xC2 = covidSamples2.get(i).getDatenum();
            yD = covidSamples2.get(i).getNewCases();
            float xCmod = Float.parseFloat(String.valueOf(xC2));
            float yCmod = Float.parseFloat(String.valueOf(yD))/1000;
            float yUmod = Float.parseFloat(String.valueOf(unemployment.get(covidSamples2.get(i).getMonth()-3)))/1000;
            yAXESCases.add(new Entry(xCmod,yCmod));
            yAXESUn.add(new Entry(xCmod,yUmod));
        }

        LineDataSet lineDataSet1v = new LineDataSet(yAXESCases, "Cases");
        lineDataSet1v.setDrawCircles(false);
        lineDataSet1v.setColor(Color.BLACK);

        LineDataSet lineDataSet2v = new LineDataSet(yAXESUn, "Unemployment");
        lineDataSet2v.setDrawCircles(false);
        lineDataSet2v.setColor(Color.RED);

        lineDataSets2.add(lineDataSet1v);
        lineDataSets2.add(lineDataSet2v);

        lineChart2.setData(new LineData(lineDataSets2));
        lineChart2.getDescription().setText("2020 (Month) x People (Thousands)");

        return root;
    }

    public void readCovidData() {
        InputStream is = getResources().openRawResource(R.raw.covid3);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8"))
        );
        String line = "";
        try {
            reader.readLine();
            while (( line = reader.readLine()) != null) {
                String[] tokens = line.split(",");
                CovidSample sample = new CovidSample();
                sample.setDate(tokens[0]);
                if (tokens[2].length() > 0) {
                    sample.setTests(Integer.parseInt(tokens[2]));
                }
                else {
                    sample.setNewCases(0);
                }
                if (tokens[1].length() > 0) {
                    sample.setPos(Integer.parseInt(tokens[1]));
                }
                else {
                    sample.setNewCases(0);
                }
                covidSamples.add(sample);
            }

        } catch (IOException e) {
            Log.wtf("MyActivity", "Error Reading Datafile on line " + line, e);
            e.printStackTrace();
        }
    }

    public void readCovidData2() {
        InputStream is = getResources().openRawResource(R.raw.covid2);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8"))
        );
        String line = "";
        try {
            reader.readLine();
            reader.readLine();
            while (( line = reader.readLine()) != null) {
                String[] tokens = line.split(",");
                CovidSample sample = new CovidSample();
                sample.setDate2(tokens[3]);
                if (tokens[5].length() > 0) {
                    sample.setNewCases(Integer.parseInt(tokens[5]));
                }
                else {
                    sample.setNewCases(0);
                }
                covidSamples2.add(sample);
            }

        } catch (IOException e) {
            Log.wtf("MyActivity", "Error Reading Datafile on line " + line, e);
            e.printStackTrace();
        }
    }
}