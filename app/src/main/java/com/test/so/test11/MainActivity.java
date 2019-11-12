package com.test.so.test11;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private SeekBar seekBar;
    private boolean isBasicSettings = true;

    public void setBasicSettings(boolean basicSettings) {
        isBasicSettings = basicSettings;
    }

    public boolean isBasicSettings() {
        return isBasicSettings;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*addNotification();
        addNotification();
        addNotification();
        seekBar = (SeekBar)findViewById(R.id.seekbar);
        seekBar.setProgress(4);
        seekBar.setMax(8);

        seekBar.setVisibility(View.VISIBLE);*/
        Intent intent = new Intent(this, TestUIActivity.class);
        startActivity(intent);
        finish();

    }

    private void addNotification() {
        int id = new Random().nextInt(100);
        String channelID = getPackageName();
        channelID = "tt" + id;
        String channelName = "test";

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (manager == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= 26) {
            NotificationChannel chan = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH);
            chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            manager.createNotificationChannel(chan);
        }

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, channelID);
        Notification notification = notificationBuilder.setOngoing(false).setPriority(NotificationCompat.PRIORITY_MIN)
                .setContentTitle("aaaatest " + id)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true)
                .build();
        manager.notify(id, notification);
    }
}
