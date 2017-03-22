package com.example.user.personreminder;

import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.user.personreminder.adapters.MainActivityRecyclerAdapter;
import com.example.user.personreminder.data.ContactList;
import com.example.user.personreminder.database.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainActivityRecyclerAdapter.itemClickCallback{

    DatabaseHelper myDB;
    FloatingActionButton addNewReminderButton ;
    private RecyclerView recyclerView;
    private MainActivityRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDB = new DatabaseHelper(this) ;

        addNewReminderButton = (FloatingActionButton) findViewById(R.id.myFAB) ;

        addNewReminderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newReminderIntent = new Intent(MainActivity.this, AddReminderActivity.class) ;
                startActivity(newReminderIntent) ;
            }
        });

        recyclerView = (RecyclerView)findViewById(R.id.recyler_view);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        Cursor res = myDB.getContactsOfReminders();

        List<ContactList> data = new ArrayList<>();

        if (res.getCount() == 0) {
            Log.d("MainActivity", "Nothing");
        }else {

            while (res.moveToNext()){
                ContactList list = new ContactList();
                Log.d("MainActivity", res.getString(0));
                list.setId(Integer.parseInt(res.getString(0)));
                list.setName(res.getString(1));
                list.setNumber(res.getString(2));
                data.add(list);
            }
        }
        
        adapter = new MainActivityRecyclerAdapter(this, data);
        adapter.setItemClickCallback(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Cursor res = myDB.getContactsOfReminders();
        List<ContactList> data = new ArrayList<>();

        if (res.getCount() == 0) {
            Log.d("MainActivity", "Nothing");
        }else {

            while (res.moveToNext()){
                ContactList list = new ContactList();
                Log.d("MainActivity", res.getString(1));
                list.setId(Integer.parseInt(res.getString(0)));
                list.setName(res.getString(1));
                list.setNumber(res.getString(2));
                data.add(list);
            }
        }
        adapter = new MainActivityRecyclerAdapter(this, data);
        adapter.setItemClickCallback(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(int p) {
        Intent i = new Intent( this , ViewRemindersActivity.class);
        i.putExtra("contact_id", p);
        startActivity(i);
    }
}
