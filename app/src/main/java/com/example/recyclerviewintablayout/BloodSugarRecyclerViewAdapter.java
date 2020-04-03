package com.example.recyclerviewintablayout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BloodSugarRecyclerViewAdapter extends RecyclerView.Adapter<BloodSugarRecyclerViewAdapter.MyViewHolder> {


    Context mContext;
    List<BloodSugar> mData;

    public BloodSugarRecyclerViewAdapter(Context mContext, List<BloodSugar> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        view = LayoutInflater.from(mContext).inflate(R.layout.item_blood_sugar,parent,false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.textView_level.setText(mData.get(position).getLevel());
        holder.textView_time.setText(mData.get(position).getTime());
        holder.textView_notes.setText(mData.get(position).getNotes());
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

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView textView_level;
        private TextView textView_time;
        private TextView textView_notes;
        private TextView textView_safe;

        public MyViewHolder(View itemView) {
            super(itemView);

            textView_level = (TextView) itemView.findViewById(R.id.level_blood_sugar);
            textView_time = (TextView) itemView.findViewById(R.id.time_blood_sugar);
            textView_notes = (TextView) itemView.findViewById(R.id.notes_blood_sugar);
            textView_safe = (TextView) itemView.findViewById(R.id.safe_blood_sugar);
        
        }
    }

}
