package com.example.recyclerviewintablayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.drm.DrmStore;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = (TabLayout) findViewById(R.id.tabLayout_id);
        viewPager = (ViewPager) findViewById(R.id.viewpager_id);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        // Add Fragments
        // Can initialize without text in title with
        //adapter.AddFragment(new FragmentSymptom(), "");
        adapter.AddFragment(new FragmentSymptom(), "Symptoms");
        adapter.AddFragment(new FragmentBloodSugar(), "Blood Sugar");
        adapter.AddFragment(new FragmentExercise(), "Exercise");

        // Update views
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        // Add Icons (Array)
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_symptom);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_symptom);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_symptom);

        // Remove Shadow from action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setElevation(0);

        //add the graph
        GraphView graph = findViewById(R.id.mainGraph_id);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });
        graph.addSeries(series);
    }

}
