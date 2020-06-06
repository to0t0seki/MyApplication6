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
            intent.putExtra("model","kizuna");
            startActivity(intent);
        });

        Button saraban2Button = new Button(this);
        saraban2Button.setText("サラ番2");
        saraban2Button.setOnClickListener((v)->{
            intent.setClass(this,DetailActivity.class);
            intent.putExtra("model","saraban2");
            startActivity(intent);
        });

        Button jagButton = new Button(this);
        jagButton.setOnClickListener((v)->{
            intent.setClass(this,DetailActivity.class);
            intent.putExtra("model","jag");
            startActivity(intent);
        });

        Spinner spinner = new Spinner(this);
        String[] kisyu = {"マイジャグ3","マイジャグ4","ファンキー","ゴージャグ1","ゴージャグ2","ミラクル"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, kisyu);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                jagButton.setText((String)parent.getSelectedItem());
                switch (position){
                    case 0:intent.putExtra("kisyu","215060005");//マイジャグ3
                        break;
                    case 1:intent.putExtra("kisyu","218050009");//マイジャグ4
                        break;
                    case 2:intent.putExtra("kisyu","216060004");//ファンキー
                        break;
                    case 3:intent.putExtra("kisyu","215030001");//ゴージャグ1
                        break;
                    case 4:intent.putExtra("kisyu","219030001");//ゴージャグ2
                        break;
                    case 5:intent.putExtra("kisyu","217050006");//ミラクル
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        baseLayout.addView(kizunaButton);
        baseLayout.addView(saraban2Button);
        baseLayout.addView(jagButton);
        baseLayout.addView(spinner);

    }
}
