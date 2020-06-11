package com.example.myapplication6;

import com.example.myapplication6.Database.AppDatabase;
import com.example.myapplication6.Database.TotalIO;


import java.lang.reflect.Field;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.List;

public class DatabeseAccessThread extends Thread {
    AppDatabase appDatabase;
    int date;
    String status;
    CallbackInstance callbackInstance;

    interface CallbackInstance{
        void callbackMethod(List<TotalIO> list);
    }

    public DatabeseAccessThread setCallbackInsetance(AppDatabase appDatabase,int date,String status,CallbackInstance callbackInsetance){
        this.callbackInstance = callbackInsetance;
        this.appDatabase = appDatabase;
        this.date = date;
        this.status = status;
        return this;
    }


    @Override
    public void run() {
        List<TotalIO> list;
        if(status=="slot"){
            list = appDatabase.allDao().querySlotDATE(date);
        }else {
            list = appDatabase.allDao().queryPachiDATE(date);
        }
        callbackInstance.callbackMethod(list);
    }
}
