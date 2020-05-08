package com.example.recyclerviewintablayout;

import android.content.Intent;
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

import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class FragmentExercise extends Fragment implements ExcerciseRecyclerViewAdapter.OnNoteListener{

    View view;
    private RecyclerView myRecyclerView;
    private List<Excercise> listExcercise;


    public FragmentExercise() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.excercise_fragment,container,false);
        myRecyclerView = (RecyclerView) view.findViewById(R.id.excercise_recyclerview);
        ExcerciseRecyclerViewAdapter recyclerViewAdapter = new ExcerciseRecyclerViewAdapter(getContext(), listExcercise, (ExcerciseRecyclerViewAdapter.OnNoteListener) this);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        myRecyclerView.setAdapter(recyclerViewAdapter);

        return view;    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PrefSingleton.getInstance().Initialize(getContext());

        listExcercise = (ArrayList<Excercise>) PrefSingleton.getInstance().LoadPreferenceList("listExcercise",new TypeToken<ArrayList<Excercise>>() {}.getType());
    }

    @Override
    public void onNoteClick(int position) {
        Log.d(TAG, "onNoteClick: clicked.");

        Intent intent = new Intent(getContext(), AddExcerciseActivity.class);
        intent.putExtra("Excercise", listExcercise.get(position));
        intent.putExtra("Position", position);
        intent.putExtra("Type", "Edit");
        startActivity(intent);
    }
}
