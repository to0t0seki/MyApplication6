package com.example.myapplication6;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import java.util.ArrayList;
import java.util.List;

public class ModelListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modellist);

        Intent intent = getIntent();
        int date = intent.getIntExtra("date",0);
        LinearLayout baseLayout = findViewById(R.id.baselayout);

        Button kizunaButton = new Button(this);
        kizunaButton.setText("絆データ");
        kizunaButton.setOnClickListener((v)->{
            intent.setClass(this,DetailActivity.class);
            intent.putExtra("modelno","220010002");
            startActivity(intent);
        });
        Spinner kizunaSpinner = new Spinner(this);
        List<Integer> kizunaDateList = new ArrayList<>();
        for(int i = 0;i<3;i++){
            kizunaDateList.add(Global.date-i);
        }
        ArrayAdapter<Integer> kizunaAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, kizunaDateList);
        kizunaSpinner.setAdapter(kizunaAdapter);
        kizunaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                intent.putExtra("date",(Integer)parent.getSelectedItem());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        Button saraban2Button = new Button(this);
        saraban2Button.setText("サラ番2");
        saraban2Button.setOnClickListener((v)->{
            intent.setClass(this,DetailActivity.class);
            intent.putExtra("modelno","220030001");
            startActivity(intent);
        });

        Spinner sarabanSpinner = new Spinner(this);
        List<Integer> sarabanDateList = new ArrayList<>();
        for(int i = 0;i<3;i++){
            sarabanDateList.add(Global.date-i);
        }
        ArrayAdapter<Integer> sarabanAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, sarabanDateList);
        sarabanSpinner.setAdapter(sarabanAdapter);
        sarabanSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                intent.putExtra("date",(Integer)parent.getSelectedItem());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        Button jagButton = new Button(this);
        jagButton.setOnClickListener((v)->{
            intent.setClass(this,DetailActivity.class);
            startActivity(intent);
        });

        Spinner jagSpinner1 = new Spinner(this);
        String[] jagArray1 = {"マイジャグ3","マイジャグ4","ファンキー","ゴージャグ1","ゴージャグ2","ミラクル"};
        ArrayAdapter<String> jagAdapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, jagArray1);
        jagSpinner1.setAdapter(jagAdapter1);
        jagSpinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                jagButton.setText((String)parent.getSelectedItem());
                switch (position){
                    case 0:intent.putExtra("modelno","215060005");//マイジャグ3
                        break;
                    case 1:intent.putExtra("modelno","218050009");//マイジャグ4
                        break;
                    case 2:intent.putExtra("modelno","216060004");//ファンキー
                        break;
                    case 3:intent.putExtra("modelno","215030001");//ゴージャグ1
                        break;
                    case 4:intent.putExtra("modelno","219030001");//ゴージャグ2
                        break;
                    case 5:intent.putExtra("modelno","217050006");//ミラクル
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        Spinner jagSpinner2 = new Spinner(this);
        List<Integer> jagDateList = new ArrayList<>();
        for(int i = 0;i<6;i++){
            jagDateList.add(Global.date-i);
        }
        ArrayAdapter<Integer> jagAdapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, jagDateList);
        jagSpinner2.setAdapter(jagAdapter2);
        jagSpinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                intent.putExtra("date",(Integer)parent.getSelectedItem());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        baseLayout.addView(kizunaButton);
        baseLayout.addView(kizunaSpinner);
        baseLayout.addView(saraban2Button);
        baseLayout.addView(sarabanSpinner);
        baseLayout.addView(jagButton);
        baseLayout.addView(jagSpinner1);
        baseLayout.addView(jagSpinner2);

    }
}
