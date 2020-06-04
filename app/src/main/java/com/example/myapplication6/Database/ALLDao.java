package com.example.myapplication6.Database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.RoomWarnings;

import java.util.List;

@Dao
public abstract class ALLDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(List<TotalMedalTable> list);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertModelTable(List<ModelTable> modelTables);

    @Query("select M.modelNAME,T.diff from TotalMedalTable as T INNER JOIN ModelTable as M ON T.modelNO = M.modelNO where  T.date = :date and T.modelNO >= 200000000")
    public abstract List<TotalIO> querySlotDATE(int date);

    @Query("select M.modelNAME,T.diff from TotalMedalTable as T INNER JOIN ModelTable as M ON T.modelNO = M.modelNO where  T.date = :date and T.modelNO < 200000000")
    public abstract List<TotalIO> queryPachiDATE(int date);


}

