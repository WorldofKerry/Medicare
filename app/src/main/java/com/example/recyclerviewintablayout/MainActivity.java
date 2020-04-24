package com.example.recyclerviewintablayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.Bundle;
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

        // Add Icons (Array) (not necessary)
        //tabLayout.getTabAt(0).setIcon(R.drawable.ic_symptom);
        //tabLayout.getTabAt(1).setIcon(R.drawable.ic_symptom);
        //tabLayout.getTabAt(2).setIcon(R.drawable.ic_symptom);

        // Remove Shadow from action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setElevation(0);
        actionBar.setTitle(R.string.track);


        //add the graph
        GraphView graph = findViewById(R.id.mainGraph_id);
        graph.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });
        graph.addSeries(series);

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
                showToast("fan 3");
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
