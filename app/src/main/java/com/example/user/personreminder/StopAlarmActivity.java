package com.example.user.personreminder;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class StopAlarmActivity extends AppCompatActivity {

    AlarmManager alarm_manager ;
    PendingIntent pendingIntent ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop_alarm);
        Intent my_intent = new Intent(this, Alarm_Receiver.class);
        pendingIntent = PendingIntent.getBroadcast(StopAlarmActivity.this, 0, my_intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarm_manager.cancel(pendingIntent);
        my_intent.putExtra("extra", "alarm off");
        sendBroadcast(my_intent);
    }
}
