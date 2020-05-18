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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AddBloodSugarActivity extends AppCompatActivity {

    private List<BloodSugar> listBloodSugar;
    BloodSugar bloodSugar;
    Button timeButton, dateButton;
    String dateSelected, timeSelected;
    int position;
    String type;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_blood_sugar);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle!=null) {
            bloodSugar = bundle.getParcelable("BloodSugar");
            position = bundle.getInt("Position", -1);
            type = bundle.getString("Type", null);
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Blood Sugar");

        EditText editText = (EditText) findViewById(R.id.editTextBloodSugarLevel);
        editText.setText(bloodSugar.getLevel(), TextView.BufferType.EDITABLE);

        dateButton = findViewById(R.id.buttonBloodSugarDate);
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker();
            }
        });
        setDateText(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

        timeButton = findViewById(R.id.buttonBloodSugarTime);
        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePicker();
            }
        });
        setTimeText(Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE));

        listBloodSugar = (ArrayList<BloodSugar>) PrefSingleton.getInstance().LoadPreferenceList("listBloodSugar", new TypeToken<ArrayList<BloodSugar>>() {
        }.getType());

        Button buttonRemoveBloodSugar = findViewById(R.id.buttonRemoveBloodSugar);
        Button buttonAddBloodSugar = findViewById(R.id.buttonAddBloodSugar);

        if (type.equals("Add")) {
            buttonRemoveBloodSugar.setEnabled(false);
            buttonAddBloodSugar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    PrefSingleton.getInstance().Initialize(getApplicationContext());

                    bloodSugar.setLevel(((EditText) findViewById(R.id.editTextBloodSugarLevel)).getText().toString());
                    bloodSugar.setTime(dateSelected + "+" + timeSelected);
                    bloodSugar.setNotes(((EditText) findViewById(R.id.editTextBloodSugarNotes)).getText().toString());
                    bloodSugar.setSafe(!((Switch) findViewById(R.id.switchBloodSugarIsMoreThanTwoHours)).isChecked());

                    int tempMinuteValue, thisMinuteValue;
                    boolean changeFailure = true;
                    String[] tempTimeIntervals;
                    Calendar tempCal = Calendar.getInstance();
                    tempTimeIntervals = bloodSugar.getTime().split("[-+:]");
                    thisMinuteValue = (tempCal.get(Calendar.YEAR) - Integer.parseInt(tempTimeIntervals[0])) * 525600 + (tempCal.get(Calendar.MONTH) + 1 - Integer.parseInt(tempTimeIntervals[1])) * 43800 + (tempCal.get(Calendar.DAY_OF_MONTH) - Integer.parseInt(tempTimeIntervals[2])) * 1440 + (tempCal.get(Calendar.HOUR_OF_DAY) - Integer.parseInt(tempTimeIntervals[3])) * 60 + (tempCal.get(Calendar.MINUTE) - Integer.parseInt(tempTimeIntervals[4]));
                    //listExcercise.remove(position);
                    if (listBloodSugar.size() > 0) {
                        for (BloodSugar tempBloodSugar : listBloodSugar) {
                            tempTimeIntervals = tempBloodSugar.getTime().split("[-+:]");
                            tempMinuteValue = (tempCal.get(Calendar.YEAR) - Integer.parseInt(tempTimeIntervals[0])) * 525600 + (tempCal.get(Calendar.MONTH) + 1 - Integer.parseInt(tempTimeIntervals[1])) * 43800 + (tempCal.get(Calendar.DAY_OF_MONTH) - Integer.parseInt(tempTimeIntervals[2])) * 1440 + (tempCal.get(Calendar.HOUR_OF_DAY) - Integer.parseInt(tempTimeIntervals[3])) * 60 + (tempCal.get(Calendar.MINUTE) - Integer.parseInt(tempTimeIntervals[4]));
                            if (thisMinuteValue > tempMinuteValue) {
                                listBloodSugar.add(listBloodSugar.indexOf(tempBloodSugar), bloodSugar);
                                changeFailure = false;
                                break;
                            } else if (thisMinuteValue == tempMinuteValue) {
                                showToast("Could not edit: Two entries cannot have the exact same time.");
                                changeFailure = false;
                                break;
                            }
                        }
                    } else {
                        listBloodSugar.add(bloodSugar);
                        changeFailure = false;
                    }
                    if (changeFailure) {
                        listBloodSugar.add(bloodSugar);
                    }


                    PrefSingleton.getInstance().writePreference("listBloodSugar", listBloodSugar);

                    Intent intent = new Intent(v.getContext(), SelectionActivity.lastActivity);
                    startActivity(intent);
                }
            });

        } else if (type.equals("Edit")) {
            setDateText(bloodSugar.getTime());
            setTimeText(bloodSugar.getTime());

            buttonAddBloodSugar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PrefSingleton.getInstance().Initialize(getApplicationContext());

                    bloodSugar.setLevel(((EditText) findViewById(R.id.editTextBloodSugarLevel)).getText().toString());
                    bloodSugar.setTime(dateSelected + "+" + timeSelected);
                    bloodSugar.setNotes(((EditText) findViewById(R.id.editTextBloodSugarNotes)).getText().toString());
                    bloodSugar.setSafe(!((Switch) findViewById(R.id.switchBloodSugarIsMoreThanTwoHours)).isChecked());

                    int tempMinuteValue, thisMinuteValue;
                    boolean changeFailure = true;
                    String[] tempTimeIntervals;
                    BloodSugar tempBackupBloodSugar = listBloodSugar.get(position);
                    Calendar tempCal = Calendar.getInstance();
                    tempTimeIntervals = bloodSugar.getTime().split("[-+:]");
                    thisMinuteValue = (tempCal.get(Calendar.YEAR) - Integer.parseInt(tempTimeIntervals[0])) * 525600 + (tempCal.get(Calendar.MONTH) + 1 - Integer.parseInt(tempTimeIntervals[1])) * 43800 + (tempCal.get(Calendar.DAY_OF_MONTH) - Integer.parseInt(tempTimeIntervals[2])) * 1440 + (tempCal.get(Calendar.HOUR_OF_DAY) - Integer.parseInt(tempTimeIntervals[3])) * 60 + (tempCal.get(Calendar.MINUTE) - Integer.parseInt(tempTimeIntervals[4]));
                    listBloodSugar.remove(position);
                    if (listBloodSugar.size() > 0) {
                        for (BloodSugar tempBloodSugar : listBloodSugar) {
                            tempTimeIntervals = tempBloodSugar.getTime().split("[-+:]");
                            tempMinuteValue = (tempCal.get(Calendar.YEAR) - Integer.parseInt(tempTimeIntervals[0])) * 525600 + (tempCal.get(Calendar.MONTH) + 1 - Integer.parseInt(tempTimeIntervals[1])) * 43800 + (tempCal.get(Calendar.DAY_OF_MONTH) - Integer.parseInt(tempTimeIntervals[2])) * 1440 + (tempCal.get(Calendar.HOUR_OF_DAY) - Integer.parseInt(tempTimeIntervals[3])) * 60 + (tempCal.get(Calendar.MINUTE) - Integer.parseInt(tempTimeIntervals[4]));
                            if (thisMinuteValue > tempMinuteValue) {
                                listBloodSugar.add(listBloodSugar.indexOf(tempBloodSugar), bloodSugar);
                                changeFailure = false;
                                break;
                            } else if (thisMinuteValue == tempMinuteValue) {
                                showToast("Could not edit: Two entries cannot have the exact same time.");
                                listBloodSugar.add(position, tempBackupBloodSugar);
                                changeFailure = false;
                                break;
                            }
                        }
                    } else {
                        listBloodSugar.add(bloodSugar);
                        changeFailure = false;
                    }
                    if (changeFailure) {
                        listBloodSugar.add(bloodSugar);
                    }

                    PrefSingleton.getInstance().writePreference("listBloodSugar", listBloodSugar);

                    Intent intent = new Intent(v.getContext(), SelectionActivity.lastActivity);
                    startActivity(intent);
                }
            });

            buttonRemoveBloodSugar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listBloodSugar.remove(position);

                    PrefSingleton.getInstance().writePreference("listBloodSugar", listBloodSugar);

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
        String[] timeIntervals = dateSelected.split("[-]");

        final int year = Integer.parseInt(timeIntervals[0]);
        final int month = Integer.parseInt(timeIntervals[1]) - 1;
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

    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
