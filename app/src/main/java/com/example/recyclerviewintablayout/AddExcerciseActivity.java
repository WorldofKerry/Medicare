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

    private List<Excercise> listExcercise;
    int position;
    String type;
    String level;
    String notes;
    private String[] arrayTypeOfWorkout;
    private int positionType = 0;
    private int positionWarmUp = 0;
    private String[] arrayTypeOfWarmup;
    Excercise excercise;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_excercise);

        arrayTypeOfWorkout = getResources().getStringArray(R.array.typeOfWorkout);
        Spinner spinnerTypeOfWorkout = (Spinner) findViewById(R.id.spinnerExcerciseType);
        ArrayAdapter<String> stringArrayAdapterLocation = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayTypeOfWorkout);
        stringArrayAdapterLocation.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTypeOfWorkout.setAdapter(stringArrayAdapterLocation);

        spinnerTypeOfWorkout.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                positionType = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        arrayTypeOfWarmup = getResources().getStringArray(R.array.warmUp);
        Spinner spinnerTypeOfWarmup = (Spinner) findViewById(R.id.spinnerExcerciseWarmUp);
        ArrayAdapter<String> stringArrayAdapterWarmup = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayTypeOfWarmup);
        stringArrayAdapterLocation.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTypeOfWarmup.setAdapter(stringArrayAdapterWarmup);

        spinnerTypeOfWarmup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                positionWarmUp = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        //excercise = new Excercise(null,null,null,null,null);
       if (bundle!=null) {
           excercise = bundle.getParcelable("Excercise");
           position = bundle.getInt("Position", -1);
           type = bundle.getString("Type", null);
       }
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Excercise Tracker");

            Button buttonAddExcercise = findViewById(R.id.buttonAddExcercise);
            Button buttonRemoveExcercise = findViewById(R.id.buttonRemoveExcercise);

            final EditText editTextTime = (EditText) findViewById(R.id.editTextExcerciseTimeBegin);
            editTextTime.setText(excercise.getTime(), TextView.BufferType.EDITABLE);

            final EditText editTextTimeEnd = (EditText) findViewById(R.id.editTextExcerciseTimeEnd);

            final EditText editTextCalories = (EditText) findViewById(R.id.editTextExcerciseCaloriesBurned);
            editTextCalories.setText(excercise.getCalories(), TextView.BufferType.EDITABLE);
            //temporary ^^

            listExcercise = (ArrayList<Excercise>) PrefSingleton.getInstance().LoadPreferenceList("listExcercise",new TypeToken<ArrayList<Excercise>>() {}.getType());

            if (type.equals("Add")) {
                buttonRemoveExcercise.setEnabled(false);
                buttonAddExcercise.setOnClickListener(new View.OnClickListener()      {
                    @Override
                    public void onClick(View v) {

                        PrefSingleton.getInstance().Initialize(getApplicationContext());

                        excercise.setType(Integer.toString(positionType));
                        excercise.setTime(editTextTime.getText().toString());
                        excercise.setCalories(editTextCalories.getText().toString());
                        excercise.setWarmUp(Integer.toString(positionWarmUp));
                        String strippedTimeBegin = editTextTime.getText().toString().replaceAll("[^0-9]","");
                        String strippedTimeEnd = editTextTimeEnd.getText().toString().replaceAll("[^0-9]","");
                        excercise.setDuration(Integer.toString(((1200 - Math.abs(Integer.parseInt(strippedTimeBegin) - Integer.parseInt(strippedTimeEnd))) / 5) * 3)); //converts ?????!?!? into duration (minutes)
                        //String test1 = Integer.toString(Math.abs(Integer.parseInt(strippedTimeBegin) - Integer.parseInt(strippedTimeEnd)));
                       // excercise.setNotes(((EditText) findViewById(R.id.editTextSymptomNotes)).getText().toString());

                        listExcercise.add(excercise);

                        PrefSingleton.getInstance().writePreference("listExcercise", listExcercise);

                        Intent intent = new Intent(v.getContext(), MainActivity.class);
                        startActivity(intent);
                    }
                });
            } else if (type.equals("Edit")) {
                spinnerTypeOfWorkout.setSelection(Integer.parseInt(excercise.getType()));
                spinnerTypeOfWarmup.setSelection(Integer.parseInt(excercise.getWarmUp()));

                buttonAddExcercise.setText(R.string.confirmEdit);
                buttonAddExcercise.setOnClickListener(new View.OnClickListener()      {
                    @Override
                    public void onClick(View v) {
                        PrefSingleton.getInstance().Initialize(getApplicationContext());

                        excercise.setType(Integer.toString(positionType));
                        excercise.setTime(editTextTime.getText().toString());
                        excercise.setCalories(editTextCalories.getText().toString());
                        excercise.setWarmUp(Integer.toString(positionWarmUp));
                        String strippedTimeBegin = editTextTime.getText().toString().replaceAll("[^0-9]","");
                        String strippedTimeEnd = editTextTimeEnd.getText().toString().replaceAll("[^0-9]","");
                        excercise.setDuration(Integer.toString(((1200 - Math.abs(Integer.parseInt(strippedTimeBegin) - Integer.parseInt(strippedTimeEnd))) / 5) * 3));

                        listExcercise.set(position, excercise);

                        PrefSingleton.getInstance().writePreference("listExcercise", listExcercise);

                        Intent intent = new Intent(v.getContext(), MainActivity.class);
                        startActivity(intent);
                    }
                });
                buttonRemoveExcercise.setOnClickListener(new View.OnClickListener()      {
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
