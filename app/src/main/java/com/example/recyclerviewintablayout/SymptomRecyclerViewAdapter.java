package com.example.recyclerviewintablayout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

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
        holder.textView_time.setText(mData.get(position).getTime());

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView textView_name;
        private TextView textView_location;
        private TextView textView_level;
        private TextView textView_time;

        OnNoteListener onNoteListener;

        public MyViewHolder(View itemView, OnNoteListener onNoteListener) {
            super(itemView);

            textView_name = (TextView) itemView.findViewById(R.id.name_symptom);
            textView_location = (TextView) itemView.findViewById(R.id.location_symptom);
            textView_level = (TextView) itemView.findViewById(R.id.level_symptom);
            textView_time = (TextView) itemView.findViewById(R.id.time_symptom);

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

}
