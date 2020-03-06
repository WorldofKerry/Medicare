package com.example.recyclerviewintablayout;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.drm.DrmStore;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

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

    }
}
