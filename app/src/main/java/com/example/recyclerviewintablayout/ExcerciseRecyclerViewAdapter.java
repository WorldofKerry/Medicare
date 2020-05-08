package com.example.recyclerviewintablayout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

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
        holder.textView_calories.setText(mData.get(position).getCalories());
        holder.textView_time.setText(mData.get(position).getTime());
        holder.textView_duration.setText(mData.get(position).getDuration());
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

        OnNoteListener onNoteListener;

        public MyViewHolder(View itemView, OnNoteListener onNoteListener) {
            super(itemView);

            textView_type = (TextView) itemView.findViewById(R.id.excercise_type);
            textView_time = (TextView) itemView.findViewById(R.id.excercise_time);
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

}
