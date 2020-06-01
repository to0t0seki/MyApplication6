package com.example.myapplication6.Database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public abstract class ALLDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(List<TotalMedal> list);

    @Query("select modelNAME,date,diff from TotalMedal where  date = :date")
    public abstract List<TotalMedal> queryDATE(int date);


}

