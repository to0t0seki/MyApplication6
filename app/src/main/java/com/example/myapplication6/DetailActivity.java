package com.example.myapplication6;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import java.util.Map;

public class DetailActivity extends AppCompatActivity {
    Handler handler;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        handler = new Handler();
        Intent intent = getIntent();

        LinearLayout baseLayout = findViewById(R.id.baselayout);
        ScrollView scrollView = new ScrollView(this);
        baseLayout.addView(scrollView);

        HorizontalScrollView horizontalScrollView = new HorizontalScrollView(this);
        scrollView.addView(horizontalScrollView);

        linearLayout = new LinearLayout(this);
        horizontalScrollView.addView(linearLayout);


        String modelNAME = intent.getStringExtra("model");
        switch (modelNAME){
            case "kizuna":
                getKizuna();
                break;
            case "jag":
                getJag();
                break;
            case "saraban2":
                getSaraban2();
                break;

        }
    }

    public void getSaraban2(){
        new GetUnitNOsThread().setCallbackInstance("00041817","220030001",(unitNOs)->{
            new GetHistoryThread().setCallbackInstance("00041817",unitNOs,(historys)->{
//               GenerateData.OutData outData =  GenerateData.saraban2(historys);
//               TableLayout tableLayout = CreatTable.getTable(this,outData);
//
//               handler.post(()->{
//                   linearLayout.removeAllViews();
//                   linearLayout.addView(tableLayout);
//               });

            }).start();
        }).start();
    }


    public void getKizuna(){
        new GetUnitNOsThread().setCallbackInstance("00041817","220010002",(unitNOs)->{
//            new GetHistoryThread().setCallbackInstance("00041817",unitNOs,(historys)->{
//                OutData outData =  GenerateOutData.kizuna2(historys);
//                TableLayout tableLayout = CreatTable.getTable(this,outData);
//                handler.post(()->{
//                    linearLayout.removeAllViews();
//                    linearLayout.addView(tableLayout);
//                });
//            }).start();
        }).start();
    }

    public void getJag(){
        new GetIndex_sort("00041817","220010002",Global.date,(map)->{

        }).start();
    }

    public TableLayout getTable(Map<String, Map<String,Integer>>map){
        TableLayout tableLayout = new TableLayout(this);
        TableRow tableHeader = new TableRow(this);
        {
            TextView textView = new TextView(this);
            textView.setBackgroundResource(R.drawable.border);
            textView.setGravity(Gravity.RIGHT);
            textView.setText("NO");
            textView.setWidth(150);
            tableHeader.addView(textView);
        }
        for(String stutus:map.keySet()){
            TextView textView = new TextView(this);
            textView.setBackgroundResource(R.drawable.border);
            textView.setGravity(Gravity.RIGHT);
            switch (stutus){
                case "6/0":
                    textView.setText("TOTAL");
                    textView.setWidth(150);
                    break;
                case "81/1":
                    textView.setText("DIFF");
                    textView.setWidth(150);
                    break;
                case "1/0":
                    textView.setText("BB");
                    textView.setWidth(150);
                    break;
                case "2/0":
                    textView.setText("RB");
                    textView.setWidth(150);
                    break;
            }
            tableHeader.addView(textView);
        }
        {
            TextView textView = new TextView(this);
            textView.setBackgroundResource(R.drawable.border);
            textView.setGravity(Gravity.RIGHT);
            textView.setText("1k/G");
            textView.setWidth(150);
            tableHeader.addView(textView);
        }
        tableLayout.addView(tableHeader);
        Map<String,Map<String,Integer>> newMap = ChangeMap3.change(map);
        int[] totals = {0,0,0,0};
        for(String NO:newMap.keySet()){
            TableRow tableRow = new TableRow(this);
            {
                TextView textView = new TextView(this);
                textView.setBackgroundResource(R.drawable.border);
                textView.setGravity(Gravity.RIGHT);
                textView.setText(NO);
                tableRow.addView(textView);
            }
            int BB=0;
            int RB=0;
            int total=0;
            int diff=0;

            double k=0;
            for (String status:newMap.get(NO).keySet()){
                TextView textView = new TextView(this);
                textView.setBackgroundResource(R.drawable.border);
                textView.setGravity(Gravity.RIGHT);
                textView.setText(String.valueOf(newMap.get(NO).get(status)));
                tableRow.addView(textView);
                switch (status){
                    case "1/0":
                        BB = newMap.get(NO).get(status);
                        totals[0]+=BB;
                        break;
                    case "2/0":
                        RB = newMap.get(NO).get(status);
                        totals[1]+=RB;
                        break;
                    case "6/0":
                        total = newMap.get(NO).get(status);
                        totals[2]+=total;
                        break;
                    case "81/1":
                        diff = newMap.get(NO).get(status);
                        totals[3]+=diff;
                        break;
                }
            }
            {
                k =(double) total/((BB*312 + RB*104)-diff)*50;
                k = ((double)Math.round(k * 100))/100;
                TextView textView = new TextView(this);
                textView.setBackgroundResource(R.drawable.border);
                textView.setGravity(Gravity.RIGHT);
                textView.setText(String.valueOf(k));
                tableRow.addView(textView);
            }

            tableLayout.addView(tableRow);
        }
        TableRow tableRowTotal = new TableRow(this);
        TextView blankView1 = new TextView(this);
        blankView1.setText("合計");
        blankView1.setBackgroundResource(R.drawable.border);
        tableRowTotal.addView(blankView1);
        for(int i:totals){
            TextView textView = new TextView(this);
            textView.setBackgroundResource(R.drawable.border);
            textView.setGravity(Gravity.RIGHT);
            textView.setText(String.valueOf(i));
            tableRowTotal.addView(textView);
        }
        TextView blankView2 = new TextView(this);
        blankView2.setBackgroundResource(R.drawable.border);
        tableRowTotal.addView(blankView2);
        tableLayout.addView(tableRowTotal,1);
        return tableLayout;
    }
}
