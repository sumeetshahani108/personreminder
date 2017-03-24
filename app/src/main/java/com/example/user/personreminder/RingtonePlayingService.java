package com.example.user.personreminder;

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
            default :
                startId = 0 ;
                break;
        }

        if (!this.isRunning && startId == 1) {
            media_song = MediaPlayer.create(this, R.raw.aaye);
            media_song.start();
            this.isRunning = true ;
            this.startId = 0 ;
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
