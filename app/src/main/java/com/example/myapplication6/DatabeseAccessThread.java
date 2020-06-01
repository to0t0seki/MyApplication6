package com.example.myapplication6;

import com.example.myapplication6.Database.AppDatabase;
import com.example.myapplication6.Database.AppDatabaseSingleton;
import com.example.myapplication6.Database.TotalMedal;

import java.util.List;

public class DatabeseAccessThread extends Thread {
    AppDatabase appDatabase;
    int date;
    CallbackInstance callbackInstance;

    interface CallbackInstance{
        void callbackMethod(List<TotalMedal> list);
    }

    public DatabeseAccessThread setCallbackInsetance(AppDatabase appDatabase,int date,CallbackInstance callbackInsetance){
        this.callbackInstance = callbackInsetance;
        this.appDatabase = appDatabase;
        this.date = date;
        return this;
    }
    @Override
    public void run() {
        List<TotalMedal> list = appDatabase.allDao().queryDATE(date);
        callbackInstance.callbackMethod(list);
    }
}
