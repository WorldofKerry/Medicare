package com.example.recyclerviewintablayout;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AddSymptomActivity extends AppCompatActivity {

    private List<Symptom> listSymptom;
    Symptom symptom;
    int position;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_symptom);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle!=null) {
            symptom = bundle.getParcelable("Symptom");

            position = bundle.getInt("Position", -1);

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(symptom.getName());

            EditText editText = (EditText) findViewById(R.id.editTextSymptomLevel);
            editText.setText(symptom.getLevel(), TextView.BufferType.EDITABLE);
            editText = (EditText) findViewById(R.id.editTextSymptomLocation);
            editText.setText(symptom.getLocation(), TextView.BufferType.EDITABLE);
            editText = (EditText) findViewById(R.id.editTextSymptomTime);
            editText.setText(symptom.getTime(), TextView.BufferType.EDITABLE);

            listSymptom = (ArrayList<Symptom>) PrefSingleton.getInstance().LoadPreferenceList("listSymptom",new TypeToken<ArrayList<Symptom>>() {}.getType());

            Button buttonAddSymptom = findViewById(R.id.buttonAddSymptom);
            buttonAddSymptom.setOnClickListener(new View.OnClickListener()      {
                @Override
                public void onClick(View v) {

                    PrefSingleton.getInstance().Initialize(getApplicationContext());

                    symptom.setLevel(((EditText) findViewById(R.id.editTextSymptomLevel)).getText().toString());
                    symptom.setLocation(((EditText) findViewById(R.id.editTextSymptomLocation)).getText().toString());
                    symptom.setTime(((EditText) findViewById(R.id.editTextSymptomTime)).getText().toString());

                    listSymptom.add(symptom);

                    PrefSingleton.getInstance().writePreference("listSymptom", listSymptom);

                    Intent intent = new Intent(v.getContext(), MainActivity.class);
                    startActivity(intent);
                }
            });

            Button buttonRemoveSymptom = findViewById(R.id.buttonRemoveSymptom);
            buttonRemoveSymptom.setOnClickListener(new View.OnClickListener()      {
                @Override
                public void onClick(View v) {
                    listSymptom.remove(position);

                    PrefSingleton.getInstance().writePreference("listSymptom", listSymptom);

                    Intent intent = new Intent(v.getContext(), MainActivity.class);
                    startActivity(intent);
                }
            });

        }




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
