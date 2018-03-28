package com.example.darkphantom.batteryallert.Interface;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.darkphantom.batteryallert.model.BatteryAlarmModel;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface DatabaseAccessObject {

    @Query("SELECT * FROM batteryAlarm" +
            "")
    List<BatteryAlarmModel> getAll();

    @Insert
    void insert(BatteryAlarmModel batteryAlarmModel);

    @Delete
    void delete(BatteryAlarmModel batteryAlarmModel);


}
