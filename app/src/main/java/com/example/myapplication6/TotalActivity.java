package com.example.myapplication6;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication6.Database.AppDatabase;
import com.example.myapplication6.Database.AppDatabaseSingleton;

import java.util.ArrayList;
import java.util.List;

public class TotalActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout baseLayout = findViewById(R.id.baselayout);
        LinearLayout linearLayout1 = new LinearLayout(this);
        LinearLayout linearLayout2 = new LinearLayout(this);
        LinearLayout linearLayout3 = new LinearLayout(this);

        AppDatabase appDatabase = AppDatabaseSingleton.getInstance(this);
        Handler handler = new Handler();
        Bundle bundle = new Bundle();


        String strSBtnText = "スロ差玉";
        String strPBtnText = "パチ差玉";

        Button diffSButton = new Button(this);
        diffSButton.setText(strSBtnText);
        diffSButton.setOnClickListener((v)->{
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            DiffFragment diffFragment = new DiffFragment();
            bundle.putString("status","slot");
            diffFragment.setArguments(bundle);
            fragmentTransaction.replace(R.id.baselayout,diffFragment);
            fragmentTransaction.commit();
        });
        linearLayout1.addView(diffSButton);

        Button diffPButton = new Button(this);
        diffPButton.setText(strPBtnText);
        diffPButton.setOnClickListener((v)->{
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            DiffFragment diffFragment = new DiffFragment();
            bundle.putString("status","pachi");
            diffFragment.setArguments(bundle);
            fragmentTransaction.replace(R.id.baselayout,diffFragment);
            fragmentTransaction.commit();
        });
        linearLayout1.addView(diffPButton);


        Spinner spinner = new Spinner(this);
        List<String> dateList = new ArrayList<>();
        for(int today = Global.date;today>=Global.STARTDATE;today--){
            dateList.add(String.valueOf(today));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dateList);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                diffPButton.setText(strPBtnText + ":" + (String)parent.getSelectedItem());
                diffSButton.setText(strSBtnText + ":" + (String)parent.getSelectedItem());
                bundle.putInt("date",Integer.parseInt((String)parent.getSelectedItem()));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        linearLayout2.addView(spinner);


        Button updateSTotalButton = new Button(this);
        updateSTotalButton.setText("スロ差枚DB更新");
        updateSTotalButton.setOnClickListener((v)->{
            new DiffMedalUpdateThread(Global.date,appDatabase,handler,this,"1-20-260540").start();
        });
        linearLayout3.addView(updateSTotalButton);

        Button updatePTotalButton = new Button(this);
        updatePTotalButton.setText("パチ差玉DB更新");
        updatePTotalButton.setOnClickListener((v)->{
            new DiffMedalUpdateThread(Global.date,appDatabase,handler,this,"0-4-268758").start();
        });
        linearLayout3.addView(updatePTotalButton);

        baseLayout.addView(linearLayout1);
        baseLayout.addView(linearLayout2);
        baseLayout.addView(linearLayout3);
    }
}
