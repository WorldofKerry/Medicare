package com.example.recyclerviewintablayout;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AddExcerciseActivity extends AppCompatActivity {

    private List<Excercise> listExcercise;
    int position;
    String type;
    String level;
    String notes;
    Button dateButton, timeButtonStart,timeButtonEnd;
    private String dateSelected = "", timeSelectedStart = "", timeSelectedEnd="";
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

        dateButton = findViewById(R.id.buttonExcerciseDate);
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker();
            }
        });
        setDateText(Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

        timeButtonStart = findViewById(R.id.buttonExcerciseTimeBegin);
        timeButtonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePicker(timeButtonStart);
            }
        });
        setTimeText(Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                Calendar.getInstance().get(Calendar.MINUTE), timeButtonStart);

        timeButtonEnd = findViewById(R.id.buttonExcerciseTimeEnd);
        timeButtonEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePicker(timeButtonEnd);
            }
        });
        setTimeText(Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                Calendar.getInstance().get(Calendar.MINUTE), timeButtonEnd);

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
                        excercise.setTime(dateSelected + "+" + timeSelectedStart);
                        excercise.setCalories(editTextCalories.getText().toString());
                        excercise.setWarmUp(Integer.toString(positionWarmUp));
                        int tempStartHours = Integer.parseInt(timeSelectedStart.split("[:]")[0]);
                        int tempStartMinutes = Integer.parseInt(timeSelectedStart.split("[:]")[1]);
                        boolean mustSubtractFrom1200 = false;
                        if (tempStartHours >= 12) {
                            tempStartHours -= 12;
                            mustSubtractFrom1200 = true;
                        }
                        int tempStartDurationCalculation = tempStartHours * 100 + tempStartMinutes;
                        int tempEndHours = Integer.parseInt(timeSelectedEnd.split("[:]")[0]);
                        int tempEndMinutes = Integer.parseInt(timeSelectedEnd.split("[:]")[1]);
                        if (tempEndHours >= 12) {
                            tempEndHours -= 12;
                            mustSubtractFrom1200 = !mustSubtractFrom1200;
                        }
                        int tempEndDurationCalculation = tempEndHours * 100 + tempEndMinutes;
                        if (mustSubtractFrom1200) {
                            excercise.setDuration(Integer.toString(((1200 - Math.abs(tempStartDurationCalculation - tempEndDurationCalculation)) * 3) / 5));
                        } else {
                            excercise.setDuration(Integer.toString(((Math.abs(tempStartDurationCalculation - tempEndDurationCalculation)) * 3) / 5));
                        }
                        listExcercise.add(excercise);

                        PrefSingleton.getInstance().writePreference("listExcercise", listExcercise);

                        Intent intent = new Intent(v.getContext(), SelectionActivity.lastActivity);
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
                        excercise.setTime(dateSelected + "+" + timeSelectedStart);
                        excercise.setCalories(editTextCalories.getText().toString());
                        excercise.setWarmUp(Integer.toString(positionWarmUp));
                        int tempStartHours = Integer.parseInt(timeSelectedStart.split("[:]")[0]);
                        int tempStartMinutes = Integer.parseInt(timeSelectedStart.split("[:]")[1]);
                        boolean mustSubtractFrom1200 = false;
                        if (tempStartHours >= 12) {
                            tempStartHours -= 12;
                            mustSubtractFrom1200 = true;
                        }
                        int tempStartDurationCalculation = tempStartHours * 100 + tempStartMinutes;
                        int tempEndHours = Integer.parseInt(timeSelectedEnd.split("[:]")[0]);
                        int tempEndMinutes = Integer.parseInt(timeSelectedEnd.split("[:]")[1]);
                        if (tempEndHours >= 12) {
                            tempEndHours -= 12;
                            mustSubtractFrom1200 = !mustSubtractFrom1200;
                        }
                        int tempEndDurationCalculation = tempEndHours * 100 + tempEndMinutes;
                        if (mustSubtractFrom1200) {
                            excercise.setDuration(Integer.toString(((1200 - Math.abs(tempStartDurationCalculation - tempEndDurationCalculation)) * 3) / 5));
                        } else {
                            excercise.setDuration(Integer.toString(((Math.abs(tempStartDurationCalculation - tempEndDurationCalculation)) * 3) / 5));
                        }

                        listExcercise.set(position, excercise);

                        PrefSingleton.getInstance().writePreference("listExcercise", listExcercise);

                        Intent intent = new Intent(v.getContext(), SelectionActivity.lastActivity);
                        startActivity(intent);
                    }
                });
                buttonRemoveExcercise.setOnClickListener(new View.OnClickListener()      {
                    @Override
                    public void onClick(View v) {
                        listExcercise.remove(position);

                        PrefSingleton.getInstance().writePreference("listExcercise", listExcercise);

                        Intent intent = new Intent(v.getContext(), SelectionActivity.lastActivity);
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

    private static final String[] months = {"January", "February", "March", "April",
            "May", "June", "July", "August", "September", "October", "November", "December"};

    private void showDatePicker() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH);
        int dayOfMonth = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                setDateText(year, month, dayOfMonth);
            }
        };

        DatePickerDialog datePicker = new DatePickerDialog(this, dateSetListener, year, month, dayOfMonth);
        datePicker.show();
    }

    private void setDateText(int year, int month, int dayOfMonth) {
        dateSelected = year + "-" + (month + 1) + "-" + dayOfMonth;
        final String displayDate = months[month] + " " + dayOfMonth + " " + year;
        dateButton.setText(displayDate);
    }

    private void showTimePicker(final Button timeButton) {
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int minute = Calendar.getInstance().get(Calendar.MINUTE);

        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                setTimeText(hour, minute, timeButton);
            }
        };


        TimePickerDialog timePicker = new TimePickerDialog(this, timeSetListener, hour, minute, false);
        timePicker.show();
    }


    private void setTimeText(int hour, int minute, Button timeButton) {
        if (timeButton.equals(timeButtonStart)) {
            timeSelectedStart = hour + ":" + String.format(Locale.CANADA, "%02d", minute);
        } else {
            timeSelectedEnd = hour + ":" + String.format(Locale.CANADA, "%02d", minute);
        }
        if(hour == 0) hour = 24;
        boolean isPM = (hour > 12);
        if(isPM) hour -= 12;

        String displayTime = hour + ":" + String.format(Locale.CANADA,"%02d", minute);
        displayTime += isPM ? " PM" : " AM";

        timeButton.setText(displayTime);
    }
}
