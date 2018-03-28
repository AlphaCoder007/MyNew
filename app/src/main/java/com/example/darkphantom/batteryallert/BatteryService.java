package com.example.darkphantom.batteryallert;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.example.darkphantom.batteryallert.utils.SongDataUtil;

import java.lang.ref.WeakReference;


public class BatteryService extends Service {

    public interface OnBroadcastListener {
        void onBroadcast(int percent);
    }

    private MainActivity mainActivity;

    private OnBroadcastListener onBroadcastListener;

    public BatteryService(){}

    public BatteryService(Activity activity) {

        WeakReference weakReference = new WeakReference<>(activity);
        mainActivity = (MainActivity) weakReference.get();

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            Intent intent1 = new Intent("BROADCAST_BATTERY_COST");

            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE,-1);
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL,-1);
            float percentage = (level/ (float) scale);



           int percent = (int)((percentage)*100);


           intent1.putExtra("BATTERY_PERCENT", percent);

           boolean batteryInformation = intent.getBooleanExtra(BatteryManager.EXTRA_PRESENT, false);
           if (batteryInformation) {

               int health = intent.getIntExtra(BatteryManager.EXTRA_HEALTH, -1);
               switch (health) {
                   case BatteryManager.BATTERY_HEALTH_COLD :
                       intent1.putExtra("BATTERY_HEALTH", "Cold");
                       Log.e("MY TAG","BATTERY STATUS = cold");
                       break;
                   case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
                       intent1.putExtra("BATTERY_HEALTH", "Over Voltage");
                       Log.e("MY TAG","BATTERY STATUS = over voltage");
                       break;
                   case  BatteryManager.BATTERY_HEALTH_DEAD:
                       intent1.putExtra("BATTERY_HEALTH", "Dead");
                       Log.e("MY TAG","BATTERY STATUS = dead");
                       break;
                   case BatteryManager.BATTERY_HEALTH_GOOD:
                       intent1.putExtra("BATTERY_HEALTH", "Good");
                       Log.e("MY TAG","BATTERY STATUS = good");
                       break;
                   case BatteryManager.BATTERY_HEALTH_OVERHEAT:
                       intent1.putExtra("BATTERY_HEALTH", "OverHeat");
                       Log.e("MY TAG","BATTERY STATUS = over heat");
                       break;
                   case BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE:
                       intent1.putExtra("BATTERY_HEALTH", "Failure");
                       Log.e("MY TAG","BATTERY STATUS = failure");
                       break;
                   default:  intent1.putExtra("BATTERY_HEALTH", "Unknown");
                       break;

               }

               int batteryPluggedStatus = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
               switch (batteryPluggedStatus) {
                   case BatteryManager.BATTERY_STATUS_DISCHARGING:
                       intent1.putExtra("BATTERY_PLUGGED_STATUS", false);
                       Log.e("MY TAG","BATTERY STATUS = discharging");
                       break;
                   case BatteryManager.BATTERY_STATUS_CHARGING:
                       intent1.putExtra("BATTERY_PLUGGED_STATUS", true);
                       Log.e("MY TAG","BATTERY STATUS = charging");
                       break;
                   default:
                       intent1.putExtra("BATTERY_PLUGGED_STATUS", false);
                       break;

               }

               intent1.putExtra("BATTERY_TECHNOLOGY", intent.getExtras().getString(BatteryManager.EXTRA_TECHNOLOGY));
               int batteryTemp = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0);
               if (batteryTemp > 0) {
                   float tempInDegree = ((float)batteryTemp)/10;
                   Log.e("MY TAG","BATTERY STATUS = temp "+tempInDegree);
                   intent1.putExtra("BATTERY_TEMPERATURE", tempInDegree);
               }

               int plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, 0);

               switch (plugged) {
                   case BatteryManager.BATTERY_PLUGGED_WIRELESS:
                       intent1.putExtra("BATTERY_PLUGGED_STATUS", "Wireless ");
                       Log.e("MY TAG","BATTERY STATUS = wireless");
                       break;

                   case BatteryManager.BATTERY_PLUGGED_USB:
                       intent1.putExtra("BATTERY_PLUGGED_STATUS", "USB");
                       Log.e("MY TAG","BATTERY STATUS = USB");
                       break;

                   case BatteryManager.BATTERY_PLUGGED_AC:
                       intent1.putExtra("BATTERY_PLUGGED_STATUS", "AC");
                       Log.e("MY TAG","BATTERY STATUS = AC");
                       break;

                   default:
                       intent1.putExtra("BATTERY_PLUGGED_STATUS", "UNKNOWN");
                       Log.e("MY TAG","BATTERY STATUS = DON'T KNOW");
                       break;
               }

               int voltage = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 0);
               intent1.putExtra("BATTERY_VOLTAGE", voltage);
               Log.e("MY TAG","BATTERY STATUS = Voltage"+ voltage);
           }



            LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent1);





               /* Intent intent1 = new Intent(MainActivity.this, Alarm.class);
                intent1.putExtra("songId", SongDataUtil.getSongId());
                intent1.putExtra("songName", SongDataUtil.getSongName());

                PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),0,intent1,0);
                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+3*1000,pendingIntent);
*/



        /*    if(percent <= 20){

                battery_image.setBackgroundResource(R.drawable.battery20);
                mood.setBackgroundResource(R.drawable.mood2);
                text.setText("Low");

                Log.d("MyNumber",""+percent);


            }


            else if(percent <= 40  && percent >= 21){

                battery_image.setBackgroundResource(R.drawable.battery40);
                mood.setBackgroundResource(R.drawable.mood1);
                text.setText("Below Average");
                Log.d("MyNumber",""+percent);

            }


            else if(percent <= 60 && percent >= 41){

                battery_image.setBackgroundResource(R.drawable.battery60);
                mood.setBackgroundResource(R.drawable.mood1);
                text.setText("Average");

                Log.d("MyNumber",""+percent);
            }



            else if(percent <= 80 && percent >= 61){

                battery_image.setBackgroundResource(R.drawable.battery80);
                mood.setBackgroundResource(R.drawable.mood1);
                text.setText("Above Average");
                Log.d("MyNumber",""+percent);

            }


            else{

                battery_image.setBackgroundResource(R.drawable.battery80);
                mood.setBackgroundResource(R.drawable.mood1);
                text.setText("Full");
                Log.d("MyNumber",""+percent);

            }


*/

        }
    };




    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("MY TAG", "I AM IN OnstartCommand");
        final IntentFilter iFilter = new IntentFilter();
        iFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        iFilter.addAction(Intent.ACTION_POWER_CONNECTED);
        iFilter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        registerReceiver(broadcastReceiver,iFilter);
        return super.onStartCommand(intent, flags, startId);
    }

    public void setOnBroadcastListener(OnBroadcastListener onBroadcastListener) {
        this.onBroadcastListener = onBroadcastListener;
    }

    @Override
    public void onDestroy() {

        unregisterReceiver(broadcastReceiver);
        Intent intent = new Intent("uk.ac.shef.oak.ActivityRecognition.RestartSensor");
        sendBroadcast(intent);
        super.onDestroy();

    }
}
