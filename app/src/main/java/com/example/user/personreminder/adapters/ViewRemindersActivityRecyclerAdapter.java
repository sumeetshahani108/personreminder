package com.example.user.personreminder.adapters;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.personreminder.R;
import com.example.user.personreminder.ViewRemindersActivity;
import com.example.user.personreminder.data.ReminderList;
import com.example.user.personreminder.database.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 19-03-2017.
 */

public class ViewRemindersActivityRecyclerAdapter extends RecyclerView.Adapter<ViewRemindersActivityRecyclerAdapter.DataHolder> {

    private LayoutInflater inflater;
    private List<ReminderList> data = new ArrayList<>();
    private itemClickCallback itemClickCallback;
    DatabaseHelper myDB;

    public ViewRemindersActivityRecyclerAdapter(Context c, List<ReminderList> listData) {
        inflater = LayoutInflater.from(c);
        this.data = listData;
    }

    public ViewRemindersActivityRecyclerAdapter() {
        super();
    }

    public interface itemClickCallback {
        void onItemClick(int p, String name);
    }

    public void setItemClickCallback(final itemClickCallback itemClickCallback) {
        this.itemClickCallback = itemClickCallback;
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
        View view = inflater.inflate(R.layout.activity_view_cards, parent, false);
        DataHolder dataHolder = new DataHolder(view);
        return dataHolder;
    }

    class DataHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title;
        TextView description;
        TextView date;
        TextView time;
        ImageButton editReminder;
        ImageButton deleteReminder;

        public DataHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.reminder_title);
            description = (TextView) itemView.findViewById(R.id.reminder_description);
            date = (TextView) itemView.findViewById(R.id.reminder_date);
            time = (TextView) itemView.findViewById(R.id.reminder_time);
            editReminder = (ImageButton) itemView.findViewById(R.id.editReminder);
            deleteReminder = (ImageButton) itemView.findViewById(R.id.deleteReminder);
            editReminder.setOnClickListener(this);
            deleteReminder.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.editReminder:
                    ReminderList itemEdit = data.get(getAdapterPosition());
                    itemClickCallback.onItemClick(itemEdit.getReminder_id(), "edit");
                    break;
                case R.id.deleteReminder:
                    ReminderList itemDelete = data.get(getAdapterPosition());
                    itemClickCallback.onItemClick(itemDelete.getReminder_id(), "delete");
                    /*myDB = new DatabaseHelper(v.getContext());
                    SQLiteDatabase sqLiteDatabase = myDB.getWritableDatabase();
                    Cursor res1 = myDB.checkLastReminder(sqLiteDatabase, item.getReminder_id());
                    if (res1.moveToFirst()) {
                        int contact_id = res1.getInt(5);
                        Cursor res2 = myDB.checkLastContact(sqLiteDatabase, contact_id);
                        if (res2.getCount() == 1) {
                            boolean bool1 = myDB.deleteReminder(sqLiteDatabase, item.getReminder_id());
                            boolean bool2 = myDB.deleteContact(sqLiteDatabase, contact_id);
                            if (bool1 && bool2) {

                            }  else {
                            }
                        } else {
                            boolean bool = myDB.deleteReminder(sqLiteDatabase, item.getReminder_id());
                            if (bool) {

                            } else {

                            }
                        }
                    } else {
                        Log.d("ReminderAdapter", "inside 1st else");
                    }*/
                    break;
            }
        }
    }
}
