package com.example.recyclerviewintablayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.getbase.floatingactionbutton.FloatingActionButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ReminderActivity extends AppCompatActivity {

    RecyclerView myRecyclerView;
    private List<Reminder> listReminder = new ArrayList<>();

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SelectionActivity.lastActivity = ReminderActivity.class;
        setContentView(R.layout.activity_reminder);

        getSupportActionBar().setTitle("Reminders");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        myRecyclerView = (RecyclerView) findViewById(R.id.reminder_recyclerview);
        ReminderRecyclerViewAdapter recyclerViewAdapter = new ReminderRecyclerViewAdapter(this, listReminder);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        myRecyclerView.setAdapter(recyclerViewAdapter);

        listReminder.add(new Reminder("Go for a walk", "11:15 - 13:00","Everyday","diet"));
        listReminder.add(new Reminder("Go for a walk", "11:15 - 13:00","Everyday","pillsicon"));
        listReminder.add(new Reminder("Go for a walk", "11:15 - 13:00","Everyday","workout"));
        listReminder.add(new Reminder("Go for a walk", "11:15 - 13:00","Everyday","diet"));
        listReminder.add(new Reminder("Go for a walk", "11:15 - 13:00","Everyday","pillsicon"));
        listReminder.add(new Reminder("Go for a walk", "11:15 - 13:00","Everyday","workout"));
        listReminder.add(new Reminder("Go for a walk", "11:15 - 13:00","Everyday","diet"));
        listReminder.add(new Reminder("Go for a walk", "11:15 - 13:00","Everyday","pillsicon"));
        listReminder.add(new Reminder("Go for a walk", "11:15 - 13:00","Everyday","workout"));
        listReminder.add(new Reminder("Go for a walk", "11:15 - 13:00","Everyday","diet"));
        listReminder.add(new Reminder("Go for a walk", "11:15 - 13:00","Everyday","pillsicon"));
        listReminder.add(new Reminder("Go for a walk", "11:15 - 13:00","Everyday","workout"));
        listReminder.add(new Reminder("Go for a walk", "11:15 - 13:00","Everyday","diet"));
        listReminder.add(new Reminder("Go for a walk", "11:15 - 13:00","Everyday","pillsicon"));
        listReminder.add(new Reminder("Go for a walk", "11:15 - 13:00","Everyday","workout"));
        listReminder.add(new Reminder("Go for a walk", "11:15 - 13:00","Everyday","diet"));
        listReminder.add(new Reminder("Go for a walk", "11:15 - 13:00","Everyday","pillsicon"));
        listReminder.add(new Reminder("Go for a walk", "11:15 - 13:00","Everyday","workout"));
        listReminder.add(new Reminder("Go for a walk", "11:15 - 13:00","Everyday","diet"));
        listReminder.add(new Reminder("Go for a walk", "11:15 - 13:00","Everyday","pillsicon"));
        listReminder.add(new Reminder("Go for a walk", "11:15 - 13:00","Everyday","workout"));
        listReminder.add(new Reminder("Go for a walk", "11:15 - 13:00","Everyday","diet"));
        listReminder.add(new Reminder("Go for a walk", "11:15 - 13:00","Everyday","pillsicon"));
        listReminder.add(new Reminder("Go for a walk", "11:15 - 13:00","Everyday","workout"));
        listReminder.add(new Reminder("Go for a walk", "11:15 - 13:00","Everyday","diet"));
        listReminder.add(new Reminder("Go for a walk", "11:15 - 13:00","Everyday","pillsicon"));
        listReminder.add(new Reminder("Go for a walk", "11:15 - 13:00","Everyday","workout"));

        FloatingActionButton floatingActionButton = findViewById(R.id.fab_action1);
        floatingActionButton.setOnClickListener(new View.OnClickListener()      {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SelectionActivity.class);
                startActivity(intent);
            }
        });

        FloatingActionButton floatingActionButton2 = findViewById(R.id.fab_action2);
        floatingActionButton2.setOnClickListener(new View.OnClickListener()      {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CalendarActivity.class);
                startActivity(intent);
            }
        });

        FloatingActionButton floatingActionButton3 = findViewById(R.id.fab_action3);
        floatingActionButton3.setOnClickListener(new View.OnClickListener()      {
            @Override
            public void onClick(View v) {
                // Testing
                Intent intent = new Intent(v.getContext(), ReminderPopup.class);
                startActivity(intent);
            }
        });

        FloatingActionButton floatingActionButton4 = findViewById(R.id.fab_action4);
        floatingActionButton4.setOnClickListener(new View.OnClickListener()      {
            @Override
            public void onClick(View v) {
                // Testing
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

    }
}
