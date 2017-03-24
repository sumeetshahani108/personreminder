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

public class ViewRemindersActivity extends AppCompatActivity implements View.OnClickListener{

    DatabaseHelper myDB;
    public RecyclerView recyclerView;
    public ViewRemindersActivityRecyclerAdapter adapter ;

    ImageButton editReminder ;
    ImageButton deleteReminder ;

    List<ReminderList> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reminders);
        myDB = new DatabaseHelper(this) ;
        recyclerView = (RecyclerView)findViewById(R.id.recyler_view_2);
        recyclerView.setHasFixedSize(true);

        editReminder = (ImageButton) findViewById(R.id.editReminder) ;
        deleteReminder = (ImageButton) findViewById(R.id.deleteReminder) ;

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        Intent myIntent = getIntent();
        int intValue = myIntent.getIntExtra("contact_id", 1);
        Log.d("VIEW",intValue+"");

        Cursor res = myDB.getReminders(intValue);



        if ( res.getCount() == 0){
            Log.d("ViewRemindersActivity", "Nothing");
        }else {
            while (res.moveToNext()) {
                ReminderList list = new ReminderList() ;
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
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.editReminder:
                break;
            case R.id.deleteReminder:
                DeleteReminder();
                break;
        }
    }

    private void DeleteReminder() {
        SQLiteDatabase sqLiteDatabase = myDB.getWritableDatabase() ;
        boolean bool = myDB.deleteReminder(sqLiteDatabase, 2);
        if(bool){
            Toast.makeText(this, "deleted", Toast.LENGTH_LONG).show();
        }
    }
}
