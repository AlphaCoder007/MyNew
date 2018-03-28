package com.example.darkphantom.batteryallert.utils;



public class SongDataUtil {

    private static long songId;
    private static String songName;
    private static int batteryLevel;

    public static int getBatteryLevel() {
        return batteryLevel;
    }

    public static void setBatteryLevel(int batteryLevel) {
        SongDataUtil.batteryLevel = batteryLevel;
    }

    public static long getSongId() {
        return songId;
    }

    public static void setSongId(long songId) {
        SongDataUtil.songId = songId;
    }

    public static String getSongName() {
        return songName;
    }

    public static void setSongName(String songName) {
        SongDataUtil.songName = songName;
    }
}
