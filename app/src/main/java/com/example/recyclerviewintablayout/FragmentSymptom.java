package com.example.recyclerviewintablayout;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
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

public class FragmentSymptom extends Fragment {

    View view;
    private RecyclerView myRecyclerView;
    private List<Symptom> listSymptom;

    public FragmentSymptom() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.symptom_fragment,container,false);
        myRecyclerView = (RecyclerView) view.findViewById(R.id.symptom_recyclerview);
        SymptomRecyclerViewAdapter recyclerViewAdapter = new SymptomRecyclerViewAdapter(getContext(), listSymptom);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        myRecyclerView.setAdapter(recyclerViewAdapter);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PrefSingleton.getInstance().Initialize(getContext());

        listSymptom = (ArrayList<Symptom>) PrefSingleton.getInstance().LoadPreferenceList("listSymptom");


        /*
        listSymptom = new ArrayList<>();
        listSymptom.add(new Symptom("Bobby", "Johnny", "Chinatown", "Brazil"));
        listSymptom.add(new Symptom("Symptom Name", "Location", "Level", "Time"));
        listSymptom.add(new Symptom("Headache", "Head (lmao)", "11", "11:45pm"));
        listSymptom.add(new Symptom("Bobby", "Johnny", "Chinatown", "Brazil"));
        */
    }

}
