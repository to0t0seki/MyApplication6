package com.example.myapplication6.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {TotalMedalTable.class,ModelTable.class},version=1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ALLDao allDao();
}