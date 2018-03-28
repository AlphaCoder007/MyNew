package com.example.darkphantom.batteryallert.utils;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;

import com.example.darkphantom.batteryallert.model.BatteryAlarmModel;

import java.util.ArrayList;
import java.util.List;


public class DatabaseQuery {

    public interface OnTaskCompeleted {
        void onCompleted(List<BatteryAlarmModel> batteryList);
    }

    static OnTaskCompeleted onTaskCompeleted;


    public void insertNewData(BatteryAlarmModel batteryAlarmModel,int cases, Context context) {

        new DatabaseWorker(batteryAlarmModel, cases, context).execute();
    }

   static class DatabaseWorker extends AsyncTask<Void,Void, Void> {

        private BatteryAlarmModel batteryAlarmModel;
        private DatabaseAcess databaseAcess;
        private int cases;
        private List<BatteryAlarmModel> batteryList = new ArrayList<>();


        public DatabaseWorker(BatteryAlarmModel batteryAlarmModel, int cases, Context context) {

            this.batteryAlarmModel = batteryAlarmModel;
            this.cases = cases;
            databaseAcess= Room.databaseBuilder(context,
                           DatabaseAcess.class, "batterAlert").build();

        }

        // In order to make different operation in database we can make either three different task or we can do cases.
        @Override
        protected Void doInBackground(Void... voids) {


            switch (cases) {
                case 1 :  databaseAcess.databaseInterfaceDOA().insert(batteryAlarmModel);

                    break;

                case 2 :  databaseAcess.databaseInterfaceDOA().delete(batteryAlarmModel);

                    break;

                case 3 : batteryList = databaseAcess.databaseInterfaceDOA().getAll();
                    break;
            }

            if (onTaskCompeleted != null) {
                onTaskCompeleted.onCompleted(batteryList);
            }

            // myDatabase.productDao().delete(myDatabase.productDao().getAll().get(1));


            return null;
        }

        public void setOnTaskCompeleted(OnTaskCompeleted onTaskkCompeleted) {
            onTaskCompeleted = onTaskkCompeleted;
        }
    }
}
