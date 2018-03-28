package com.example.darkphantom.batteryallert;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.darkphantom.batteryallert.UI.RingtonSelectionActivity;
import com.example.darkphantom.batteryallert.model.SongModel;
import com.example.darkphantom.batteryallert.utils.SongDataUtil;
import com.example.darkphantom.batteryallert.utils.SonglistUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements BatteryService.OnBroadcastListener {


    ImageView battery_image,mood;
    TextView battery_level,text;
    float percentage;
    int percent = 0;

     EditText batteryNotification;
     Button notifyButton;
     int batteryNotificationLevel;
     private ArrayList<SongModel> songList = new ArrayList<>();
     private static final int REQUEST_MULTIPLE_PER=1;

     private BatteryService batteryService;
     private Intent intent;

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            Log.e("MY TAG","PERCENT IN RECEIVER = "+intent.getIntExtra("BATTERY_PERCENT", -1));

            percent = intent.getIntExtra("BATTERY_PERCENT", -1);

            if(percent <= 20){

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

            /*int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE,-1);
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL,-1);
             percentage = (level/ (float) scale);

             percent = (int)((percentage)*100);


            battery_level.setText(" "+percent);

            Log.d("percent",""+percent);

            if (batteryNotificationLevel == percent) {

                Intent intent1 = new Intent(MainActivity.this, Alarm.class);
                intent1.putExtra("songId", SongDataUtil.getSongId());
                intent1.putExtra("songName", SongDataUtil.getSongName());

                PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),0,intent1,0);
                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+3*1000,pendingIntent);

            }

           */

        }
    };




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);


        boolean check= checkAndRequestPermission();
        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)==
                PackageManager.PERMISSION_GRANTED && check) {
            initialFlow();
        }



    }

    private void initialFlow() {

        setContentView(R.layout.activity_main);

       final IntentFilter iFilter = new IntentFilter("BROADCAST_BATTERY_COST");
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(broadcastReceiver, iFilter);

        battery_image = findViewById(R.id.battery20);
        batteryNotification = findViewById(R.id.batteryNotification);
        mood = findViewById(R.id.mood);
        notifyButton = findViewById(R.id.notify_battery);

        final RadioButton radioButton = findViewById(R.id.testRadio);

        radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("MY TAG", "IS IT CHECKED "+radioButton.isChecked());
                radioButton.setChecked(false);
            }
        });



        battery_level = findViewById(R.id.batter_level);
        text = findViewById(R.id.mood_text);


        batteryService = new BatteryService(MainActivity.this);
        intent  = new Intent(getApplicationContext(), batteryService.getClass());

        if (!isMyServiceRunning(batteryService.getClass())) {
            startService(intent);
        }
        batteryService.setOnBroadcastListener(new BatteryService.OnBroadcastListener() {
            @Override
            public void onBroadcast(int percent) {

            }
        });

        notifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                BatteryAlarm alarm = new BatteryAlarm(batteryNotificationLevel);
                getApplicationContext().registerReceiver(alarm,iFilter);

                batteryNotificationLevel = Integer.parseInt(batteryNotification.getText().toString());
                Intent intent = new Intent(MainActivity.this, RingtonSelectionActivity.class);
                startActivity(intent);*/

                String manufacturer = "xiaomi";
                if (manufacturer.equalsIgnoreCase(android.os.Build.MANUFACTURER)) {
                    //this will open auto start screen where user can enable permission for your app
                    Intent intent1 = new Intent();
                    intent1.setComponent(new ComponentName("com.miui.securitycenter", "com.miui.permcenter.autostart.AutoStartManagementActivity"));
                    startActivity(intent1);
                }
            }
        });

    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.e("isMyServiceRunning?", true+"");
                return true;
            }
        }
        Log.i ("isMyServiceRunning?", false+"");
        return false;
    }

    private boolean checkAndRequestPermission() {
        int permissionMsg = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int locationPermission = ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.READ_PHONE_STATE);
        List<String> listPermissionNeeded = new ArrayList<>();
        if(locationPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionNeeded.add(Manifest.permission.READ_PHONE_STATE);
        }
        if(permissionMsg != PackageManager.PERMISSION_GRANTED) {
            listPermissionNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if(!listPermissionNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(MainActivity.this, listPermissionNeeded
                            .toArray(new String[listPermissionNeeded.size()]),
                    REQUEST_MULTIPLE_PER);
        }
        return true;
    }



    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_MULTIPLE_PER:
                Map<String, Integer> perms = new HashMap<>();

                perms.put(Manifest.permission.READ_PHONE_STATE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.READ_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                if (grantResults.length >0) {
                    for (int i=0; i< permissions.length;i++)
                        perms.put(permissions[i],grantResults[i]);

                    if(perms.get(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED && perms.get(Manifest
                            .permission.READ_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED) {
                        //Normal flow
                        Log.e("MY TAG","ALL PERMISSION SELECTED");
                        initialFlow();
                    }
                    else {
                        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                                || ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.READ_PHONE_STATE)) {
                            showDialogOk("we need to access file manager for getting the songs ", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    switch (which) {
                                        case DialogInterface.BUTTON_POSITIVE:
                                            checkAndRequestPermission();
                                            break;
                                        case DialogInterface.BUTTON_NEGATIVE:
                                            break;
                                    }
                                }
                            });
                        }
                    }
                }
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void showDialogOk(String s,DialogInterface.OnClickListener onClickListener) {
        new AlertDialog.Builder(MainActivity.this)
                .setMessage(s)
                .setPositiveButton("ok",onClickListener)
                .setNegativeButton("cancel",onClickListener)
                .create()
                .show();
    }

    @Override
    protected void onDestroy() {
        stopService(intent);
        super.onDestroy();
    }

    @Override
    public void onBroadcast(int percent) {
        Log.e("MY TAG","PERCENT "+percent);
    }

    public static void batteryStatusChanged(int percent) {

        /*


        */
    }


}

