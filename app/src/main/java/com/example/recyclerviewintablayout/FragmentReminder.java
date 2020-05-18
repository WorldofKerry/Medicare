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

public class FragmentReminder extends Fragment {

    View view;
    private RecyclerView myRecyclerView;
    private List<Reminder> listReminder = new ArrayList<>();

    public FragmentReminder() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.reminder_fragment,container,false);
        myRecyclerView = (RecyclerView) view.findViewById(R.id.reminder_recyclerview);
        ReminderRecyclerViewAdapter recyclerViewAdapter = new ReminderRecyclerViewAdapter(getContext(), listReminder);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        myRecyclerView.setAdapter(recyclerViewAdapter);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PrefSingleton.getInstance().Initialize(getContext());
        listReminder = (ArrayList<Reminder>) PrefSingleton.getInstance().LoadPreferenceList("listBloodSugar",new TypeToken<ArrayList<BloodSugar>>() {}.getType());
        listReminder.add(new Reminder("392", "yes","??","456454"));
        listReminder.add(new Reminder("392", "yes","??","456454"));
        listReminder.add(new Reminder("392", "yes","??","456454"));
        listReminder.add(new Reminder("392", "yes","??","456454"));
        listReminder.add(new Reminder("392", "yes","??","456454"));

    }

}