package com.example.myapplication6;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication6.Database.AppDatabase;
import com.example.myapplication6.Database.AppDatabaseSingleton;
import com.example.myapplication6.Database.TotalMedal;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class DiffFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LinearLayout linearLayout = new LinearLayout(getActivity());
        Bundle bundle = getArguments();
        int i = bundle.getInt("DATE");
        AppDatabase appDatabase = AppDatabaseSingleton.getInstance(getActivity());
        new DatabeseAccessThread().setCallbackInsetance(appDatabase,i,(list)->{
            System.out.println(list);

        }).start();


        return linearLayout;
    }
}
