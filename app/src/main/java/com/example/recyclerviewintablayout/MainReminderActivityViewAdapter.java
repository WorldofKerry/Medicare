package com.example.recyclerviewintablayout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainReminderActivityViewAdapter extends RecyclerView.Adapter<MainReminderActivityViewAdapter.MyViewHolder> {

    Context mContext;
    List<MainReminderBuilder> mData;

    public MainReminderActivityViewAdapter(Context mContext, List<MainReminderBuilder> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        view = LayoutInflater.from(mContext).inflate(R.layout.item_main_reminder,parent,false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.textView_name.setText(mData.get(position).getName());
        holder.textView_days.setText(mData.get(position).getDays());
        holder.textView_timeStart.setText(mData.get(position).getTimeStart());
        holder.textView_timeEnd.setText(mData.get(position).getTimeEnd());

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView textView_name;
        private TextView textView_days;
        private TextView textView_timeStart;
        private TextView textView_timeEnd;

        public MyViewHolder(View itemView) {
            super(itemView);

            textView_name = (TextView) itemView.findViewById(R.id.name_main_reminder);
            textView_days = (TextView) itemView.findViewById(R.id.days_main_reminder);
            textView_timeStart = (TextView) itemView.findViewById(R.id.timeStart_main_reminder);
            textView_timeEnd = (TextView) itemView.findViewById(R.id.timeEnd_main_reminder);

        }
    }

}
