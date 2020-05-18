package com.example.recyclerviewintablayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.reflect.TypeToken;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    private ArrayList<BloodSugar> listBloodSugar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SelectionActivity.lastActivity = MainActivity.class;
        setContentView(R.layout.activity_main);

        tabLayout = (TabLayout) findViewById(R.id.tabLayout_id);
        viewPager = (ViewPager) findViewById(R.id.viewpager_id);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        PrefSingleton.getInstance().Initialize(this);
        listBloodSugar = (ArrayList<BloodSugar>) PrefSingleton.getInstance().LoadPreferenceList("listBloodSugar",new TypeToken<ArrayList<BloodSugar>>() {}.getType());


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
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
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
                Intent intent = new Intent(v.getContext(), ReminderActivity.class);
                startActivity(intent);
            }
        });

    }

    // To test if an setOnClickListener is functional
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


}
