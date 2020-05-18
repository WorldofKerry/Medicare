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

public class BloodSugarRecyclerViewAdapter extends RecyclerView.Adapter<BloodSugarRecyclerViewAdapter.MyViewHolder>{


    Context mContext;
    List<BloodSugar> mData;
    private OnNoteListener mOnNoteListener;

    public BloodSugarRecyclerViewAdapter(Context mContext, List<BloodSugar> mData, OnNoteListener mOnNoteListener) {
        this.mContext = mContext;
        this.mData = mData;

        this.mOnNoteListener = mOnNoteListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        view = LayoutInflater.from(mContext).inflate(R.layout.item_blood_sugar,parent,false);
        MyViewHolder viewHolder = new MyViewHolder(view, mOnNoteListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.textView_level.setText(mContext.getResources().getString(R.string.mgdl,mData.get(position).getLevel()));
        String[] dateTime = timeText(mData.get(position).getTime()).split("[+]");

        if (dateTime.length == 2) {
            holder.textView_date.setText(dateTime[0]);
            holder.textView_time.setText(dateTime[1]);
        }
        if (mData.get(position).getSafe()) {
            holder.textView_safe.setText(R.string.safe);
        } else {
            holder.textView_safe.setText(R.string.unsafe);
        }


    }



    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView textView_level;
        private TextView textView_time;
        private TextView textView_date;
        private TextView textView_safe;

        OnNoteListener onNoteListener;


        public MyViewHolder(View itemView, OnNoteListener onNoteListener) {
            super(itemView);

            textView_level = (TextView) itemView.findViewById(R.id.level_blood_sugar);
            textView_time = (TextView) itemView.findViewById(R.id.time_blood_sugar);
            textView_date = (TextView) itemView.findViewById(R.id.date_blood_sugar);
            textView_safe = (TextView) itemView.findViewById(R.id.safe_blood_sugar);

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
        int minute = Integer.parseInt(timeIntervals[4]);

        String displayTime = hour + ":" + String.format(Locale.CANADA,"%02d", minute);
        displayTime += isPM ? " PM" : " AM";

        return monthText + " " + timeIntervals[2] + ", " + timeIntervals[0] + "+" + displayTime;
    }
}



