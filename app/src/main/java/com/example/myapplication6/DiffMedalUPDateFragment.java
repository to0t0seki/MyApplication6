package com.example.myapplication6;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication6.Database.AppDatabase;
import com.example.myapplication6.Database.AppDatabaseSingleton;
import com.example.myapplication6.Database.TotalMedal;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DiffMedalUPDateFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LinearLayout linearLayout = new LinearLayout(getActivity());
        new DiffMedalThread().setCallbackInstance((map,total)->{
            List<TotalMedal> list = new ArrayList<>();
            SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyyyMMdd");
            Calendar calendar = Calendar.getInstance();
            int date =Integer.parseInt(simpleDateFormat.format(calendar.getTime()));

            for(String s:map.keySet()){
                TotalMedal totalMedal = new TotalMedal();
                totalMedal.modelNAME=s;
                totalMedal.diff=map.get(s);
                totalMedal.date=date;
                list.add(totalMedal);
            }
            AppDatabase appDatabase = AppDatabaseSingleton.getInstance(getActivity());
            appDatabase.allDao().insert(list);
        }).start();

        return linearLayout;
    }
}
