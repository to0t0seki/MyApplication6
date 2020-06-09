package com.example.myapplication6.Data;

public class Kizuna2 {
    public int no;
    public int ibc;
    public int dbc;
    public int tbc;
    public int ibt;
    public int dbt;
    public int tbt;
    public int total;
    public int diff;
    public int rate;
    public int hamariG;
    public int hamariBc;

    public int getToBC(){
        return ibc+dbc+tbc;
    }
    public int getToBT(){
        return ibt+dbt+tbt;
    }
}
