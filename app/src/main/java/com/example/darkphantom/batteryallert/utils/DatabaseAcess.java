package com.example.darkphantom.batteryallert.utils;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.darkphantom.batteryallert.Interface.DatabaseAccessObject;
import com.example.darkphantom.batteryallert.model.BatteryAlarmModel;


@Database(entities = {BatteryAlarmModel.class}, version = 1)
public abstract class DatabaseAcess extends RoomDatabase {
    public abstract DatabaseAccessObject databaseInterfaceDOA();
}
