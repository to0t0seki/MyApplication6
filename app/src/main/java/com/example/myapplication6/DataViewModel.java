package com.example.myapplication6;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Map;

public class DataViewModel extends ViewModel {
    MutableLiveData<Map> liveData = new MutableLiveData<>();
    MutableLiveData<Map> MedalLiveData = new MutableLiveData<>();
    String updateTime;



}
