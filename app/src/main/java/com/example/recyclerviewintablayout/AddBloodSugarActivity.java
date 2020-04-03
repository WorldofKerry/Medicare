package com.example.recyclerviewintablayout;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class AddBloodSugarActivity extends AppCompatActivity {

    private List<BloodSugar> listBloodSugar;
    BloodSugar bloodSugar;
    int position;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_blood_sugar);

        Intent intent = getIntent();
        bloodSugar = new BloodSugar(null,null,null,false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Blood Sugar");

            EditText editText = (EditText) findViewById(R.id.editTextBloodSugarLevel);
            editText.setText(bloodSugar.getLevel(), TextView.BufferType.EDITABLE);
            editText = (EditText) findViewById(R.id.editTextBloodSugarTime);
            editText.setText(bloodSugar.getTime(), TextView.BufferType.EDITABLE);

            listBloodSugar = (ArrayList<BloodSugar>) PrefSingleton.getInstance().LoadPreferenceList("listBloodSugar",new TypeToken<ArrayList<BloodSugar>>() {}.getType());

            Button buttonAddBloodSugar = findViewById(R.id.buttonAddBloodSugar);
            buttonAddBloodSugar.setOnClickListener(new View.OnClickListener()      {
                @Override
                public void onClick(View v) {

                    PrefSingleton.getInstance().Initialize(getApplicationContext());

                    bloodSugar.setLevel(((EditText) findViewById(R.id.editTextBloodSugarLevel)).getText().toString());
                    bloodSugar.setTime(((EditText) findViewById(R.id.editTextBloodSugarTime)).getText().toString());
                    bloodSugar.setNotes(((EditText) findViewById(R.id.editTextBloodSugarNotes)).getText().toString());
                    bloodSugar.setSafe(!((Switch) findViewById(R.id.switchBloodSugarIsMoreThanTwoHours)).isChecked());

                    listBloodSugar.add(bloodSugar);

                    PrefSingleton.getInstance().writePreference("listBloodSugar", listBloodSugar);

                    Intent intent = new Intent(v.getContext(), MainActivity.class);
                    startActivity(intent);
                }
            });

            Button buttonRemoveBloodSugar = findViewById(R.id.buttonRemoveBloodSugar);
            buttonRemoveBloodSugar.setOnClickListener(new View.OnClickListener()      {
                @Override
                public void onClick(View v) {
                    listBloodSugar.remove(position);

                    PrefSingleton.getInstance().writePreference("listBloodSugar", listBloodSugar);

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
