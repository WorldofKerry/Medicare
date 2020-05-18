package com.example.recyclerviewintablayout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static android.os.Build.VERSION_CODES.N;
import static androidx.constraintlayout.widget.Constraints.TAG;

public class FragmentSymptom extends Fragment implements SymptomRecyclerViewAdapter.OnNoteListener {

    View view;
    private RecyclerView myRecyclerView;
    private List<Symptom> listSymptom;
    SymptomRecyclerViewAdapter recyclerViewAdapter;

    public FragmentSymptom(List<Symptom> ListSymptom) {
        listSymptom = ListSymptom;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.symptom_fragment,container,false);
        myRecyclerView = (RecyclerView) view.findViewById(R.id.symptom_recyclerview);
        recyclerViewAdapter = new SymptomRecyclerViewAdapter(getContext(), listSymptom, this);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setStackFromEnd(true);
        mLayoutManager.setReverseLayout(true);
        myRecyclerView.setLayoutManager(mLayoutManager);
        myRecyclerView.setAdapter(recyclerViewAdapter);
        return view;    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
        listSymptom = new ArrayList<>();
        listSymptom.add(new Symptom("Bobby", "Johnny", "Chinatown", "Brazil"));
        listSymptom.add(new Symptom("Symptom Name", "Location", "Level", "Time"));
        listSymptom.add(new Symptom("Headache", "Head (lmao)", "11", "11:45pm"));
        listSymptom.add(new Symptom("Bobby", "Johnny", "Chinatown", "Brazil"));
        */
    }

    @Override
    public void onNoteClick(int position) {
        Log.d(TAG, "onNoteClick: clicked.");

        Intent intent = new Intent(getContext(), AddSymptomActivity.class);
        intent.putExtra("Symptom", listSymptom.get(position));
        intent.putExtra("Position", position);
        intent.putExtra("Type", "Edit");
        startActivity(intent);
    }
}
