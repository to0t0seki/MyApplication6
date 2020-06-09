package com.example.myapplication6;

import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication6.Database.AppDatabase;
import com.example.myapplication6.Database.AppDatabaseSingleton;


import java.lang.reflect.Field;

import java.util.List;


public class DiffFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        int date = bundle.getInt("date");
        String status = bundle.getString("status");
        Handler handler = new Handler();
        AppDatabase appDatabase = AppDatabaseSingleton.getInstance(getActivity());


        LinearLayout linearLayout = new LinearLayout(getActivity());
        ScrollView scrollView = new ScrollView(getActivity());
        scrollView.addView(linearLayout);

        new DatabeseAccessThread().setCallbackInsetance(appDatabase,date,status,(list)->{
            handler.post(()->{
                TableLayout tableLayout = new TableLayout(getActivity());
                try {
                    tableLayout = creatTable(list);
                }catch (IllegalAccessException e){
                    System.out.println(e);
                }
                linearLayout.removeAllViews();
                linearLayout.addView(tableLayout);
            });
        }).start();

        return scrollView;
    }

    public  TableLayout creatTable(List list) throws IllegalAccessException {

        TableRow tableHeader = new TableRow(getActivity());
        Field[] fields = list.get(0).getClass().getFields();

        for(Field f:fields){
            TextView textView = new TextView(getActivity());
            textView.setBackgroundResource(R.drawable.border);
            switch (f.getName()) {
                case "modelNAME":
                    textView.setText("機種名");
                    textView.setWidth(250);
                    tableHeader.addView(textView,0);
                    break;
                case "diff":
                    textView.setText("差玉");
                    textView.setWidth(150);
                    textView.setGravity(Gravity.RIGHT);
                    tableHeader.addView(textView);
                    break;
            }
        }
        TableLayout tableLayout = new TableLayout(getActivity());
        tableLayout.addView(tableHeader);

        int diffTotal=0;
        for (Object o:list){
            TableRow tableRow = new TableRow(getActivity());
            for(Field f:fields) {
                TextView textView = new TextView(getActivity());
                textView.setBackgroundResource(R.drawable.border);
                String s=null;
                int i =f.get(o).toString().length();
                if(f.get(o).toString().length()>15){
                    s = f.get(o).toString().substring(0,16);
                }else {
                    s = f.get(o).toString();
                }
                textView.setText(s);

                switch (f.getName()) {
                    case "modelNAME":
                        tableRow.addView(textView,0);
                        break;
                    case "diff":
                        textView.setGravity(Gravity.RIGHT);
                        tableRow.addView(textView);
                        diffTotal+=Integer.parseInt(s);
                        break;
                }
            }
            tableLayout.addView(tableRow);
        }

        TableRow totalRow = new TableRow(getActivity());
        TextView textView1 = new TextView(getActivity());
        textView1.setBackgroundResource(R.drawable.border);
        textView1.setText("合計");
        TextView textView2 = new TextView(getActivity());
        textView2.setBackgroundResource(R.drawable.border);
        textView2.setText(String.valueOf(diffTotal));
        textView2.setGravity(Gravity.RIGHT);
        totalRow.addView(textView1);
        totalRow.addView(textView2);
        tableLayout.addView(totalRow,1);

        return tableLayout;
    }
}
