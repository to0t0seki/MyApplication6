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

import com.example.myapplication6.Data.Kizuna2;
import com.example.myapplication6.Data.Saraban2;

import java.util.List;
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
                TableLayout tableLayout = createTableObject(saraban2TableData);
                List<TableHeader.Header>headers = TableHeader.creatSaraban2Header();
                TableRow tableRow =TableHeader.creatTableRow(this,headers);
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
                TableLayout tableLayout = createTableObject(kizuna2TableData);
                List<TableHeader.Header>headers = TableHeader.creatKizuna2Header();
                TableRow tableRow =TableHeader.creatTableRow(this,headers);
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
            TableLayout tableLayout = createTableObject(tableList);
            handler.post(()->{
                List<TableHeader.Header> headers = TableHeader.creatIndex_sortHeader();
                TableRow tableRow = TableHeader.creatTableRow(this,headers);
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

    public TableLayout createTableObject(List<List<Object>> tableList){

        TableLayout tableLayout = new TableLayout(this);

        for(List<Object>list :tableList){
            TableRow tableRow = new TableRow(this);
            for(Object data:list){
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
