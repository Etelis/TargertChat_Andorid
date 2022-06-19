package com.example.targertchat.data.model;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Contact.class, Message.class}, version = 8)
public abstract class LocalDatabase extends RoomDatabase{
    private static volatile LocalDatabase instance;
    private static final String DB_NAME = "LOCAL_DB";

    public abstract IContactDao contactDao();
    public abstract IMessageDao messageDao();

    public static void initializeDB(Context context) {
        if (instance == null)
            instance = Room.databaseBuilder(context.getApplicationContext(), LocalDatabase.class, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
    }

    public static synchronized LocalDatabase getInstance(){
        return instance;
    }
}
