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


    public static <T> TableLayout getTable(Context context, GenerateData.OutData outData)  {
        TableLayout tableLayout = new TableLayout(context);
        TableRow tableHeader = new TableRow(context);
        Map<String,Map<String,String>> headerMap = outData.headerData;
        Iterator<String> headerItr = headerMap.keySet().iterator();
        while (headerItr.hasNext()) {
            String field = headerItr.next();
            TextView textView = new TextView(context);
            textView.setBackgroundResource(R.drawable.border);
            textView.setGravity(Gravity.CENTER);
            textView.setWidth(Integer.parseInt(headerMap.get(field).get("width")));
            textView.setText(field);
            tableHeader.addView(textView);
        }

        tableLayout.addView(tableHeader);

        Map<Integer,Map<String,Integer>> dataMap = outData.data;


        Iterator<Integer> NOItr = dataMap.keySet().iterator();
        while (NOItr.hasNext()) {
            TableRow tableRow = new TableRow(context);
            int NO = NOItr.next();
            TextView textViewNO = new TextView(context);
            textViewNO.setBackgroundResource(R.drawable.border);
            textViewNO.setGravity(Gravity.RIGHT);
            textViewNO.setText(String.valueOf(NO));
            tableRow.addView(textViewNO);
            Map<String,Integer> fieldMap = dataMap.get(NO);
            Iterator<String> fieldItr = fieldMap.keySet().iterator();
            while (fieldItr.hasNext()){
                String field =fieldItr.next();
                TextView textView = new TextView(context);
                textView.setBackgroundResource(R.drawable.border);
                textView.setGravity(Gravity.RIGHT);
                textView.setText(String.valueOf(fieldMap.get(field)));
                tableRow.addView(textView);
            }
            tableLayout.addView(tableRow);
        }
        return tableLayout;
    }
}
