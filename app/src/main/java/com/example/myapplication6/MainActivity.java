package com.example.myapplication6;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout baseLayout = findViewById(R.id.baselayout);
        LinearLayout topLayout = new LinearLayout(this);
        topLayout.setOrientation(LinearLayout.HORIZONTAL);

        SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance();
        int date =Integer.parseInt(simpleDateFormat.format(calendar.getTime()));
        Bundle bundle = new Bundle();
        bundle.putInt("DATE",date);

        DataViewModel dataViewModel = new ViewModelProvider(this,new ViewModelProvider.NewInstanceFactory()).get(DataViewModel.class);

        Button kizunaButton = new Button(this);
        kizunaButton.setText("絆データ");
        kizunaButton.setOnClickListener((v)->{
            new KizunaThread().setCallbackInstance((resultMap)->{
                dataViewModel.liveData.postValue(resultMap);
            }).start();

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.baselayout,new KizunaFragment());
            fragmentTransaction.commit();
        });

        Button diffButton = new Button(this);
        diffButton.setText("差枚データ");
        diffButton.setOnClickListener((v)->{
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            DiffFragment diffFragment = new DiffFragment();
            diffFragment.setArguments(bundle);
            fragmentTransaction.replace(R.id.baselayout,diffFragment);
            fragmentTransaction.commit();
        });

        Button diffUPdateButton = new Button(this);
        diffUPdateButton.setText("差枚データベース更新");
        diffUPdateButton.setOnClickListener((v)->{
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.baselayout,new DiffMedalUPDateFragment());
            fragmentTransaction.commit();
        });
        topLayout.addView(kizunaButton);
        topLayout.addView(diffButton);
        topLayout.addView(diffUPdateButton);
        baseLayout.addView(topLayout);

    }
}
