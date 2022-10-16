package com.avisys.allinone.RoomDatabase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = PersonDetails.class,version = 1,exportSchema = false)
public abstract class RoomDatabaseBuilder extends RoomDatabase {
    private static final String DB_NAME = "Person-Details";
    private static RoomDatabaseBuilder instance;
    /*
     * Here we are creating @Object of this class using Room which accept
     * @param1 getApplicationContext,@param2 RoomDatabaseBuilder.class and @param3 DB_NAME
     */
    public static RoomDatabaseBuilder getInstance(Context context){
        if (instance==null){
            synchronized (RoomDatabaseBuilder.class){
                instance = Room.databaseBuilder(context.getApplicationContext(),RoomDatabaseBuilder.class,DB_NAME)
                        .allowMainThreadQueries().fallbackToDestructiveMigration().build();
            }
        }/* if closed */
        return instance;
    }
    /* Here we just call personDAO using class Object and perform different operation */
    public abstract PersonDAO personDAO();
}
