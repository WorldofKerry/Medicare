package com.example.recyclerviewintablayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.reflect.TypeToken;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CalendarActivity extends AppCompatActivity {
	private CompactCalendarView calendar;
	public static String dateSelected;
	private List<Symptom> symptoms;

	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SelectionActivity.lastActivity = CalendarActivity.class;

		final SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM yyyy", Locale.CANADA);
		getSupportActionBar().setTitle(dateFormat.format(new Date()));

		setContentView(R.layout.activity_calendar);

		// Calendar

		calendar = findViewById(R.id.calendar);
		calendar.setUseThreeLetterAbbreviation(true);
		calendar.shouldDrawIndicatorsBelowSelectedDays(true);
		calendar.setFirstDayOfWeek(1);

		setDateText(Calendar.getInstance().get(Calendar.YEAR),
				Calendar.getInstance().get(Calendar.MONTH),
				Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

		symptoms = (ArrayList<Symptom>) PrefSingleton.getInstance().LoadPreferenceList
				("listSymptom",new TypeToken<ArrayList<Symptom>>() {}.getType());

		setSymptomEvents();

		calendar.setListener(new CompactCalendarView.CompactCalendarViewListener() {
			@Override
			public void onDayClick(Date dateClicked) {

			}

			@Override
			public void onMonthScroll(Date firstDayOfNewMonth) {
				getSupportActionBar().setTitle(dateFormat.format(firstDayOfNewMonth));
			}
		});

		// View Pager

		TabLayout tabLayout = findViewById(R.id.calTabLayout);
		ViewPager viewPager = findViewById(R.id.calViewPager);
		ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

		adapter.AddFragment(new FragmentSymptom(), "Symptoms");
		adapter.AddFragment(new FragmentBloodSugar(), "Blood Sugar");
		adapter.AddFragment(new FragmentExercise(), "Exercise");

		viewPager.setAdapter(adapter);
		tabLayout.setupWithViewPager(viewPager);

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
			}
		});

		FloatingActionButton floatingActionButton3 = findViewById(R.id.fab_action3);
		floatingActionButton3.setOnClickListener(new View.OnClickListener()      {
			@Override
			public void onClick(View v) {
				// Testing
				Intent intent = new Intent(v.getContext(), MainReminderActivity.class);
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

	private void setDateText(int year, int month, int dayOfMonth) {
		dateSelected = year + "-" + (month + 1) + "-" + dayOfMonth;
	}

	private void setSymptomEvents() {
		for(int i = 0; i < symptoms.size(); i++) {
			final String[] date = symptoms.get(i).getTime().split("[-+:]");

			Calendar c = Calendar.getInstance();
			c.set(Integer.parseInt(date[0]), Integer.parseInt(date[1]) - 1, Integer.parseInt(date[2]), Integer.parseInt(date[3]), Integer.parseInt(date[4]));

			Log.d("Epoch Time", "" + c.getTimeInMillis());
			Event event = new Event(Color.RED, c.getTimeInMillis());

			calendar.addEvent(event);
		}
	}
}
