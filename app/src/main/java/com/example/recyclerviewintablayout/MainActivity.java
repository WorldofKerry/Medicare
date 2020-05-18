package com.example.recyclerviewintablayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.CalendarView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.PointsGraphSeries;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    private ArrayList<BloodSugar> listBloodSugar;
    private ArrayList<Excercise> listExcercise;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = (TabLayout) findViewById(R.id.tabLayout_id);
        viewPager = (ViewPager) findViewById(R.id.viewpager_id);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        PrefSingleton.getInstance().Initialize(this);
        listBloodSugar = (ArrayList<BloodSugar>) PrefSingleton.getInstance().LoadPreferenceList("listBloodSugar",new TypeToken<ArrayList<BloodSugar>>() {}.getType());
        listExcercise = (ArrayList<Excercise>) PrefSingleton.getInstance().LoadPreferenceList("listExcercise",new TypeToken<ArrayList<Excercise>>() {}.getType());


        // Add Fragments
        // Can initialize without text in title with
        //adapter.AddFragment(new FragmentSymptom(), "");
        adapter.AddFragment(new FragmentSymptom(), "Symptoms");
        adapter.AddFragment(new FragmentBloodSugar(), "Blood Sugar");
        adapter.AddFragment(new FragmentExercise(), "Exercise");

        // Update views
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        // Remove Shadow from action bar
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setElevation(0);
        actionBar.setTitle(R.string.track);

        final GraphView graph = findViewById(R.id.mainGraph_id);
        graph.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        graph.getGridLabelRenderer().setHorizontalLabelsVisible(false);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        graph.removeAllSeries();
                        graph.setTitle("");
                        //graph.setVisibility(View.GONE);
                        break;
                    case 1:
                        LineGraphSeries<DataPoint> lineSeries = new LineGraphSeries<>();
                        graph.removeAllSeries();
                        graph.getViewport().setMaxX(7.5);
                        graph.getViewport().setXAxisBoundsManual(true);
                        graph.setTitle("Blood Sugar Graph (Past 7 Days)");
                        graph.getGridLabelRenderer().setVerticalAxisTitle("Blood Sugar (mg/dl)");
                        if (listBloodSugar != null && !listBloodSugar.isEmpty()) {
                            ArrayList<BloodSugar> tempRelevantPoints = new ArrayList<BloodSugar>();
                            int tempMinuteValue;
                            String[] tempTimeIntervals;
                            Calendar tempCal = Calendar.getInstance();
                            //int test1, test2, test3, test4, test5, test6;
                            for (BloodSugar bloodSugar: listBloodSugar) {
                                tempTimeIntervals = bloodSugar.getTime().split("[-+:]");
                                //test4 = tempCal.get(Calendar.MONTH);
                                //test5 = tempCal.get(Calendar.DAY_OF_MONTH);
                                //test6 = tempCal.get(Calendar.HOUR_OF_DAY);
                                //test1 = (tempCal.get(Calendar.YEAR) - Integer.parseInt(tempTimeIntervals[0])) * 8760;
                                //test2 = ((tempCal.get(Calendar.MONTH) - Integer.parseInt(tempTimeIntervals[1])) * 730);
                                //test3 = (tempCal.get(Calendar.DAY_OF_MONTH) - Integer.parseInt(tempTimeIntervals[2])) * 24;
                                tempMinuteValue = (tempCal.get(Calendar.YEAR) - Integer.parseInt(tempTimeIntervals[0])) * 525600 + (tempCal.get(Calendar.MONTH) + 1 - Integer.parseInt(tempTimeIntervals[1])) * 43800 + (tempCal.get(Calendar.DAY_OF_MONTH) - Integer.parseInt(tempTimeIntervals[2])) * 1440 + (tempCal.get(Calendar.HOUR_OF_DAY) - Integer.parseInt(tempTimeIntervals[3])) * 60 + (tempCal.get(Calendar.MINUTE) - Integer.parseInt(tempTimeIntervals[4]));
                                if (tempMinuteValue < 10080) {
                                    tempRelevantPoints.add(bloodSugar);
                                }
                            }
                            //showToast(listBloodSugar.get(0).getTime());
                            DataPoint[] dataPoints = new DataPoint[tempRelevantPoints.size()];
                            for (int i = 0; i < tempRelevantPoints.size(); i++) {
                                tempTimeIntervals = tempRelevantPoints.get(i).getTime().split("[-+:]");
                                tempMinuteValue = (tempCal.get(Calendar.YEAR) - Integer.parseInt(tempTimeIntervals[0])) * 525600 + (tempCal.get(Calendar.MONTH) + 1 - Integer.parseInt(tempTimeIntervals[1])) * 43800 + (tempCal.get(Calendar.DAY_OF_MONTH) - Integer.parseInt(tempTimeIntervals[2])) * 1440 + (tempCal.get(Calendar.HOUR_OF_DAY) - Integer.parseInt(tempTimeIntervals[3])) * 60 + (tempCal.get(Calendar.MINUTE) - Integer.parseInt(tempTimeIntervals[4]));
                                dataPoints[i] = new DataPoint(7 - (double) tempMinuteValue/1440,Double.parseDouble(tempRelevantPoints.get(i).getLevel()));
                            }
                            lineSeries.resetData(dataPoints);
                            graph.addSeries(lineSeries);
                        }
                        //showToast("0");
                        //dataPoints.
                        break;
                    case 2:
                        PointsGraphSeries<DataPoint> pointSeries = new PointsGraphSeries<>();
                        graph.removeAllSeries();
                        graph.getViewport().setMaxX(7.5);
                        graph.getViewport().setXAxisBoundsManual(true);
                        graph.setTitle("Excercise Graph (Past 7 Days)");
                        graph.getGridLabelRenderer().setVerticalAxisTitle("Calories Burned");
                        if (listExcercise != null && !listExcercise.isEmpty()) {
                            ArrayList<Excercise> tempRelevantPoints = new ArrayList<Excercise>();
                            int tempMinuteValue;
                            String[] tempTimeIntervals;
                            Calendar tempCal = Calendar.getInstance();
                            //int test1, test2, test3, test4, test5, test6;
                            for (Excercise excercise: listExcercise) {
                                tempTimeIntervals = excercise.getTime().split("[-+:]");
                                //test4 = tempCal.get(Calendar.MONTH);
                                //test5 = tempCal.get(Calendar.DAY_OF_MONTH);
                                //test6 = tempCal.get(Calendar.HOUR_OF_DAY);
                                //test1 = (tempCal.get(Calendar.YEAR) - Integer.parseInt(tempTimeIntervals[0])) * 8760;
                                //test2 = ((tempCal.get(Calendar.MONTH) - Integer.parseInt(tempTimeIntervals[1])) * 730);
                                //test3 = (tempCal.get(Calendar.DAY_OF_MONTH) - Integer.parseInt(tempTimeIntervals[2])) * 24;
                                tempMinuteValue = (tempCal.get(Calendar.YEAR) - Integer.parseInt(tempTimeIntervals[0])) * 525600 + (tempCal.get(Calendar.MONTH) + 1 - Integer.parseInt(tempTimeIntervals[1])) * 43800 + (tempCal.get(Calendar.DAY_OF_MONTH) - Integer.parseInt(tempTimeIntervals[2])) * 1440 + (tempCal.get(Calendar.HOUR_OF_DAY) - Integer.parseInt(tempTimeIntervals[3])) * 60 + (tempCal.get(Calendar.MINUTE) - Integer.parseInt(tempTimeIntervals[4]));
                                if (tempMinuteValue < 10080) {
                                    tempRelevantPoints.add(excercise);
                                }
                            }
                            //showToast(listBloodSugar.get(0).getTime());
                            DataPoint[] dataPoints = new DataPoint[tempRelevantPoints.size()];
                            for (int i = 0; i < tempRelevantPoints.size(); i++) {
                                tempTimeIntervals = tempRelevantPoints.get(i).getTime().split("[-+:]");
                                tempMinuteValue = (tempCal.get(Calendar.YEAR) - Integer.parseInt(tempTimeIntervals[0])) * 525600 + (tempCal.get(Calendar.MONTH) + 1 - Integer.parseInt(tempTimeIntervals[1])) * 43800 + (tempCal.get(Calendar.DAY_OF_MONTH) - Integer.parseInt(tempTimeIntervals[2])) * 1440 + (tempCal.get(Calendar.HOUR_OF_DAY) - Integer.parseInt(tempTimeIntervals[3])) * 60 + (tempCal.get(Calendar.MINUTE) - Integer.parseInt(tempTimeIntervals[4]));
                                dataPoints[i] = new DataPoint(7 - (double) tempMinuteValue/1440,Double.parseDouble(tempRelevantPoints.get(i).getCalories()));
                            }
                            pointSeries.resetData(dataPoints);
                            graph.addSeries(pointSeries);
                            pointSeries.setSize((float) 16);
                        }
                        //showToast("0");
                        //dataPoints.
                        break;
                    default:
                        showToast("What just happened? Perhaps the archives are incomplete...");
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        // Add Icons (Array) (not necessary)
        //tabLayout.getTabAt(0).setIcon(R.drawable.ic_symptom);
        //tabLayout.getTabAt(1).setIcon(R.drawable.ic_symptom);
        //tabLayout.getTabAt(2).setIcon(R.drawable.ic_symptom);

        //add the graph
        //GraphView graph = findViewById(R.id.mainGraph_id);
       // graph.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        //LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
        //if (listBloodSugar != null) {
        //    for (BloodSugar bloodSugar : listBloodSugar) {
        //        showToast(bloodSugar.getLevel());
        //    }
        //}
        //graph.addSeries(series);
        //graph.setTitle("Symptoms Graph (Past 7 Days)");

        // Floating Action Button stuff
        FloatingActionButton floatingActionButton = findViewById(R.id.fab_action1);
        floatingActionButton.setOnClickListener(new View.OnClickListener()      {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SelectionActivity.class);
                startActivity(intent);
            }
        });

        FloatingActionButton floatingActionButton2 = findViewById(R.id.fab_action2);
        floatingActionButton2.setOnClickListener(new View.OnClickListener()      {
            @Override
            public void onClick(View v) {
                // Testing
                Intent intent = new Intent(MainActivity.this, CalendarActivity.class);
                startActivity(intent);
            }
        });

        FloatingActionButton floatingActionButton3 = findViewById(R.id.fab_action3);
        floatingActionButton3.setOnClickListener(new View.OnClickListener()      {
            @Override
            public void onClick(View v) {
                // Testing
                Intent intent = new Intent(v.getContext(), MainReminderActivity.class);
                startActivity(intent);
            }
        });

    }

    // To test if an setOnClickListener is functional
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


}
