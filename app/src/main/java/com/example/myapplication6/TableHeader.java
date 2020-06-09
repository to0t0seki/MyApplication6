package com.example.myapplication6;

import android.content.Context;
import android.view.Gravity;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TableHeader {

    static class Header{
        String name;
        int width;
        boolean total;
        Header(String name,int width){
            this.name=name;
            this.width=width;
        }
    }

   static public List<Header> creatIndex_sortHeader(){
        Header header1 = new Header("NO",80);
        Header header2 = new Header("total",130);
        Header header3 = new Header("BB",80);
        Header header4 = new Header("RB",80);
        Header header5 = new Header("diff",130);
       Header header6 = new Header("1k/G",130);
        List<Header> headers = new ArrayList<>();
        headers.add(header1);
        headers.add(header2);
        headers.add(header3);
        headers.add(header4);
        headers.add(header5);
        headers.add(header6);
        return headers;
    }

    static public List<Header> creatKizuna2Header(){
        Header header1 = new Header("NO",80);
        Header header2 = new Header("total",130);
        Header header3 = new Header("IBC",80);
        Header header4 = new Header("IBT",80);
        Header header5 = new Header("DBC",80);
        Header header6 = new Header("DBT",80);
        Header header7 = new Header("TBC",80);
        Header header8 = new Header("TBT",80);
        Header header9 = new Header("toBC",80);
        Header header10 = new Header("toBT",80);
        Header header11 = new Header("RATE",130);
        Header header12 = new Header("HG",100);
        Header header13 = new Header("HBC",80);
        List<Header> headers = new ArrayList<>();
        headers.add(header1);
        headers.add(header2);
        headers.add(header3);
        headers.add(header4);
        headers.add(header5);
        headers.add(header6);
        headers.add(header7);
        headers.add(header8);
        headers.add(header9);
        headers.add(header10);
        headers.add(header11);
        headers.add(header12);
        headers.add(header13);
        return headers;
    }


    static public List<Header> creatSaraban2Header(){
        Header header1 = new Header("NO",80);
        Header header2 = new Header("total",130);
        Header header3 = new Header("BB",80);
        Header header4 = new Header("RB",80);
        Header header5 = new Header("NRB",80);
        Header header6 = new Header("hamari",130);
        List<Header> headers = new ArrayList<>();
        headers.add(header1);
        headers.add(header2);
        headers.add(header3);
        headers.add(header4);
        headers.add(header5);
        headers.add(header6);
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
}
