package com.example.myapplication6;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.app.Application;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.myapplication6.Database.AppDatabase;
import com.example.myapplication6.Database.AppDatabaseSingleton;


import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout baseLayout = findViewById(R.id.baselayout);


        SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance();
        Global.date =Integer.parseInt(simpleDateFormat.format(calendar.getTime()));


        Handler handler = new Handler();

        AppDatabase appDatabase = AppDatabaseSingleton.getInstance(this);

        Button Totalbutton = new Button(this);
        Totalbutton.setText("total差玉表示");
        Totalbutton.setOnClickListener((v)->{
            Intent intent = new Intent(this, TotalActivity.class);
            startActivity(intent);
        });
        baseLayout.addView(Totalbutton);



        Button modelListButton = new Button(this);
        modelListButton.setText("機種リスト");
        modelListButton.setOnClickListener((v)->{
            Intent intent = new Intent(this, ModelListActivity.class);
            intent.putExtra("date", Global.date);
            startActivity(intent);
        });


        Button updateModelButton = new Button(this);
        updateModelButton.setText("機種リスト更新");
        updateModelButton.setOnClickListener((v)->{
            new UpdateModelTableThread(this,handler,appDatabase,"00041817", new String[]{"1-20-268759", "0-4-268758"}).start();
        });





        baseLayout.addView(modelListButton);
        baseLayout.addView(updateModelButton);


    }
}
