package com.example.recyclerviewintablayout;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class AddExcerciseActivity extends AppCompatActivity {

    private List<Symptom> listExcercise;
    Symptom excercise;
    int position;
    String type;
    String level;
    String notes;
    private TextView textViewSymptomLevel;
    private SeekBar seekBarSymptomLevel;
    private int positionLocation = 0;
    private String[] arrayLocation;
    private int positionType = 0;
    private String[] arrayType;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_symptom);

        arrayLocation = getResources().getStringArray(R.array.Location);
        Spinner spinnerLocation = (Spinner) findViewById(R.id.spinnerSymptomLocation);
        ArrayAdapter<String> stringArrayAdapterLocation = new ArrayAdapter<String>(AddExcerciseActivity.this, android.R.layout.simple_list_item_1, arrayLocation);
        stringArrayAdapterLocation.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLocation.setAdapter(stringArrayAdapterLocation);

        spinnerLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                positionLocation = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        arrayType = getResources().getStringArray(R.array.Type);
        Spinner spinnerType = (Spinner) findViewById(R.id.spinnerSymptomType);
        ArrayAdapter<String> stringArrayAdapterType = new ArrayAdapter<String>(AddExcerciseActivity.this, android.R.layout.simple_list_item_1, arrayType);
        stringArrayAdapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(stringArrayAdapterType);

        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                positionType = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        textViewSymptomLevel = (TextView) findViewById(R.id.textViewSymptomLevel);
        seekBarSymptomLevel = (SeekBar) findViewById(R.id.seekBarSymptomLevel);
        seekBarSymptomLevel.setMax(0);
        seekBarSymptomLevel.setMax(10);
        seekBarSymptomLevel.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                level=Integer.toString(progress);
                textViewSymptomLevel.setText("" + progress + "/10");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle!=null) {
            excercise = bundle.getParcelable("Symptom");
            position = bundle.getInt("Position", -1);
            type = bundle.getString("Type", null);

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(excercise.getName());

            Button buttonAddSymptom = findViewById(R.id.buttonAddSymptom);
            Button buttonRemoveSymptom = findViewById(R.id.buttonRemoveSymptom);

            EditText editText = (EditText) findViewById(R.id.editTextSymptomTime);
            editText.setText(excercise.getTime(), TextView.BufferType.EDITABLE);
            editText = (EditText) findViewById(R.id.editTextSymptomNotes);
            editText.setText(excercise.getNotes(), TextView.BufferType.EDITABLE);

            listExcercise = (ArrayList<Symptom>) PrefSingleton.getInstance().LoadPreferenceList("listExcercise",new TypeToken<ArrayList<Symptom>>() {}.getType());

            if (type.equals("Add")) {
                buttonRemoveSymptom.setEnabled(false);
                buttonAddSymptom.setOnClickListener(new View.OnClickListener()      {
                    @Override
                    public void onClick(View v) {

                        PrefSingleton.getInstance().Initialize(getApplicationContext());

                        excercise.setLevel(level);
                        excercise.setLocation(Integer.toString(positionLocation));
                        excercise.setType(Integer.toString(positionType));
                        excercise.setTime(((EditText) findViewById(R.id.editTextSymptomTime)).getText().toString());
                        excercise.setNotes(((EditText) findViewById(R.id.editTextSymptomNotes)).getText().toString());

                        listExcercise.add(excercise);

                        PrefSingleton.getInstance().writePreference("listExcercise", listExcercise);

                        Intent intent = new Intent(v.getContext(), MainActivity.class);
                        startActivity(intent);
                    }
                });
            } else if (type.equals("Edit")) {

                seekBarSymptomLevel.setMax(0);
                seekBarSymptomLevel.setMax(10);
                seekBarSymptomLevel.setProgress(Integer.parseInt(excercise.getLevel()));
                textViewSymptomLevel.setText(excercise.getLevel() + "/10");
                spinnerLocation.setSelection(Integer.parseInt(excercise.getLocation()));
                spinnerType.setSelection(Integer.parseInt(excercise.getType()));

                buttonAddSymptom.setText(R.string.save_Symptom);
                buttonAddSymptom.setOnClickListener(new View.OnClickListener()      {
                    @Override
                    public void onClick(View v) {
                        PrefSingleton.getInstance().Initialize(getApplicationContext());

                        excercise.setLevel(level);
                        excercise.setLocation(Integer.toString(positionLocation));
                        excercise.setType(Integer.toString(positionType));
                        excercise.setTime(((EditText) findViewById(R.id.editTextSymptomTime)).getText().toString());
                        excercise.setNotes(((EditText) findViewById(R.id.editTextSymptomNotes)).getText().toString());

                        listExcercise.set(position, excercise);

                        PrefSingleton.getInstance().writePreference("listExcercise", listExcercise);

                        Intent intent = new Intent(v.getContext(), MainActivity.class);
                        startActivity(intent);
                    }
                });
                buttonRemoveSymptom.setOnClickListener(new View.OnClickListener()      {
                    @Override
                    public void onClick(View v) {
                        listExcercise.remove(position);

                        PrefSingleton.getInstance().writePreference("listExcercise", listExcercise);

                        Intent intent = new Intent(v.getContext(), MainActivity.class);
                        startActivity(intent);
                    }
                });
            }




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
