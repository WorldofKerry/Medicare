package com.example.recyclerviewintablayout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Locale;

public class SymptomRecyclerViewAdapter extends RecyclerView.Adapter<SymptomRecyclerViewAdapter.MyViewHolder> {

    Context mContext;
    List<Symptom> mData;
    private OnNoteListener mOnNoteListener;
    private String[] arrayLocation;

    public SymptomRecyclerViewAdapter(Context mContext, List<Symptom> mData, OnNoteListener onNoteListener) {
        this.mContext = mContext;
        this.mData = mData;
        this.mOnNoteListener = onNoteListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(mContext).inflate(R.layout.item_symptom,parent,false);
        MyViewHolder viewHolder = new MyViewHolder(view, mOnNoteListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        arrayLocation = mContext.getResources().getStringArray(R.array.Location);
        holder.textView_name.setText(mData.get(position).getName());
        holder.textView_location.setText(arrayLocation[Integer.parseInt(mData.get(position).getLocation())]);
        holder.textView_level.setText("Level: " + mData.get(position).getLevel());

        String[] dateTime = timeText(mData.get(position).getTime()).split("[+]");

        if (dateTime.length == 2) {
            holder.textView_date.setText(dateTime[0]);
            holder.textView_time.setText(dateTime[1]);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView textView_name;
        private TextView textView_location;
        private TextView textView_level;
        private TextView textView_date;
        private TextView textView_time;

        OnNoteListener onNoteListener;

        public MyViewHolder(View itemView, OnNoteListener onNoteListener) {
            super(itemView);

            textView_name = itemView.findViewById(R.id.name_symptom);
            textView_location = itemView.findViewById(R.id.location_symptom);
            textView_level = itemView.findViewById(R.id.level_symptom);
            textView_date = itemView.findViewById(R.id.date_symptom);
            textView_time = itemView.findViewById(R.id.time_symptom);

            this.onNoteListener = onNoteListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onNoteListener.onNoteClick(getAdapterPosition());
        }
    }

    public interface OnNoteListener {
        void onNoteClick(int position);
    }


    private static final String[] months = {"Jan.", "Feb.", "Mar.", "Apr.", "May", "June", "July", "Aug.", "Sept.", "Oct.", "Nov.", "Dec."};

    private String timeText(String time) {
        if(time == null) return "null";
        String[] timeIntervals = time.split("[-+:]");
        if(timeIntervals.length <= 4) return "Invalid time format";

        String monthText = months[Integer.parseInt(timeIntervals[1]) - 1];

        int hour = Integer.parseInt(timeIntervals[3]);
        if(hour == 0) hour = 24;
        boolean isPM = (hour > 12);
        if(isPM) hour -= 12;
        if(hour == 12) isPM = !isPM;
        int minute = Integer.parseInt(timeIntervals[4]);

        String displayTime = hour + ":" + String.format(Locale.CANADA,"%02d", minute);
        displayTime += isPM ? " PM" : " AM";

        return monthText + " " + timeIntervals[2] + ", " + timeIntervals[0] + "+" + displayTime;
    }
}
