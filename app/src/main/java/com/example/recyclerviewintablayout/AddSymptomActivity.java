package com.example.recyclerviewintablayout;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AddSymptomActivity extends AppCompatActivity {

    private List<Symptom> listSymptom;
    Symptom symptom;
    int position;
    String type;
    String level = "0";
    Button dateButton, timeButton;
    private String dateSelected = "", timeSelected = "";
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
        ArrayAdapter<String> stringArrayAdapterLocation = new ArrayAdapter<String>(this, R.layout.item_symptom_spinner, arrayLocation);
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
        ArrayAdapter<String> stringArrayAdapterType = new ArrayAdapter<String>(AddSymptomActivity.this, R.layout.item_symptom_spinner, arrayType);
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

        //textViewSymptomLevel = (TextView) findViewById(R.id.textViewSymptomLevel);
        seekBarSymptomLevel = (SeekBar) findViewById(R.id.seekBarSymptomLevel);
        seekBarSymptomLevel.setMax(0);
        seekBarSymptomLevel.setMax(10);
        seekBarSymptomLevel.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                level=Integer.toString(progress);
                //textViewSymptomLevel.setText("" + progress + "/10");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        dateButton = findViewById(R.id.buttonSymptomDate);
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker();
            }
        });
        setDateText(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

        timeButton = findViewById(R.id.buttonSymptomTime);
        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePicker();
            }
        });
        setTimeText(Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE));

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle!=null) {
            symptom = bundle.getParcelable("Symptom");
            position = bundle.getInt("Position", -1);
            type = bundle.getString("Type", null);

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(symptom.getName());

            Button buttonAddSymptom = findViewById(R.id.buttonAddSymptom);
            Button buttonRemoveSymptom = findViewById(R.id.buttonRemoveSymptom);

            EditText editText = findViewById(R.id.editTextSymptomNotes);
            editText.setText(symptom.getNotes(), TextView.BufferType.EDITABLE);

            listSymptom = (ArrayList<Symptom>) PrefSingleton.getInstance().LoadPreferenceList("listSymptom",new TypeToken<ArrayList<Symptom>>() {}.getType());

            if (type.equals("Add")) {
                buttonRemoveSymptom.setVisibility(View.GONE);
                buttonAddSymptom.setOnClickListener(new View.OnClickListener()      {
                    @Override
                    public void onClick(View v) {

                        PrefSingleton.getInstance().Initialize(getApplicationContext());

                        symptom.setLevel(level);
                        symptom.setLocation(Integer.toString(positionLocation));
                        symptom.setType(Integer.toString(positionType));
                        symptom.setTime(dateSelected + "+" + timeSelected);
                        symptom.setNotes(((EditText) findViewById(R.id.editTextSymptomNotes)).getText().toString());

                        listSymptom.add(symptom);

                        PrefSingleton.getInstance().writePreference("listSymptom", listSymptom);

                        Intent intent = new Intent(v.getContext(), MainActivity.class);
                        startActivity(intent);
                    }
                });
            } else if (type.equals("Edit")) {
                seekBarSymptomLevel.setMax(0);
                seekBarSymptomLevel.setMax(10);
                seekBarSymptomLevel.setProgress(Integer.parseInt(symptom.getLevel()));
                //textViewSymptomLevel.setText(symptom.getLevel() + "/10");
                spinnerLocation.setSelection(Integer.parseInt(symptom.getLocation()));
                spinnerType.setSelection(Integer.parseInt(symptom.getType()));
                setDateText(symptom.getTime());
                setTimeText(symptom.getTime());

                buttonAddSymptom.setText(R.string.save_Symptom);
                buttonAddSymptom.setOnClickListener(new View.OnClickListener()      {
                    @Override
                    public void onClick(View v) {
                        PrefSingleton.getInstance().Initialize(getApplicationContext());

                        symptom.setLevel(level);
                        symptom.setLocation(Integer.toString(positionLocation));
                        symptom.setType(Integer.toString(positionType));
                        symptom.setTime(dateSelected + "+" + timeSelected);
                        symptom.setNotes(((EditText) findViewById(R.id.editTextSymptomNotes)).getText().toString());

                        listSymptom.set(position, symptom);

                        PrefSingleton.getInstance().writePreference("listSymptom", listSymptom);

                        Intent intent = new Intent(v.getContext(), MainActivity.class);
                        startActivity(intent);
                    }
                });
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
        String[] timeIntervals = dateSelected.split("[-]");

        final int year = Integer.parseInt(timeIntervals[0]);
        final int month = Integer.parseInt(months[Integer.parseInt(timeIntervals[1]) - 1]);
        final int dayOfMonth = Integer.parseInt(timeIntervals[2]);

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

    private void showTimePicker() {
        String[] timeIntervals = timeSelected.split("[:]");

        int hour = Integer.parseInt(timeIntervals[0]);
        int minute = Integer.parseInt(timeIntervals[1]);

        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                setTimeText(hour, minute);
            }
        };

        TimePickerDialog timePicker = new TimePickerDialog(this, timeSetListener, hour, minute, false);
        timePicker.show();
    }

    private void setTimeText(int hour, int minute) {
        timeSelected = hour + ":" + String.format(Locale.CANADA,"%02d", minute);

        if(hour == 0) hour = 24;
        boolean isPM = (hour > 12);
        if(isPM) hour -= 12;
        if(hour == 12) isPM = !isPM;

        String displayTime = hour + ":" + String.format(Locale.CANADA,"%02d", minute);
        displayTime += isPM ? " PM" : " AM";

        timeButton.setText(displayTime);
    }

    private void setTimeText(String time) {
        try {
            timeSelected = time.split("[+]")[1];
        } catch (ArrayIndexOutOfBoundsException e) {
            Log.d("setTimeText", "ArrayOutOfBoundsException");
            e.printStackTrace();
            return;
        }

        final String[] timeIntervals = timeSelected.split("[:]");
        int hour = Integer.parseInt(timeIntervals[0]);

        if(hour == 0) hour = 24;
        boolean isPM = (hour > 12);
        if(isPM) hour -= 12;
        if(hour == 12) isPM = !isPM;

        String displayTime = hour + ":" + String.format(Locale.CANADA,"%02d", Integer.parseInt(timeIntervals[1]));
        displayTime += isPM ? " PM" : " AM";

        timeButton.setText(displayTime);
    }
}
