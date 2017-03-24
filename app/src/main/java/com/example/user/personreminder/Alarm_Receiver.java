package com.example.user.personreminder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by user on 24-03-2017.
 */

public class Alarm_Receiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        String get_your_string = intent.getExtras().getString("extra");
        Intent service_intent = new Intent(context, RingtonePlayingService.class);
        service_intent.putExtra("extra", get_your_string);
        context.startService(service_intent);
    }
}
