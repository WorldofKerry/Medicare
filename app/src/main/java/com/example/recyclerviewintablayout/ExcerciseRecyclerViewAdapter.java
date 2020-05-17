package com.example.recyclerviewintablayout;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class ExcerciseRecyclerViewAdapter extends RecyclerView.Adapter<ExcerciseRecyclerViewAdapter.MyViewHolder> {


    Context mContext;
    List<Excercise> mData;
    private OnNoteListener mOnNoteListener;
    private String[] arrayType;


    public ExcerciseRecyclerViewAdapter(Context mContext, List<Excercise> mData, OnNoteListener onNoteListener) {
        this.mContext = mContext;
        this.mData = mData;

        this.mOnNoteListener = onNoteListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        view = LayoutInflater.from(mContext).inflate(R.layout.item_excercise,parent,false);
        MyViewHolder viewHolder = new MyViewHolder(view, mOnNoteListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        arrayType = mContext.getResources().getStringArray(R.array.typeOfWorkout);
        holder.textView_calories.setText(mContext.getResources().getString(R.string.calories,mData.get(position).getCalories()));
        String[] dateTime = timeText(mData.get(position).getTime()).split("[+]");

        if (dateTime.length == 2) {
            holder.textView_date.setText(dateTime[0]);
            holder.textView_time.setText(dateTime[1]);
        }
        holder.textView_duration.setText(mContext.getResources().getString(R.string.duration_minutes,mData.get(position).getDuration()));
        holder.textView_type.setText(arrayType[Integer.parseInt(mData.get(position).getType())]);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView textView_type;
        private TextView textView_calories;
        private TextView textView_duration;
        private TextView textView_time;
        private TextView textView_date;

        OnNoteListener onNoteListener;

        public MyViewHolder(View itemView, OnNoteListener onNoteListener) {
            super(itemView);

            textView_type = (TextView) itemView.findViewById(R.id.excercise_type);
            textView_time = (TextView) itemView.findViewById(R.id.excercise_time);
            textView_date = (TextView) itemView.findViewById(R.id.excercise_date);
            textView_duration = (TextView) itemView.findViewById(R.id.excercise_duration);
            textView_calories = (TextView) itemView.findViewById(R.id.excercise_calories);

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

        Log.d("Time Intervals", Arrays.toString(timeIntervals));
        String monthText = months[Integer.parseInt(timeIntervals[1]) - 1];

        int hour = Integer.parseInt(timeIntervals[3]);
        if(hour == 0) hour = 24;
        boolean isPM = (hour > 12);
        if(isPM) hour -= 12;
        int minute = Integer.parseInt(timeIntervals[4]);

        String displayTime = hour + ":" + String.format(Locale.CANADA,"%02d", minute);
        displayTime += isPM ? " PM" : " AM";

        return monthText + " " + timeIntervals[2] + ", " + timeIntervals[0] + "+" + displayTime;
    }
}
