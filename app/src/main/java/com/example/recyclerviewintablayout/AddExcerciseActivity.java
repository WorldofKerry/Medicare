package com.example.recyclerviewintablayout;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

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
    Button dateButton, timeButtonStart, timeButtonEnd;
    private String dateSelected = "", timeSelectedStart = "", timeSelectedEnd = "";
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

        if (bundle != null) {
            excercise = bundle.getParcelable("Excercise");
            position = bundle.getInt("Position", -1);
            type = bundle.getString("Type", null);
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Exercise Tracker");

        Button buttonAddExcercise = findViewById(R.id.buttonAddExcercise);
        Button buttonRemoveExcercise = findViewById(R.id.buttonRemoveExcercise);

        final EditText editTextCalories = (EditText) findViewById(R.id.editTextExcerciseCaloriesBurned);
        editTextCalories.setText(excercise.getCalories(), TextView.BufferType.EDITABLE);
        //temporary ^^

        listExcercise = (ArrayList<Excercise>) PrefSingleton.getInstance().LoadPreferenceList("listExcercise", new TypeToken<ArrayList<Excercise>>() {
        }.getType());

        if (type.equals("Add")) {
            buttonRemoveExcercise.setEnabled(false);
            buttonAddExcercise.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PrefSingleton.getInstance().Initialize(getApplicationContext());

                    excercise.setType(Integer.toString(positionType));
                    excercise.setTime(dateSelected + "+" + timeSelectedStart);
                    excercise.setEndTime(timeSelectedEnd);
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
                    //listExcercise.add(excercise);

                    int tempMinuteValue, thisMinuteValue;
                    boolean changeFailure = true;
                    String[] tempTimeIntervals;
                    Calendar tempCal = Calendar.getInstance();
                    tempTimeIntervals = excercise.getTime().split("[-+:]");
                    thisMinuteValue = (tempCal.get(Calendar.YEAR) - Integer.parseInt(tempTimeIntervals[0])) * 525600 + (tempCal.get(Calendar.MONTH) + 1 - Integer.parseInt(tempTimeIntervals[1])) * 43800 + (tempCal.get(Calendar.DAY_OF_MONTH) - Integer.parseInt(tempTimeIntervals[2])) * 1440 + (tempCal.get(Calendar.HOUR_OF_DAY) - Integer.parseInt(tempTimeIntervals[3])) * 60 + (tempCal.get(Calendar.MINUTE) - Integer.parseInt(tempTimeIntervals[4]));
                    //listExcercise.remove(position);
                    if (listExcercise.size() > 0) {
                        for (Excercise tempExcercise : listExcercise) {
                            tempTimeIntervals = tempExcercise.getTime().split("[-+:]");
                            tempMinuteValue = (tempCal.get(Calendar.YEAR) - Integer.parseInt(tempTimeIntervals[0])) * 525600 + (tempCal.get(Calendar.MONTH) + 1 - Integer.parseInt(tempTimeIntervals[1])) * 43800 + (tempCal.get(Calendar.DAY_OF_MONTH) - Integer.parseInt(tempTimeIntervals[2])) * 1440 + (tempCal.get(Calendar.HOUR_OF_DAY) - Integer.parseInt(tempTimeIntervals[3])) * 60 + (tempCal.get(Calendar.MINUTE) - Integer.parseInt(tempTimeIntervals[4]));
                            if (thisMinuteValue > tempMinuteValue) {
                                listExcercise.add(listExcercise.indexOf(tempExcercise), excercise);
                                changeFailure = false;
                                break;
                            } else if (thisMinuteValue == tempMinuteValue) {
                                showToast("Could not edit: Two entries cannot have the exact same time.");
                                changeFailure = false;
                                break;
                            }
                        }
                    } else {
                        listExcercise.add(excercise);
                        changeFailure = false;
                    }
                    if (changeFailure) {
                        listExcercise.add(excercise);
                    }

                    PrefSingleton.getInstance().writePreference("listExcercise", listExcercise);

                    Intent intent = new Intent(v.getContext(), SelectionActivity.lastActivity);
                    startActivity(intent);
                }
            });
        } else if (type.equals("Edit")) {
            spinnerTypeOfWorkout.setSelection(Integer.parseInt(excercise.getType()));
            spinnerTypeOfWarmup.setSelection(Integer.parseInt(excercise.getWarmUp()));

            setDateText(excercise.getTime());
            setTimeText(excercise.getTime());
            setEndTimeText(excercise.getEndTime());

            buttonAddExcercise.setText(R.string.confirmEdit);
            buttonAddExcercise.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PrefSingleton.getInstance().Initialize(getApplicationContext());

                    excercise.setType(Integer.toString(positionType));
                    excercise.setTime(dateSelected + "+" + timeSelectedStart);
                    excercise.setEndTime(timeSelectedEnd);
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

                    //listExcercise.set(position, excercise);
                    int tempMinuteValue, thisMinuteValue;
                    boolean changeFailure = true;
                    String[] tempTimeIntervals;
                    Excercise tempBackupExcercise = listExcercise.get(position);
                    Calendar tempCal = Calendar.getInstance();
                    tempTimeIntervals = excercise.getTime().split("[-+:]");
                    thisMinuteValue = (tempCal.get(Calendar.YEAR) - Integer.parseInt(tempTimeIntervals[0])) * 525600 + (tempCal.get(Calendar.MONTH) + 1 - Integer.parseInt(tempTimeIntervals[1])) * 43800 + (tempCal.get(Calendar.DAY_OF_MONTH) - Integer.parseInt(tempTimeIntervals[2])) * 1440 + (tempCal.get(Calendar.HOUR_OF_DAY) - Integer.parseInt(tempTimeIntervals[3])) * 60 + (tempCal.get(Calendar.MINUTE) - Integer.parseInt(tempTimeIntervals[4]));
                    listExcercise.remove(position);
                    if (listExcercise.size() > 0) {
                        for (Excercise tempExcercise : listExcercise) {
                            tempTimeIntervals = tempExcercise.getTime().split("[-+:]");
                            tempMinuteValue = (tempCal.get(Calendar.YEAR) - Integer.parseInt(tempTimeIntervals[0])) * 525600 + (tempCal.get(Calendar.MONTH) + 1 - Integer.parseInt(tempTimeIntervals[1])) * 43800 + (tempCal.get(Calendar.DAY_OF_MONTH) - Integer.parseInt(tempTimeIntervals[2])) * 1440 + (tempCal.get(Calendar.HOUR_OF_DAY) - Integer.parseInt(tempTimeIntervals[3])) * 60 + (tempCal.get(Calendar.MINUTE) - Integer.parseInt(tempTimeIntervals[4]));
                            if (thisMinuteValue > tempMinuteValue) {
                                listExcercise.add(listExcercise.indexOf(tempExcercise), excercise);
                                changeFailure = false;
                                break;
                            } else if (thisMinuteValue == tempMinuteValue) {
                                showToast("Could not edit: Two entries cannot have the exact same time.");
                                listExcercise.add(position, tempBackupExcercise);
                                changeFailure = false;
                                break;
                            }
                        }
                    } else {
                        listExcercise.add(excercise);
                        changeFailure = false;
                    }
                    if (changeFailure) {
                        listExcercise.add(excercise);
                    }

                    PrefSingleton.getInstance().writePreference("listExcercise", listExcercise);

                    Intent intent = new Intent(v.getContext(), SelectionActivity.lastActivity);
                    startActivity(intent);
                }
            });
            buttonRemoveExcercise.setOnClickListener(new View.OnClickListener() {
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
        switch (item.getItemId()) {
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

    private void setDateText(String time) {
        dateSelected = time.split("[+]")[0];
        final String[] timeIntervals = dateSelected.split("[-]");
        if(timeIntervals.length < 3) return;

        final String displayDate = months[Integer.parseInt(timeIntervals[1]) - 1] + " " + timeIntervals[2] + " " + timeIntervals[0];
        dateButton.setText(displayDate);
    }

    private void showTimePicker(final Button timeButton) {
        String[] timeIntervals;

        if(timeButton == timeButtonStart) {
            timeIntervals = timeSelectedStart.split("[+]");
        } else {
            timeIntervals = timeSelectedEnd.split("[+]");
        }

        timeIntervals = timeIntervals[timeIntervals.length - 1].split("[:]");

        int hour = Integer.parseInt(timeIntervals[0]);
        int minute = Integer.parseInt(timeIntervals[1]);

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

        if (hour == 0) hour = 24;
        boolean isPM = (hour > 12);
        if (isPM) hour -= 12;

        String displayTime = hour + ":" + String.format(Locale.CANADA, "%02d", minute);
        displayTime += isPM ? " PM" : " AM";

        timeButton.setText(displayTime);
    }

    private void setTimeText(String time) {
        String[] timeIntervals;

        timeSelectedStart = time.split("[+]")[1];
        timeIntervals = timeSelectedStart.split("[:]");

        int hour = Integer.parseInt(timeIntervals[0]);

        if(hour == 0) hour = 24;
        boolean isPM = (hour > 12);
        if(isPM) hour -= 12;
        if(hour == 12) isPM = !isPM;

        String displayTime = hour + ":" + String.format(Locale.CANADA,"%02d", Integer.parseInt(timeIntervals[1]));
        displayTime += isPM ? " PM" : " AM";

        timeButtonStart.setText(displayTime);
    }

    private void setEndTimeText(String time) {
        if(time == null || time.equals("")) {
            timeButtonEnd.setText(timeButtonStart.getText());
            return;
        }

        timeSelectedEnd = time;
        String[] timeIntervals;

        timeIntervals = timeSelectedEnd.split("[+]");
        timeIntervals = timeIntervals[timeIntervals.length - 1].split("[:]");

        int hour = Integer.parseInt(timeIntervals[0]);

        if(hour == 0) hour = 24;
        boolean isPM = (hour > 12);
        if(isPM) hour -= 12;
        if(hour == 12) isPM = !isPM;

        String displayTime = hour + ":" + String.format(Locale.CANADA,"%02d", Integer.parseInt(timeIntervals[1]));
        displayTime += isPM ? " PM" : " AM";

        timeButtonEnd.setText(displayTime);
    }

    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
