package com.example.myapplication6.Database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(primaryKeys = {"modelNAME", "date"})
public class TotalMedal {
       // public int modelNO;
       @NonNull
        public String modelNAME;
        public int date;
        public int diff;
}
