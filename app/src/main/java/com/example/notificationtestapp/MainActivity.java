package com.example.notificationtestapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.*;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createNotificationChannel();

        Intent alarmIntent = new Intent(this, ReminderBroadcast.class);
        PendingIntent alarmPendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);

        final AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        // Get the time for the first alarm (10 am)
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTimeInMillis(System.currentTimeMillis());
        calendar1.set(Calendar.HOUR_OF_DAY, 10);
        calendar1.set(Calendar.MINUTE, 0);


        Log.d("Tag", "First alarm time: " + calendar1.getTime().toString());

        // Set the alarm to start at approximately 10:00 a.m. and repeat daily
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar1.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, alarmPendingIntent);


        // Get the time for the second alarm (1 pm)
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTimeInMillis(System.currentTimeMillis());
        calendar2.set(Calendar.HOUR_OF_DAY, 13);
        calendar2.set(Calendar.MINUTE, 0);

        Log.d("Tag", "Second alarm time: " + calendar2.getTime().toString());

        // Set the second alarm to start at approximately 1:00 p.m. and repeat daily
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar2.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, alarmPendingIntent);


        // Get the time for the third alarm (4 pm)
        Calendar calendar3 = Calendar.getInstance();
        calendar3.setTimeInMillis(System.currentTimeMillis());
        calendar3.set(Calendar.HOUR_OF_DAY, 16);
        calendar3.set(Calendar.MINUTE, 0);

        Log.d("Tag", "Second alarm time: " + calendar3.getTime().toString());

        // Set the second alarm to start at approximately 4:00 p.m. and repeat daily
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar3.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, alarmPendingIntent);


        //Do high priority notification with activity
//        Intent fullScreenIntent = new Intent(this, Activity2.class);
//        PendingIntent fullScreenPendingIntent = PendingIntent.getActivity(this, 0,
//                fullScreenIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//        final NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "EmmeM")
//                .setSmallIcon(R.drawable.ic_stat_name)
//                .setContentTitle("My notification")
//                .setContentText("Hello World!")
//                .setPriority(NotificationCompat.PRIORITY_HIGH)
//                .setFullScreenIntent(fullScreenPendingIntent, true);
//
//        final NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        Button button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                // notificationId is a unique int for each notification that you must define
                //notificationManager.notify(100, builder.build());

                //
                Toast.makeText(MainActivity.this, "Reminder Set", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(MainActivity.this, ReminderBroadcast.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, 0);

                //AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

                // Get time + time when notification goes off
                long timeAtButtonClick = System.currentTimeMillis();
                long tenSecondsInMillis = 1000*10;

                Log.d("Tag", "Button Click");

                alarmManager.set(AlarmManager.RTC_WAKEUP,
                        timeAtButtonClick + tenSecondsInMillis, pendingIntent);
            }
        });

//        // Create an explicit intent for an Activity in your app
//        Intent intent = new Intent(this, Activity2.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
//
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "EmmeM")
//                .setSmallIcon(R.drawable.ic_stat_name)
//                .setContentTitle("My notification")
//                .setContentText("Hello World!")
//                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//                // Set the intent that will fire when the user taps the notification
//                .setContentIntent(pendingIntent)
//                .setAutoCancel(true);

    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("EmmeM", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}