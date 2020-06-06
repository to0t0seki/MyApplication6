package com.example.myapplication6;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.Map;

public class KizunaFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        HorizontalScrollView horizontalScrollView = new HorizontalScrollView(getActivity());

        DataViewModel dataViewModel = new ViewModelProvider(getActivity(),new ViewModelProvider.NewInstanceFactory()).get(DataViewModel.class);
        dataViewModel.liveData.observe(getViewLifecycleOwner(),(map)->{
            horizontalScrollView.removeAllViews();
            LinearLayout linearLayout = new LinearLayout(getActivity());
            linearLayout.setOrientation(LinearLayout.VERTICAL);

            TextView updatteTimeText = new TextView(getActivity());
            updatteTimeText.setText("更新時間:"+dataViewModel.updateTime);
            linearLayout.addView(updatteTimeText);

            TableLayout tableLayout = new TableLayout(getActivity());
            TableRow rowHeader = new TableRow(getActivity());

            TextView NOHeader = new TextView(getActivity());
            NOHeader.setText("NO");
            NOHeader.setBackgroundResource(R.drawable.border);
            NOHeader.setWidth(90);
            NOHeader.setGravity(Gravity.RIGHT);

            TextView IBCHeader = new TextView(getActivity());
            IBCHeader.setText("IBC");
            IBCHeader.setBackgroundResource(R.drawable.border);
            IBCHeader.setWidth(80);
            IBCHeader.setGravity(Gravity.RIGHT);

            TextView DBCHeader = new TextView(getActivity());
            DBCHeader.setText("DBC");
            DBCHeader.setBackgroundResource(R.drawable.border);
            DBCHeader.setWidth(90);
            DBCHeader.setGravity(Gravity.RIGHT);

            TextView TBCHeader = new TextView(getActivity());
            TBCHeader.setText("TBC");
            TBCHeader.setBackgroundResource(R.drawable.border);
            TBCHeader.setWidth(80);
            TBCHeader.setGravity(Gravity.RIGHT);

            TextView IBTHeader = new TextView(getActivity());
            IBTHeader.setText("IBT");
            IBTHeader.setBackgroundResource(R.drawable.border);
            IBTHeader.setWidth(80);
            IBTHeader.setGravity(Gravity.RIGHT);

            TextView DBTHeader = new TextView(getActivity());
            DBTHeader.setText("DBT");
            DBTHeader.setBackgroundResource(R.drawable.border);
            DBTHeader.setWidth(80);
            DBTHeader.setGravity(Gravity.RIGHT);

            TextView TBTHeader = new TextView(getActivity());
            TBTHeader.setText("TBT");
            TBTHeader.setBackgroundResource(R.drawable.border);
            TBTHeader.setWidth(80);
            TBTHeader.setGravity(Gravity.RIGHT);

            TextView toBCHeader = new TextView(getActivity());
            toBCHeader.setText("toC");
            toBCHeader.setBackgroundResource(R.drawable.border);
            toBCHeader.setWidth(80);
            toBCHeader.setGravity(Gravity.RIGHT);

            TextView toBTHeader = new TextView(getActivity());
            toBTHeader.setText("toT");
            toBTHeader.setBackgroundResource(R.drawable.border);
            toBTHeader.setWidth(80);
            toBTHeader.setGravity(Gravity.RIGHT);

            TextView RATEHeader = new TextView(getActivity());
            RATEHeader.setText("RATE");
            RATEHeader.setBackgroundResource(R.drawable.border);
            RATEHeader.setWidth(120);
            RATEHeader.setGravity(Gravity.RIGHT);

            TextView HGHeader = new TextView(getActivity());
            HGHeader.setText("HG");
            HGHeader.setBackgroundResource(R.drawable.border);
            HGHeader.setWidth(80);
            HGHeader.setGravity(Gravity.RIGHT);

            TextView HBCHeader = new TextView(getActivity());
            HBCHeader.setText("HB");
            HBCHeader.setBackgroundResource(R.drawable.border);
            HBCHeader.setWidth(80);
            HBCHeader.setGravity(Gravity.RIGHT);

            TextView DIFFHeader = new TextView(getActivity());
            DIFFHeader.setText("DIFF");
            DIFFHeader.setBackgroundResource(R.drawable.border);
            DIFFHeader.setWidth(120);
            DIFFHeader.setGravity(Gravity.RIGHT);

            rowHeader.addView(NOHeader);
            rowHeader.addView(IBCHeader);
            rowHeader.addView(IBTHeader);
            rowHeader.addView(DBCHeader);
            rowHeader.addView(DBTHeader);
            rowHeader.addView(TBCHeader);
            rowHeader.addView(TBTHeader);
            rowHeader.addView(toBCHeader);
            rowHeader.addView(toBTHeader);
            rowHeader.addView(RATEHeader);
            rowHeader.addView(HGHeader);
            rowHeader.addView(HBCHeader);
            rowHeader.addView(DIFFHeader);

            tableLayout.addView(rowHeader);

            int IBCTotal = 0;
            int DBCTotal = 0;
            int TBCTotal = 0;
            int toBCTotal = 0;
            int IBTTotal = 0;
            int DBTTotal = 0;
            int TBTTotal = 0;
            int toBTTotal = 0;
            int DIFFTotal = 0;

            for (Object s:map.keySet()){

                TableRow tableRow = new TableRow(getActivity());
                Map<String,Integer>detailmap = (Map<String,Integer>)map.get(s);
                TextView textNO = new TextView(getActivity());
                textNO.setBackgroundResource(R.drawable.border);
                textNO.setText(s.toString());
                textNO.setGravity(Gravity.RIGHT);
      

                TextView textIBC = new TextView(getActivity());
                textIBC.setBackgroundResource(R.drawable.border);
                textIBC.setText(String.valueOf(detailmap.get("IBC")));
                textIBC.setGravity(Gravity.RIGHT);

                IBCTotal += detailmap.get("IBC");

                TextView textDBC = new TextView(getActivity());
                textDBC.setBackgroundResource(R.drawable.border);
                textDBC.setText(String.valueOf(detailmap.get("DBC")));
                textDBC.setGravity(Gravity.RIGHT);

                DBCTotal += detailmap.get("DBC");

                TextView textTBC = new TextView(getActivity());
                textTBC.setBackgroundResource(R.drawable.border);
                textTBC.setText(String.valueOf(detailmap.get("TBC")));
                textTBC.setGravity(Gravity.RIGHT);

                TBCTotal += detailmap.get("TBC");


                TextView textIBT = new TextView(getActivity());
                textIBT.setBackgroundResource(R.drawable.border);
                textIBT.setText(String.valueOf(detailmap.get("IBT")));
                textIBT.setGravity(Gravity.RIGHT);

                IBTTotal += detailmap.get("IBT");

                TextView textDBT = new TextView(getActivity());
                textDBT.setBackgroundResource(R.drawable.border);
                textDBT.setText(String.valueOf(detailmap.get("DBT")));
                textDBT.setGravity(Gravity.RIGHT);

                DBTTotal += detailmap.get("DBT");

                TextView textTBT = new TextView(getActivity());
                textTBT.setBackgroundResource(R.drawable.border);
                textTBT.setText(String.valueOf(detailmap.get("TBT")));
                textTBT.setGravity(Gravity.RIGHT);

                TBTTotal += detailmap.get("TBT");

                TextView texttoBC = new TextView(getActivity());
                texttoBC.setBackgroundResource(R.drawable.border);
                texttoBC.setText(String.valueOf(detailmap.get("toBC")));
                texttoBC.setGravity(Gravity.RIGHT);

                toBCTotal += detailmap.get("toBC");

                TextView texttoBT = new TextView(getActivity());
                texttoBT.setBackgroundResource(R.drawable.border);
                texttoBT.setText(String.valueOf(detailmap.get("toBT")));
                texttoBT.setGravity(Gravity.RIGHT);

                toBTTotal += detailmap.get("toBT");


                TextView textRATE = new TextView(getActivity());
                textRATE.setBackgroundResource(R.drawable.border);
                textRATE.setText(String.valueOf(detailmap.get("RATE")));
                textRATE.setGravity(Gravity.RIGHT);


                TextView textHG = new TextView(getActivity());
                textHG.setBackgroundResource(R.drawable.border);
                textHG.setText(String.valueOf(detailmap.get("HG")));
                textHG.setGravity(Gravity.RIGHT);


                TextView textHBC = new TextView(getActivity());
                textHBC.setBackgroundResource(R.drawable.border);
                textHBC.setText(String.valueOf(detailmap.get("HBC")));
                textHBC.setGravity(Gravity.RIGHT);

                TextView textDIFF = new TextView(getActivity());
                textDIFF.setBackgroundResource(R.drawable.border);
                textDIFF.setText(String.valueOf(detailmap.get("DIFF")));
                textDIFF.setGravity(Gravity.RIGHT);

                DIFFTotal += detailmap.get("DIFF");


                tableRow.addView(textNO);
                tableRow.addView(textIBC);
                tableRow.addView(textIBT);
                tableRow.addView(textDBC);
                tableRow.addView(textDBT);
                tableRow.addView(textTBC);
                tableRow.addView(textTBT);
                tableRow.addView(texttoBC);
                tableRow.addView(texttoBT);
                tableRow.addView(textRATE);
                tableRow.addView(textHG);
                tableRow.addView(textHBC);
                tableRow.addView(textDIFF);

                tableLayout.addView(tableRow);
            }

            TableRow tableRow = new TableRow(getActivity());
            TableRow.LayoutParams rowLayout = new TableRow.LayoutParams();
            rowLayout.span = 3;

            TextView blankView1 = new TextView(getActivity());
            blankView1.setBackgroundResource(R.drawable.border);

            TextView blankView2 = new TextView(getActivity());
            blankView2.setBackgroundResource(R.drawable.border);

            TextView totalIBC = new TextView(getActivity());
            totalIBC.setBackgroundResource(R.drawable.border);
            totalIBC.setText(String.valueOf(IBCTotal));
            totalIBC.setGravity(Gravity.RIGHT);

            TextView totalDBC = new TextView(getActivity());
            totalDBC.setBackgroundResource(R.drawable.border);
            totalDBC.setText(String.valueOf(DBCTotal));
            totalDBC.setGravity(Gravity.RIGHT);

            TextView totalTBC = new TextView(getActivity());
            totalTBC.setBackgroundResource(R.drawable.border);
            totalTBC.setText(String.valueOf(TBCTotal));
            totalTBC.setGravity(Gravity.RIGHT);

            TextView totalIBT = new TextView(getActivity());
            totalIBT.setBackgroundResource(R.drawable.border);
            totalIBT.setText(String.valueOf(IBTTotal));
            totalIBT.setGravity(Gravity.RIGHT);

            TextView totalDBT = new TextView(getActivity());
            totalDBT.setBackgroundResource(R.drawable.border);
            totalDBT.setText(String.valueOf(DBTTotal));
            totalDBT.setGravity(Gravity.RIGHT);

            TextView totalTBT = new TextView(getActivity());
            totalTBT.setBackgroundResource(R.drawable.border);
            totalTBT.setText(String.valueOf(TBTTotal));
            totalTBT.setGravity(Gravity.RIGHT);

            TextView totaltoBC = new TextView(getActivity());
            totaltoBC.setBackgroundResource(R.drawable.border);
            totaltoBC.setText(String.valueOf(toBCTotal));
            totaltoBC.setGravity(Gravity.RIGHT);

            TextView totaltoBT = new TextView(getActivity());
            totaltoBT.setBackgroundResource(R.drawable.border);
            totaltoBT.setText(String.valueOf(toBTTotal));
            totaltoBT.setGravity(Gravity.RIGHT);

            TextView totalDIFF = new TextView(getActivity());
            totalDIFF.setBackgroundResource(R.drawable.border);
            totalDIFF.setText(String.valueOf(DIFFTotal));
            totalDIFF.setGravity(Gravity.RIGHT);


            tableRow.addView(blankView1);
            tableRow.addView(totalIBC);
            tableRow.addView(totalIBT);
            tableRow.addView(totalDBC);
            tableRow.addView(totalDBT);
            tableRow.addView(totalTBC);
            tableRow.addView(totalTBT);
            tableRow.addView(totaltoBC);
            tableRow.addView(totaltoBT);
            tableRow.addView(blankView2,rowLayout);
            tableRow.addView(totalDIFF);
            tableLayout.addView(tableRow);


            linearLayout.addView(tableLayout);
            horizontalScrollView.addView(linearLayout);
            Toast.makeText(getActivity() , "更新しました。", Toast.LENGTH_LONG).show();
        });

        return horizontalScrollView;
    }
}
