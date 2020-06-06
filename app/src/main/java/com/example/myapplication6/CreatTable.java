package com.example.myapplication6;

import android.content.Context;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Map;

public class CreatTable {


    public static <T> TableLayout getTable(Context context,Map<Integer,Map<String,Integer>> map)  {
        TableLayout tableLayout = new TableLayout(context);
        TableRow tableHeader = new TableRow(context);
        Iterator<Integer> Itr = map.keySet().iterator();
        if(Itr.hasNext()){
            int i = Itr.next();
            Iterator<String> strItr = map.get(i).keySet().iterator();
            TextView textViewNO = new TextView(context);
            textViewNO.setBackgroundResource(R.drawable.border);
            textViewNO.setGravity(Gravity.CENTER);
            textViewNO.setWidth(150);
            textViewNO.setText("NO");
            tableHeader.addView(textViewNO);
            while (strItr.hasNext()){
                TextView textView = new TextView(context);
                textView.setBackgroundResource(R.drawable.border);
                textView.setGravity(Gravity.CENTER);
                textView.setWidth(150);
                textView.setText(strItr.next());
                tableHeader.addView(textView);
            }
        }else{
            return tableLayout;
        }



        tableLayout.addView(tableHeader);

        Iterator<Integer> NOItr = map.keySet().iterator();
        while (NOItr.hasNext()) {
            TableRow tableRow = new TableRow(context);
            int NO = NOItr.next();
            TextView textViewNO = new TextView(context);
            textViewNO.setBackgroundResource(R.drawable.border);
            textViewNO.setGravity(Gravity.RIGHT);
            textViewNO.setWidth(150);
            textViewNO.setText(String.valueOf(NO));
            tableRow.addView(textViewNO);
            Map<String,Integer> fieldMap = map.get(NO);
            Iterator<String> fieldItr = fieldMap.keySet().iterator();
            while (fieldItr.hasNext()){
                String field =fieldItr.next();
                TextView textView = new TextView(context);
                textView.setBackgroundResource(R.drawable.border);
                textView.setGravity(Gravity.RIGHT);
                textView.setWidth(150);
                textView.setText(String.valueOf(fieldMap.get(field)));
                tableRow.addView(textView);
            }
            tableLayout.addView(tableRow);
        }
        return tableLayout;
    }
}
