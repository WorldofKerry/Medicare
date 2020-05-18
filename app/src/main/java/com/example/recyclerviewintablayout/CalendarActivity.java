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
	private ArrayList<Symptom> symptoms;
	private ArrayList<Symptom> displayedSymptoms;
	ViewPagerAdapter adapter;

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

		// Getting the list of symptoms and cloning them to another list to be modified, should be fine

		symptoms = (ArrayList) PrefSingleton.getInstance().LoadPreferenceList("listSymptom", new TypeToken<ArrayList<Symptom>>() {}.getType());
		displayedSymptoms = new ArrayList<>();

		// Setting calendar events from symptom list

		setSymptomEvents();

		calendar.setListener(new CompactCalendarView.CompactCalendarViewListener() {
			@Override
			public void onDayClick(Date dateClicked) {
				Calendar c = Calendar.getInstance();
				c.setTime(dateClicked);
				setDateText(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));

				// Writing to the other ArrayList

				displayedSymptoms.clear();

				for(Symptom s : symptoms) {
					if (s.getTime().split("[+]")[0].equals(dateSelected)) {
						displayedSymptoms.add(s);
						adapter.notifyDataSetChanged();
					}
				}

				setDisplayedSymptoms();
			}

			@Override
			public void onMonthScroll(Date firstDayOfNewMonth) {
				// Resetting displayed symptom list to the full list when the month changes

				Calendar c = Calendar.getInstance();
				c.setTime(firstDayOfNewMonth);
				setDateText(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));

				getSupportActionBar().setTitle(dateFormat.format(firstDayOfNewMonth));

				// This is an inefficient copy-paste
				// but it's the only way I can make the function work

				displayedSymptoms.clear();

				for(Symptom s : symptoms) {
					if (s.getTime().substring(0, 7).equals(dateSelected.substring(0, 7))) {
						displayedSymptoms.add(s);
						adapter.notifyDataSetChanged();
					}
				}

				setDisplayedSymptoms();
			}
		});

		// View Pager

		TabLayout tabLayout = findViewById(R.id.calTabLayout);
		ViewPager viewPager = findViewById(R.id.calViewPager);
		adapter = new ViewPagerAdapter(getSupportFragmentManager());

		for(Symptom s : symptoms) {
			if (s.getTime().substring(0, 7).equals(dateSelected.substring(0, 7))) {
				displayedSymptoms.add(s);
				adapter.notifyDataSetChanged();
			}
		}
		PrefSingleton.getInstance().Initialize(this);

		adapter.AddFragment(new FragmentSymptom(displayedSymptoms), "Symptoms");
		adapter.AddFragment(new FragmentBloodSugar(), "Blood Sugar");
		adapter.AddFragment(new FragmentExercise(), "Exercise");

		viewPager.setAdapter(adapter);
		tabLayout.setupWithViewPager(viewPager);

		// Over here I reset the symptoms displayed to the full symptom list

		FloatingActionButton floatingActionButton = findViewById(R.id.fab_action1);
		floatingActionButton.setOnClickListener(new View.OnClickListener()      {
			@Override
			public void onClick(View v) {
				PrefSingleton.getInstance().writePreference("listSymptom", symptoms);

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
				PrefSingleton.getInstance().writePreference("listSymptom", symptoms);

				Intent intent = new Intent(v.getContext(), ReminderActivity.class);
				startActivity(intent);
			}
		});

		FloatingActionButton floatingActionButton4 = findViewById(R.id.fab_action4);
		floatingActionButton4.setOnClickListener(new View.OnClickListener()      {
			@Override
			public void onClick(View v) {
				// Testing
				PrefSingleton.getInstance().writePreference("listSymptom", symptoms);

				Intent intent = new Intent(v.getContext(), MainActivity.class);
				startActivity(intent);
			}
		});
	}

	private void setDisplayedSymptoms() {
		// The goal is to allow the user to click on a calendar date, and view the symptom inputs they have for that specific date.
		// Currently when the user clicks on a date, an extra fragment Symptom fragment is added. The new Symptom fragment recyclerview shows the correct inputs.
		// However, I don't want to add an extra fragments, but I can't seem to remove the old fragments.
		// This can probably also be done by directly updating the recyclerview in the fragment, but can't figure out how either.

		adapter.ClearFragments();
		adapter.AddFragment(new FragmentSymptom(displayedSymptoms), "Symptoms");
		adapter.AddFragment(new FragmentBloodSugar(), "Blood Sugar");
		adapter.AddFragment(new FragmentExercise(), "Exercise");
		adapter.notifyDataSetChanged();
	}

	private void setDateText(int year, int month, int dayOfMonth) {
		dateSelected = year + "-" + (month + 1) + "-" + dayOfMonth;
	}

	private void setSymptomEvents() {
		for(int i = 0; i < symptoms.size(); i++) {
			final String[] date = symptoms.get(i).getTime().split("[-+:]");

			Calendar c = Calendar.getInstance();
			c.set(Integer.parseInt(date[0]), Integer.parseInt(date[1]) - 1, Integer.parseInt(date[2]), Integer.parseInt(date[3]), Integer.parseInt(date[4]));

			Event event = new Event(Color.RED, c.getTimeInMillis());

			calendar.addEvent(event);
		}
	}
}
