package com.example.myapplication6;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


public class CalcData {

    static public  Map<Integer,Map<String,Integer>>  saraban2(Map<Integer,List<Map<String,String>>> historys) {
        Map<Integer,Map<String,Integer>> mapNOs = new TreeMap<>();
        Iterator<Integer> NOItr = historys.keySet().iterator();
        while (NOItr.hasNext()){
            int NO = NOItr.next();
            Iterator<Map<String,String>> historyItr = historys.get(NO).iterator();
            Map<String,Integer> mapFI = new TreeMap<>();
            String pre="";
            int totalGame = 0;
            int firstLukyBB = 0;
            int firstLukyRB = 0;
            int rushFromBB = 0;
            int lastG = 0;
            while (historyItr.hasNext()){
                Map<String,String> history = historyItr.next();
                if(Integer.parseInt(history.get("start"))>2){
                    totalGame+=Integer.parseInt(history.get("start"));
                    if(history.get("sts").equals("BIG")){
                        firstLukyBB+=1;
                        pre="BB";
                    }else if(history.get("sts").equals("REG")){
                        firstLukyRB+=1;
                        pre="RB";
                    }else{
                        lastG=Integer.parseInt(history.get("start"));
                    }
                }else if(history.get("sts").equals("REG") && pre.equals("BB") ){
                    rushFromBB+=1;
                    pre="RB";
                }
            }
            mapFI.put("BB",firstLukyBB);
            mapFI.put("RB",rushFromBB);
            mapFI.put("NRB",firstLukyRB);
            mapFI.put("Total",totalGame);
            mapFI.put("LastG",lastG);
            mapNOs.put(NO,mapFI);
        }
        return mapNOs;
    }
}
