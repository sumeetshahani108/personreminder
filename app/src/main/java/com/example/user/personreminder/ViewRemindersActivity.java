package com.example.user.personreminder;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.user.personreminder.adapters.MainActivityRecyclerAdapter;
import com.example.user.personreminder.adapters.ViewRemindersActivityRecyclerAdapter;
import com.example.user.personreminder.data.ReminderList;
import com.example.user.personreminder.database.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class ViewRemindersActivity extends AppCompatActivity implements ViewRemindersActivityRecyclerAdapter.itemClickCallback {

    DatabaseHelper myDB;
    public RecyclerView recyclerView;
    public ViewRemindersActivityRecyclerAdapter adapter;

    List<ReminderList> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reminders);
        myDB = new DatabaseHelper(this);
        recyclerView = (RecyclerView) findViewById(R.id.recyler_view_2);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        Intent myIntent = getIntent();
        int intValue = myIntent.getIntExtra("contact_id", 1);
        Log.d("VIEW", intValue + "");

        Cursor res = myDB.getRemindersOfContact(intValue);

        if (res.getCount() == 0) {
            Log.d("ViewRemindersActivity", "Nothing");
        } else {
            while (res.moveToNext()) {
                ReminderList list = new ReminderList();
                list.setReminder_id(Integer.parseInt(res.getString(0)));
                list.setReminder_title(res.getString(1));
                list.setReminder_description(res.getString(2));
                list.setReminder_date(res.getString(7));
                list.setReminder_time(res.getString(8));
                list.setAlert_date(res.getString(9));
                list.setAlert_time(res.getString(10));
                data.add(list);
            }
        }

        adapter = new ViewRemindersActivityRecyclerAdapter(this, data);
        adapter.setItemClickCallback(this);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onItemClick(int p, String buttonName) {
        if (buttonName.equals("edit")) {
            editReminder(p);
        } else {
            deleteReminder(p);
        }


    }

    private void editReminder(int p) {
        Intent editIntent = new Intent(this, AddReminderActivity.class) ;
        editIntent.putExtra("calling_activity", ActivityConstants.EDIT_ACTIVITY) ;
        editIntent.putExtra("reminder_id", p);
        startActivity(editIntent);
    }

    private void deleteReminder(int p) {
        myDB = new DatabaseHelper(this);
        SQLiteDatabase sqLiteDatabase = myDB.getWritableDatabase();
        Cursor res1 = myDB.getReminder(sqLiteDatabase, p);
        if (res1.moveToFirst()) {
            int contact_id = res1.getInt(5);
            Cursor res2 = myDB.checkLastContact(sqLiteDatabase, contact_id);
            if (res2.getCount() == 1) {
                boolean bool1 = myDB.deleteReminder(sqLiteDatabase, p);
                boolean bool2 = myDB.deleteContact(sqLiteDatabase, contact_id);
                if (bool1 && bool2) {
                    finish();
                    overridePendingTransition(0, 0);
                    startActivity(getIntent());

                } else {
                }
            } else {
                boolean bool = myDB.deleteReminder(sqLiteDatabase, p);
                if (bool) {
                    finish();
                    overridePendingTransition(0, 0);
                    startActivity(getIntent());
                } else {

                }
            }
        } else {
            Log.d("ReminderAdapter", "error - reminder not in database");
        }
    }
}
