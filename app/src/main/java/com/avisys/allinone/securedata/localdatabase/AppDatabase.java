package com.avisys.allinone.securedata.localdatabase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {UserData.class},version = 1,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase appDatabase ;

    public abstract UserDao userDao();

    // Creation of singleton
    public static AppDatabase getAppDatabase(Context context) {
        if (appDatabase==null){
            appDatabase = Room.databaseBuilder(context,AppDatabase.class,"User-Database")
                    .allowMainThreadQueries()
                    .build();
        }
        return appDatabase;
    }



}
