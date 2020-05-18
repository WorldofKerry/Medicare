package com.example.recyclerviewintablayout;

        import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;
        import android.widget.TextView;

        import androidx.annotation.NonNull;
        import androidx.recyclerview.widget.RecyclerView;

        import java.util.List;

public class ReminderRecyclerViewAdapter extends RecyclerView.Adapter<ReminderRecyclerViewAdapter.MyViewHolder> {


    Context mContext;
    List<Reminder> mData;

    public ReminderRecyclerViewAdapter(Context mContext, List<Reminder> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        view = LayoutInflater.from(mContext).inflate(R.layout.item_reminder,parent,false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.textView_Activity.setText(mData.get(position).getActivity());
        holder.textView_time.setText(mData.get(position).getRepeat() + ": " + mData.get(position).getTime());

        String icon = mData.get(position).getIcon();

        if (icon.equals("diet")) {
            holder.imageView_icon.setImageResource(R.drawable.ic_diet);
        } else if (icon.equals("pillsicon")) {
            holder.imageView_icon.setImageResource(R.drawable.ic_pillsicon);
        } else {
            holder.imageView_icon.setImageResource(R.drawable.ic_workout);
        }

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView textView_Activity;
        private TextView textView_time;
        private ImageView imageView_icon;

        public MyViewHolder(View itemView) {
            super(itemView);

            imageView_icon = (ImageView) itemView.findViewById(R.id.reminder_icon);
            textView_Activity = (TextView) itemView.findViewById(R.id.textViewActivity);
            textView_time = (TextView) itemView.findViewById(R.id.textViewTime);

        }
    }

}
