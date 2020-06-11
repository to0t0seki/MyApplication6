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
import com.example.myapplication6.Database.TotalIO;


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
                tableLayout = creatTable(list);
                linearLayout.removeAllViews();
                linearLayout.addView(tableLayout);
            });
        }).start();

        return scrollView;
    }


    public  TableLayout creatTable(List<TotalIO> list)  {

        TableLayout tableLayout = new TableLayout(getActivity());
        TableRow tableHeader = new TableRow(getActivity());
        String[] names = {"機種名","差玉"};
        int[] widths = {250,150};
        List<CreateTableData.Header>headers =  CreateTableData.createHeaderList(names,widths);
        for (CreateTableData.Header header:headers){
            TextView textView = CreateTableData.creatTextViewHeader(getActivity(),header);
            tableHeader.addView(textView);
        }
        tableLayout.addView(tableHeader);

        int diffTotal=0;

        for (TotalIO totalIO:list) {
            TableRow tableRow = new TableRow(getActivity());
            if(totalIO.modelNAME.length()>15){
                totalIO.modelNAME = totalIO.modelNAME.substring(0,14);
            }
            TextView textView1 = CreateTableData.creatTextViewData(getActivity(),totalIO.modelNAME);
            TextView textView2 = CreateTableData.creatTextViewData(getActivity(),String.valueOf(totalIO.diff));
            diffTotal+=totalIO.diff;
            tableRow.addView(textView1);
            tableRow.addView(textView2);
            tableLayout.addView(tableRow);
        }

        TableRow totalRow = new TableRow(getActivity());
        TextView textView1 = new TextView(getActivity());
        textView1.setBackgroundResource(R.drawable.border);
        textView1.setText("合計");
        textView1.setGravity(Gravity.CENTER);

        TextView textView2 = CreateTableData.creatTextViewData(getActivity(),String.valueOf(diffTotal));
        totalRow.addView(textView1);
        totalRow.addView(textView2);
        tableLayout.addView(totalRow,1);

        return tableLayout;
    }
}
