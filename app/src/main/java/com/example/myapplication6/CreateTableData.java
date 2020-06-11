package com.example.myapplication6;

import android.content.Context;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


import java.util.ArrayList;

import java.util.List;

public class CreateTableData {
    static class Header{
        String name;
        int width;
        boolean total;
        Header(String name,int width){
            this.name=name;
            this.width=width;
        }
        int gravity = Gravity.CENTER;
    }

    static public TextView creatTextViewData(Context context,String data){
        TextView textView = new TextView(context);
        textView.setText(data);
        textView.setBackgroundResource(R.drawable.border);
        textView.setGravity(Gravity.RIGHT);
        return textView;
    }

    static public TextView creatTextViewHeader(Context context,Header header){
        TextView textView = new TextView(context);
        textView.setText(header.name);
        textView.setWidth(header.width);
        textView.setBackgroundResource(R.drawable.border);
        textView.setGravity(header.gravity);
        return textView;
    }



    static public List<Header> createHeaderList(String[] names,int[] widths){
        List<Header> headers = new ArrayList<>();
        if(names.length !=widths.length){
            System.out.println("長さが違います");
            return null;
        }
        for(int i=0;i<names.length;i++){
            Header header = new Header(names[i],widths[i]);
            headers.add(header);
        }
        return headers;
    }


    static public TableRow creatTableRow(Context context,List<Header> headers){
        TableRow tableRow = new TableRow(context);
        for (Header header:headers){
            TextView textView = new TextView(context);
            textView.setText(header.name);
            textView.setWidth(header.width);
            textView.setBackgroundResource(R.drawable.border);
            textView.setGravity(Gravity.RIGHT);
            tableRow.addView(textView);
        }
        return tableRow;
    }

    static public TableLayout createTableLayout(Context context,List<List<Object>> tableList){

        TableLayout tableLayout = new TableLayout(context);

        for(List<Object>list :tableList){
            TableRow tableRow = new TableRow(context);
            for(Object data:list){
                TextView textView = new TextView(context);
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
