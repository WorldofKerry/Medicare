package com.example.recyclerviewintablayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainReminderActivity extends AppCompatActivity {
    private List<MainReminderBuilder> listReminders;
    private RecyclerView myRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_reminder);

        listReminders = new ArrayList<>();
        listReminders.add(new MainReminderBuilder("bruh","Saturjday","10:30pm","11:30pm"));
        listReminders.add(new MainReminderBuilder("ThisEventReminder","Mon,Tues","10:10pm","12:30pm"));

        myRecyclerView = (RecyclerView) findViewById(R.id.reminder_recyclerview);
        MainReminderActivityViewAdapter recyclerViewAdapter = new MainReminderActivityViewAdapter(this, listReminders);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        myRecyclerView.setAdapter(recyclerViewAdapter);



    }
}
