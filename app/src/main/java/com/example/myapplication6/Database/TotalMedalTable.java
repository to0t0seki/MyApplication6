package com.example.myapplication6.Database;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(primaryKeys = {"modelNO", "date"})
public class TotalMedalTable {
       @NonNull
        public int modelNO;
        public int date;
        public int diff;
}
