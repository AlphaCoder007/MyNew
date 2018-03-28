package com.example.darkphantom.batteryallert.model;


/*
**  This model has set of attributes for database which is used to store the data of battery
*    which will restore and retrive from the database when user again open the app in his phone
 */

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "batteryAlarm")
public class BatteryAlarmModel {

    @PrimaryKey(autoGenerate = true)
    private int uid;

    /* song path which already exist in the memory */
    @ColumnInfo(name = "songPath")
    private String songPath;

    @ColumnInfo(name = "songName")
    private String songName;

    @ColumnInfo(name = "batteryLevel")
    private int batteryLevel;

    @ColumnInfo(name = "vibrate")
    private boolean vibrate;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getSongPath() {
        return songPath;
    }

    public void setSongPath(String songPath) {
        this.songPath = songPath;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public int getBatteryLevel() {
        return batteryLevel;
    }

    public void setBatteryLevel(int batteryLevel) {
        this.batteryLevel = batteryLevel;
    }

    public boolean isVibrate() {
        return vibrate;
    }

    public void setVibrate(boolean vibrate) {
        this.vibrate = vibrate;
    }
}
