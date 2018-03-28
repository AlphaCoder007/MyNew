package com.example.darkphantom.batteryallert;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.util.Log;

import com.example.darkphantom.batteryallert.utils.SongDataUtil;

import static android.content.Context.ALARM_SERVICE;


public class BatteryAlarm extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {


        Log.e("MY TAG","I AM IN THIS");

        Intent intent1 = new Intent(context, BatteryService.class);
        context.startService(intent1);

       /* int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE,-1);
        int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL,-1);
        float percentage = (level/ (float) scale);
        int percent = (int)((percentage)*100);

       if (percent == batteryLevel) {
           Intent intent1 = new Intent(context, Alarm.class);
           intent1.putExtra("songId", SongDataUtil.getSongId());
           intent1.putExtra("songName", SongDataUtil.getSongName());

           PendingIntent pendingIntent = PendingIntent.getBroadcast(context,0,intent1,0);
           AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
           alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+3*1000,pendingIntent);
       }*/

    }
}
