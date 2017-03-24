package com.example.user.personreminder.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.personreminder.R;
import com.example.user.personreminder.ViewRemindersActivity;
import com.example.user.personreminder.data.ReminderList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 19-03-2017.
 */

public class ViewRemindersActivityRecyclerAdapter extends RecyclerView.Adapter<ViewRemindersActivityRecyclerAdapter.DataHolder> {

    private LayoutInflater inflater;
    private List<ReminderList> data = new ArrayList<>();

    public ViewRemindersActivityRecyclerAdapter(Context c, List<ReminderList> listData){
        inflater = LayoutInflater.from(c);
        this.data = listData ;
    }

    public ViewRemindersActivityRecyclerAdapter() {
        super();
    }

    @Override
    public void onBindViewHolder(DataHolder holder, int position) {
        ReminderList item = data.get(position);
        holder.title.setText(item.getReminder_title());
        holder.description.setText(item.getReminder_description());
        holder.date.setText(item.getReminder_date());
        holder.time.setText(item.getReminder_time());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public ViewRemindersActivityRecyclerAdapter.DataHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.activity_view_cards,parent,false);
        DataHolder dataHolder = new DataHolder(view);
        return dataHolder;
    }

    class DataHolder extends RecyclerView.ViewHolder{

        TextView title ;
        TextView description ;
        TextView date ;
        TextView time ;

        public DataHolder(View itemView){
            super(itemView);
            title = (TextView)itemView.findViewById(R.id.reminder_title);
            description = (TextView)itemView.findViewById(R.id.reminder_description);
            date = (TextView)itemView.findViewById(R.id.reminder_date);
            time = (TextView)itemView.findViewById(R.id.reminder_time);
        }
    }
}
