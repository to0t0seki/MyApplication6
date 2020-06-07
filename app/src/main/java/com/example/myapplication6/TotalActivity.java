package com.example.myapplication6;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication6.Database.AppDatabase;
import com.example.myapplication6.Database.AppDatabaseSingleton;

public class TotalActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout baseLayout = findViewById(R.id.baselayout);

        AppDatabase appDatabase = AppDatabaseSingleton.getInstance(this);
        Handler handler = new Handler();
        Bundle bundle = new Bundle();
        bundle.putInt("DATE",Global.date);

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
        baseLayout.addView(diffSButton);

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
        baseLayout.addView(diffPButton);

        Button updateSTotalButton = new Button(this);
        updateSTotalButton.setText("スロ差枚DB更新");
        updateSTotalButton.setOnClickListener((v)->{
            new DiffMedalUpdateThread(Global.date,appDatabase,handler,this,"1-20-260540").start();
        });
        baseLayout.addView(updateSTotalButton);

        Button updatePTotalButton = new Button(this);
        updatePTotalButton.setText("パチ差玉DB更新");
        updatePTotalButton.setOnClickListener((v)->{
            new DiffMedalUpdateThread(Global.date,appDatabase,handler,this,"0-4-268758").start();
        });
        baseLayout.addView(updatePTotalButton);
    }
}
