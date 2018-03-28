package com.example.darkphantom.batteryallert;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.darkphantom.batteryallert.utils.DatabaseQuery;

import static android.content.Context.NOTIFICATION_SERVICE;


public class Alarm extends BroadcastReceiver {


    Notification nm;
    private AudioManager audioManager;
    private Context context;
    private MediaPlayer mediaPlayer = new MediaPlayer();
    private static final String MyOnClick = "myOnClickTag";

    @Override
    public void onReceive(Context context, Intent intent) {

        DatabaseQuery databaseQuery = new DatabaseQuery();

        databaseQuery.insertNewData(null,0,context);


        RemoteViews remoteViews;

        if (MyOnClick.equals(intent.getAction())){
            //your onClick action is here
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
        }


        Toast.makeText(context, "This don message guys", Toast.LENGTH_SHORT).show();

        Intent notificationIntent = new Intent(context, MainActivity.class);
        notificationIntent.setAction("Action_Event");
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);
        this.context = context;
        remoteViews = new  RemoteViews(context.getPackageName(), R.layout.notification_bar);

       // rm.setImageViewResource(R.id.status_bar_album_art, R.drawable.football);
       /* nm = new Notification.Builder(context).build();
        nm.contentView = remoteViews;
        nm.bigContentView = rm;
        nm.icon = R.drawable.music;
        nm.priority = Notification.PRIORITY_MAX;
        nm.contentIntent = pendingIntent;
        //context.startForeground(Constants.NotificationID.FOREGROUND_SERVICE, nm);
        NotificationManager notificationmanager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        notificationmanager.notify(110, nm);*/

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setContent(remoteViews)
                        .setSmallIcon(R.drawable.ic_aspect_ratio)
                        .setContentTitle("You was set the battery notification")
                         .setContentInfo("Thankyou for choosing me");


        mBuilder.setContentIntent(pendingIntent);
        remoteViews.setOnClickPendingIntent(R.id.stopMusic,
                getPendingSelfIntent(context, MyOnClick));

        int mNotificationId = 001;
// Gets an instance of the NotificationManager service
        NotificationManager mNotifyMgr =
                (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
// Builds the notification and issues it.
        mNotifyMgr.notify(mNotificationId, mBuilder.build());

        /*try {
            Uri path = Uri.parse("android.resource://"+context.getPackageName()+"/raw/sound.mp3");
            // The line below will set it as a default ring tone replace
            // RingtoneManager.TYPE_RINGTONE with RingtoneManager.TYPE_NOTIFICATION
            // to set it as a notification tone
            RingtoneManager.setActualDefaultRingtoneUri(
                    context, RingtoneManager.TYPE_RINGTONE,
                    path);
            Ringtone r = RingtoneManager.getRingtone(context, path);
            r.play();

           //requestAudioFocus();
            initMediaplayer();

        }
        catch (Exception e) {
            e.printStackTrace();


        }*/

        long songId = intent.getLongExtra("songId", -1);
        if (songId != -1) {
            playSong(songId);
        }
    }

    private void playSong(long id) {

        if (!mediaPlayer.isPlaying()) {

            Uri uri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id);
            //  Uri uri = Uri.fromFile(new File(cuurent.getPath()));
            //mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            try {
                mediaPlayer.setDataSource(context, uri);
                mediaPlayer.prepare();
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mediaPlayer.start();
                        // Toast.makeText(c, "We are in play media", Toast.LENGTH_SHORT).show();
                        // mediaPlayer.setLooping(true);
                        //changeNotificationState();
                    }
                });

            } catch (Exception e) {
                // Log.e("MY TAG", "Error in the setDataSource==" + e.toString());
            }
        }
        else {
            mediaPlayer.reset();

            Uri muri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id);
            //  Uri uri = Uri.fromFile(new File(cuurent.getPath()));
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            try {
                mediaPlayer.setDataSource(context, muri);
                mediaPlayer.prepare();
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mediaPlayer.start();
                    }
                });
            } catch (Exception e) {
                //Log.e("MY TAG", "Error in the setDataSource==" + e.toString());
            }

        }

    }

    protected PendingIntent getPendingSelfIntent(Context context, String action) {
        Intent intent = new Intent(context, getClass());
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }

    /*private void initMediaplayer() {
        m = MediaPlayer.create(context, R.raw.sound);
        m.start();
    }*/

   /* private boolean requestAudioFocus() {

        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        int result = audioManager.requestAudioFocus(this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
        if (result == AudioManager.AUDIOFOCUS_GAIN) {
            return true;
        }

        return false;
    }*/


   /* @Override
    public void onAudioFocusChange(int focusChange) {

        switch (focusChange) {
            case AudioManager.AUDIOFOCUS_GAIN:
                // Toast.makeText(getApplicationContext(), "we are get the audio focus", Toast.LENGTH_SHORT).show();
                if (m==null) {
                    initMediaplayer();
                }

                break;
            case AudioManager.AUDIOFOCUS_LOSS:
                // Toast.makeText(getApplicationContext(), "we are loss the audio focus", Toast.LENGTH_SHORT).show();
                if (m!= null && m.isPlaying())
                    m.stop();

                break;
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                // Toast.makeText(getApplicationContext(), "we are Loss transisit the audio focus", Toast.LENGTH_SHORT).show();

                break;
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                // Toast.makeText(getApplicationContext(), "we are Duck the audio focus", Toast.LENGTH_SHORT).show();
                if (m!= null && m.isPlaying())
                    m.setVolume(0.1f, 0.1f);
                break;
        }
    }*/
}
