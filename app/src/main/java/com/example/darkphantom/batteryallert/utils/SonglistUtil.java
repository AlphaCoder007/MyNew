package com.example.darkphantom.batteryallert.utils;


import com.example.darkphantom.batteryallert.model.SongModel;

import java.util.ArrayList;

public class SonglistUtil {

    private static ArrayList<SongModel> songModelsList = new ArrayList<>();

    public static ArrayList<SongModel> getSongModelsList() {
        return songModelsList;
    }

    public static void setSongModelsList(ArrayList<SongModel> songModelsList) {
        SonglistUtil.songModelsList = songModelsList;
    }
}
