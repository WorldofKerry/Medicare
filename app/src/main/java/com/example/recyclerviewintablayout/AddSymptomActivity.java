package com.example.recyclerviewintablayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class AddSymptomActivity extends AppCompatActivity {

    private List<Symptom> listSymptom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_symptom);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle!=null) {
            getSupportActionBar().setTitle((String) bundle.get("title"));
        }

        Button button = findViewById(R.id.buttonAddSymptom);
        button.setOnClickListener(new View.OnClickListener()      {
            @Override
            public void onClick(View v) {

                getSupportActionBar().setDisplayHomeAsUpEnabled(true);

                PrefSingleton.getInstance().Initialize(getApplicationContext());

                listSymptom = (ArrayList<Symptom>) PrefSingleton.getInstance().LoadPreferenceList("listSymptom");

                // Replace this with input
                listSymptom.add(new Symptom("Bobby", "Johnny", "Chinatown", "Brazil"));

                PrefSingleton.getInstance().writePreference("listSymptom", listSymptom);

                Intent intent = new Intent(v.getContext(), MainActivity.class);
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
