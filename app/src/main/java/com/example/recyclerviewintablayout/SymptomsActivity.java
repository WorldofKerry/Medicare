package com.example.recyclerviewintablayout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class SymptomsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symptoms);

        getSupportActionBar().setTitle("I have...");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button buttonHeadache = findViewById(R.id.buttonAHeadache);
        buttonHeadache.setOnClickListener(new View.OnClickListener()      {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AddSymptomActivity.class);
                Symptom symptom = new Symptom("Headache", null, null, null, null, null);
                intent.putExtra("Symptom", (Parcelable) symptom);
                intent.putExtra("Type", "Add");
                startActivity(intent);
            }
        });

        Button buttonBlurredVision = findViewById(R.id.buttonBlurredVision);
        buttonBlurredVision.setOnClickListener(new View.OnClickListener()      {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AddSymptomActivity.class);
                Symptom symptom = new Symptom("Blurred Vision", null, null, null, null, null);
                intent.putExtra("Symptom", (Parcelable) symptom);
                intent.putExtra("Type", "Add");
                startActivity(intent);
            }
        });

        Button buttonFeelingsOfExhaustionAndWeakness = findViewById(R.id.buttonFeelingsOfExhaustionAndWeakness);
        buttonFeelingsOfExhaustionAndWeakness.setOnClickListener(new View.OnClickListener()      {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AddSymptomActivity.class);
                Symptom symptom = new Symptom("Feelings of Exhaustion And Weakness", null, null, null, null, null);
                intent.putExtra("Symptom", (Parcelable) symptom);
                intent.putExtra("Type", "Add");
                startActivity(intent);
            }
        });

        Button buttonFeelingsOfUnusualThirst = findViewById(R.id.buttonFeelingsOfUnusualThirst);
        buttonFeelingsOfUnusualThirst.setOnClickListener(new View.OnClickListener()      {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AddSymptomActivity.class);
                Symptom symptom = new Symptom("Feelings of Unusual Thirst", null, null, null, null, null);
                intent.putExtra("Symptom", (Parcelable) symptom);
                intent.putExtra("Type", "Add");
                startActivity(intent);
            }
        });

        Button buttonNauseaVomiting = findViewById(R.id.buttonNauseaVomiting);
        buttonNauseaVomiting.setOnClickListener(new View.OnClickListener()      {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AddSymptomActivity.class);
                Symptom symptom = new Symptom("Nausea/Vomiting", null, null, null, null, null);
                intent.putExtra("Symptom", (Parcelable) symptom);
                intent.putExtra("Type", "Add");
                startActivity(intent);
            }
        });

        Button buttonTroubleBreathing = findViewById(R.id.buttonTroubleBreathing);
        buttonTroubleBreathing.setOnClickListener(new View.OnClickListener()      {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AddSymptomActivity.class);
                Symptom symptom = new Symptom("Trouble Breathing", null, null, null, null, null);
                intent.putExtra("Symptom", (Parcelable) symptom);
                intent.putExtra("Type", "Add");
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
