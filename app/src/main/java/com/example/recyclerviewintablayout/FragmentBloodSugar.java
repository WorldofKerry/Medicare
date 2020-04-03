package com.example.recyclerviewintablayout;

import android.os.Bundle;
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

public class FragmentBloodSugar extends Fragment {

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
        BloodSugarRecyclerViewAdapter recyclerViewAdapter = new BloodSugarRecyclerViewAdapter(getContext(), listBloodSugar);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
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

}