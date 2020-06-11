package com.example.myapplication6;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.example.myapplication6.Data.Kizuna2;
import com.example.myapplication6.Data.Saraban2;

import java.util.List;

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


        String modelNo = intent.getStringExtra("modelno");
        switch (modelNo){
            case "220010002":
                getKizuna(Global.hallNo,modelNo,intent.getIntExtra("date",0));
                break;
            case "215060005":
            case "218050009":
            case "216060004":
            case "215030001":
            case "219030001":
            case "217050006":

                getJag(Global.hallNo,modelNo,intent.getIntExtra("date",0));
                break;
            case "220030001":
                getSaraban2(Global.hallNo,modelNo,intent.getIntExtra("date",0));
                break;

        }
    }

    public void getSaraban2(String hallNo,String modelNo,int date){
        new GetUnitNOsThread().setCallbackInstance(hallNo,modelNo,(unitNOs)->{
            new GetHistoryThread().setCallbackInstance(hallNo,unitNOs,date,(historys)->{
                List<Saraban2>saraban2s  =  GenerateOutData.getSaraban2List(historys);
                List<List<Object>> saraban2TableData = GenerateOutData.genSaraban2Table(saraban2s);
                TableLayout tableLayout = CreateTableData.createTableLayout(this,saraban2TableData);
                String[] names = {"NO","total","BB","RB","NRB","HG"};
                int[] widths = {80,150,80,80,80,150};
                List<CreateTableData.Header>headers = CreateTableData.createHeaderList(names,widths);
                TableRow tableRow =CreateTableData.creatTableRow(this,headers);
                tableLayout.addView(tableRow,0);
                handler.post(()->{
                    linearLayout.removeAllViews();
                    linearLayout.addView(tableLayout);
                });
            }).start();
        }).start();
    }


    public void getKizuna(String hallNo,String modelNo,int date){
        new GetUnitNOsThread().setCallbackInstance(hallNo,modelNo,(unitNOs)->{
            new GetHistoryThread().setCallbackInstance(hallNo,unitNOs,date,(historys)->{
                List<Kizuna2> kizuna2s =  GenerateOutData.getkizuna2List(historys);
                List<List<Object>> kizuna2TableData = GenerateOutData.genKizuna2Table(kizuna2s);
                TableLayout tableLayout = CreateTableData.createTableLayout(this,kizuna2TableData);
                String[] names ={"NO","total","IBC","IBT","DBC","DBT","TBC","TBT","toBC","toBT","RATE","HG","HBC"};
                int[] widths ={80,150,80,80,80,80,80,80,80,80,150,100,80};
                List<CreateTableData.Header>headers = CreateTableData.createHeaderList(names,widths);
                TableRow tableRow =CreateTableData.creatTableRow(this,headers);
                tableLayout.addView(tableRow,0);
                handler.post(()->{
                    linearLayout.removeAllViews();
                    linearLayout.addView(tableLayout);
                });
            }).start();
        }).start();
    }

    public void getJag(String hallNo,String modelNo,int date){
        new GetIndex_sort(hallNo,modelNo,date,(tableList)->{
            TableLayout tableLayout = CreateTableData.createTableLayout(this,tableList);
            handler.post(()->{
                String[] names = {"NO","total","BB","RB","diff","1k/G"};
                int[] widths = {80,150,80,80,150,150};
                List<CreateTableData.Header> headers = CreateTableData.createHeaderList(names,widths);
                TableRow tableRow = CreateTableData.creatTableRow(this,headers);
                tableLayout.addView(tableRow,0);
                linearLayout.addView(tableLayout);
            });




        }).start();
    }

    public TableLayout createTable(List<List<Integer>> tableList){

        TableLayout tableLayout = new TableLayout(this);

        for(List<Integer>list :tableList){
            TableRow tableRow = new TableRow(this);
            for(int data:list){
                TextView textView = new TextView(this);
                textView.setText(String.valueOf(data));
                textView.setBackgroundResource(R.drawable.border);
                textView.setGravity(Gravity.RIGHT);
                tableRow.addView(textView);
            }
            tableLayout.addView(tableRow);
        }
        return tableLayout;
    }


}