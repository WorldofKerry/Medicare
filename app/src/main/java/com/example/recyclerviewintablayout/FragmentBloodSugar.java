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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class FragmentBloodSugar extends Fragment implements BloodSugarRecyclerViewAdapter.OnNoteListener {

    View view;
    private RecyclerView myRecyclerView;
    private List<BloodSugar> listBloodSugar = new ArrayList<>();

    public FragmentBloodSugar() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.blood_sugar_fragment,container,false);
        myRecyclerView = (RecyclerView) view.findViewById(R.id.blood_sugar_recyclerview);
        BloodSugarRecyclerViewAdapter recyclerViewAdapter = new BloodSugarRecyclerViewAdapter(getContext(), listBloodSugar, this);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setStackFromEnd(true);
        mLayoutManager.setReverseLayout(true);
        myRecyclerView.setLayoutManager(mLayoutManager);
        myRecyclerView.setAdapter(recyclerViewAdapter);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PrefSingleton.getInstance().Initialize(getContext());
        listBloodSugar = (ArrayList<BloodSugar>) PrefSingleton.getInstance().LoadPreferenceList("listBloodSugar",new TypeToken<ArrayList<BloodSugar>>() {}.getType());
        //listBloodSugar.add(new BloodSugar("392", "yes","??",false));
    }

    @Override
    public void onNoteClick(int position) {
        Log.d(TAG, "onNoteClick: clicked.");

        Intent intent = new Intent(getContext(), AddBloodSugarActivity.class);
        intent.putExtra("BloodSugar", listBloodSugar.get(position));
        intent.putExtra("Position", position);
        intent.putExtra("Type", "Edit");
        startActivity(intent);
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}