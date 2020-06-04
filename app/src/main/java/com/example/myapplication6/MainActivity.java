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
import com.example.myapplication6.Database.TotalIO;

import org.jsoup.select.Evaluator;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        LinearLayout baseLayout = findViewById(R.id.baselayout);


        SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance();
        int date =Integer.parseInt(simpleDateFormat.format(calendar.getTime()));

        Bundle bundle = new Bundle();
        bundle.putInt("DATE",date);

        Handler handler = new Handler();

        AppDatabase appDatabase = AppDatabaseSingleton.getInstance(this);


        Button modelListButton = new Button(this);
        modelListButton.setText("機種リスト");
        modelListButton.setOnClickListener((v)->{
            Intent intent = new Intent(this, ModelListActivity.class);
            intent.putExtra("date", date);
            startActivity(intent);
        });



        Button updateModelButton = new Button(this);
        updateModelButton.setText("機種LIST更新");
        updateModelButton.setOnClickListener((v)->{
            new UpdateModelTableThread(this,handler,appDatabase,"00041817", new String[]{"1-20-268759", "0-4-268758"}).start();
        });

        Button diffSButton = new Button(this);
        diffSButton.setText("スロ差枚");
        diffSButton.setOnClickListener((v)->{
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            DiffFragment diffFragment = new DiffFragment();
            bundle.putString("status","slot");
            diffFragment.setArguments(bundle);
            fragmentTransaction.replace(R.id.baselayout,diffFragment);
            fragmentTransaction.commit();
        });

        Button diffPButton = new Button(this);
        diffPButton.setText("パチ差玉");
        diffPButton.setOnClickListener((v)->{
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            DiffFragment diffFragment = new DiffFragment();
            bundle.putString("status","pachi");
            diffFragment.setArguments(bundle);
            fragmentTransaction.replace(R.id.baselayout,diffFragment);
            fragmentTransaction.commit();
        });

        Button updateSTotalButton = new Button(this);
        updateSTotalButton.setText("スロ差枚DB更新");
        updateSTotalButton.setOnClickListener((v)->{
            new DiffMedalUpdateThread(date,appDatabase,handler,this,"1-20-260540").start();
        });
        Button updatePTotalButton = new Button(this);
        updatePTotalButton.setText("パチ差玉DB更新");
        updatePTotalButton.setOnClickListener((v)->{
            new DiffMedalUpdateThread(date,appDatabase,handler,this,"0-4-268758").start();
        });

        LinearLayout layout1 = new LinearLayout(this);
        LinearLayout layout2= new LinearLayout(this);
        LinearLayout layout3= new LinearLayout(this);

        layout1.setOrientation(LinearLayout.HORIZONTAL);
        layout2.setOrientation(LinearLayout.HORIZONTAL);
        layout3.setOrientation(LinearLayout.HORIZONTAL);

        layout1.addView(modelListButton);
        layout1.addView(updateModelButton);
        layout1.addView(diffSButton);
        layout1.addView(diffPButton);

        layout2.addView(updateSTotalButton);
        layout2.addView(updatePTotalButton);
        baseLayout.addView(layout1);
        baseLayout.addView(layout2);

    }
}
