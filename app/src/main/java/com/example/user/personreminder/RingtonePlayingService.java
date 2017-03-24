package com.example.user.personreminder;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.security.Provider;
import java.util.List;
import java.util.Map;

/**
 * Created by user on 24-03-2017.
 */

public class RingtonePlayingService extends Service {

    MediaPlayer media_song ;
    int startId ;
    boolean isRunning ;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String state = intent.getExtras().getString("extra");

        assert state != null ;
        switch (state) {
            case "alarm on" :
                startId = 1 ;
                break;
            case "alarm off" :
                startId = 0 ;
                break ;
            default :
                startId = 0 ;
                break;
        }

        if (!this.isRunning && startId == 1) {
            media_song = MediaPlayer.create(this, R.raw.aaye);
            media_song.start();
            this.isRunning = true ;
            this.startId = 0 ;
            NotificationManager notify_manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            Intent intent_stop_alarm_activity = new Intent(this.getApplicationContext(), StopAlarmActivity.class);
            PendingIntent pending_intent_stop_alarm = PendingIntent.getActivity(this, 0, intent_stop_alarm_activity, 0);
            Notification notification_popup = new Notification.Builder(this)
                    .setContentTitle("An alarm is going off")
                    .setContentText("Click me!")
                    .setContentIntent(pending_intent_stop_alarm)
                    .setAutoCancel(true)
                    .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                    .build();

            notify_manager.notify(0, notification_popup);
        }
        else if (this.isRunning && startId == 0){
            media_song.stop();
            media_song.reset();
            this.isRunning = false ;
            this.startId = 0 ;
        }
        else if (!this.isRunning && startId == 0) {
            this.isRunning = false ;
            this.startId = 0;

        }
        else if (this.isRunning && startId == 1){
            this.isRunning = true;
            this.startId = 1;
        }
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.isRunning = false ;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
