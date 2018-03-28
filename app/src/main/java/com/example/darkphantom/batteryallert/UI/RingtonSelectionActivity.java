package com.example.darkphantom.batteryallert.UI;

import android.arch.persistence.room.Room;
import android.content.ContentUris;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import com.example.darkphantom.batteryallert.R;
import com.example.darkphantom.batteryallert.adapter.SongAdapter;
import com.example.darkphantom.batteryallert.model.BatteryAlarmModel;
import com.example.darkphantom.batteryallert.model.SongModel;
import com.example.darkphantom.batteryallert.utils.DatabaseAcess;
import com.example.darkphantom.batteryallert.utils.SongDataUtil;

import java.util.ArrayList;
import java.util.List;

public class RingtonSelectionActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<SongModel> songList = new ArrayList<>();
    private static  DatabaseAcess myDatabase;
    private static MediaPlayer mediaPlayer = new MediaPlayer();
    private Button selectButton;
    private long songId;
    private String songName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rington_selection);

        listView = findViewById(R.id.songlist);
        selectButton = findViewById(R.id.songSelectButton);
        myDatabase = Room.databaseBuilder(getApplicationContext(),
                DatabaseAcess.class, "database-name").build();
        getSongList();
        SongAdapter songAdapter = new SongAdapter(songList, RingtonSelectionActivity.this);

        songAdapter.setOnLayoutClickListener(new SongAdapter.OnLayoutClickListener() {
            @Override
            public void onClick(long id, String song) {

                 songId = id;
                 songName = song;
                if (!mediaPlayer.isPlaying()) {

                    Uri uri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id);
                    //  Uri uri = Uri.fromFile(new File(cuurent.getPath()));
                    //mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    try {
                        mediaPlayer.setDataSource(getApplicationContext(), uri);
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
                        mediaPlayer.setDataSource(getApplicationContext(), muri);
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
        });

        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SongDataUtil.setSongId(songId);
                SongDataUtil.setSongName(songName);
                onBackPressed();

            }
        });

        listView.setAdapter(songAdapter);

        new DatabaseWorker().execute();

    }

    public void getSongList() {
        //retrieve song info
        Uri musicUri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor musicCursor = getContentResolver().query(musicUri, null,null, null, MediaStore.MediaColumns.TITLE);
        if(musicCursor!=null && musicCursor.moveToFirst()){
            //get columns
            int titleColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media.TITLE);
            int idColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media._ID);
            int artistColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media.ARTIST);
            int pathColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media.DATA);
            int albumColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM);
            int albumIdIndex = musicCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID);
            int durationIndex = musicCursor.getColumnIndex(MediaStore.Audio.Media.DURATION);
            //add songs to list
            do {
                long thisId = musicCursor.getLong(idColumn);
                long albumId = musicCursor.getLong(albumIdIndex);
                String thisTitle = musicCursor.getString(titleColumn);
                String thisArtist = musicCursor.getString(artistColumn);
                String songPath = musicCursor.getString(pathColumn);
                String album = musicCursor.getString(albumColumn);
                long duration = musicCursor.getLong(durationIndex);
                songList.add(new SongModel(thisId, thisTitle, thisArtist,songPath,albumId, album, duration));
            }
            while (musicCursor.moveToNext());

            musicCursor.close();
        }

    }

   static class DatabaseWorker extends AsyncTask<Void,Void, Void> {


        @Override
        protected Void doInBackground(Void... voids) {


            BatteryAlarmModel entity = new BatteryAlarmModel();
            entity.setBatteryLevel(70);
            entity.setSongName("KUMAR");
            BatteryAlarmModel batteryAlarmModel = new BatteryAlarmModel();
            batteryAlarmModel.setSongName("ANKIT KUMAR");
            batteryAlarmModel.setBatteryLevel(80);


            myDatabase.databaseInterfaceDOA().insert(entity);
            myDatabase.databaseInterfaceDOA().insert(batteryAlarmModel);

           // myDatabase.productDao().delete(myDatabase.productDao().getAll().get(1));
            List<BatteryAlarmModel> list = myDatabase.databaseInterfaceDOA().getAll();
            Log.e("MY DATABSE", "Data is"+list.size());
            return null;
        }
    }
}
