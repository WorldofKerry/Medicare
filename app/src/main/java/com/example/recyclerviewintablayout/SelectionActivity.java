package com.example.recyclerviewintablayout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class SelectionActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);

        getSupportActionBar().setTitle("I want to...");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button button = findViewById(R.id.buttonTrackASymptom);
        button.setOnClickListener(new View.OnClickListener()      {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SymptomsActivity.class);
                startActivity(intent);
            }
        });

        Button button2 = findViewById(R.id.buttonTrackBloodSugar);
        button2.setOnClickListener(new View.OnClickListener()      {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AddBloodSugarActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



}
