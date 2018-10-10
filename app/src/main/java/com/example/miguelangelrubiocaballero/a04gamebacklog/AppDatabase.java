package com.example.miguelangelrubiocaballero.a04gamebacklog;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;


@Database(entities = {Games.class}, version = 1 , exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract GameDao reminderDao();

    private final static String NAME_DATABASE = "game_db";

    private static AppDatabase sInstance;

    public static AppDatabase getInstance(Context context) {

        if (sInstance == null) {

            sInstance = Room.databaseBuilder(context, AppDatabase.class, NAME_DATABASE).build();
        }

        return sInstance;
    }

}
