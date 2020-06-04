package com.example.myapplication6.Database;

import androidx.room.Entity;

@Entity(primaryKeys = {"modelNO"})
public class ModelTable {
    public int modelNO;
    public String modelNAME;
}
