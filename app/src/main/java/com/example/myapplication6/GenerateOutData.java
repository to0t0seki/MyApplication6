package com.example.myapplication6;


import com.example.myapplication6.Data.Kizuna2;
import com.example.myapplication6.Data.Saraban2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class GenerateOutData {


    static public  List<Kizuna2> getkizuna2List(Map<Integer, List<Map<String,String>>> historys) {
        
        List<Kizuna2> kizuna2DataList = new ArrayList<>();
        
        Iterator<Integer> NOItr = historys.keySet().iterator();
        while (NOItr.hasNext()){
            Kizuna2 kizuna2Data = new Kizuna2();
            kizuna2Data.no = NOItr.next();
            Iterator<Map<String,String>> historyItr = historys.get(kizuna2Data.no).iterator();
            String pre="";
            while (historyItr.hasNext()) {
                Map<String, String> history = historyItr.next();
                if (!history.get("cnt").equals("end")) {
                    if (Integer.parseInt(history.get("start")) > 1) {
                        kizuna2Data.total += Integer.parseInt(history.get("start"));
                        kizuna2Data.hamariG += Integer.parseInt(history.get("start"));
                        kizuna2Data.hamariBc += 1;
                        if (kizuna2Data.hamariG > 805) {
                            kizuna2Data.hamariG = 0;
                            kizuna2Data.hamariBc = 0;
                            kizuna2Data.tbc+=1;
                            pre="TBC";
                        } else {
                            if(Integer.parseInt(history.get("out"))>34){
                                kizuna2Data.dbc+=1;
                                pre="DBC";
                            }else{
                                kizuna2Data.ibc+=1;
                                pre="IBC";
                            }
                        }
                        kizuna2Data.rate+=Double.parseDouble(history.get("start")) * 0.144 + 26.7;
                    }else{
                        kizuna2Data.hamariG=0;
                        kizuna2Data.hamariBc=0;
                        if(!pre.equals("BT")){
                            if (pre.equals("TBC")){
                                kizuna2Data.tbt+=1;
                                pre="BT";
                            }else if(pre.equals("DBC")){
                                kizuna2Data.dbt+=1;
                                pre="BT";
                            }else{
                                kizuna2Data.ibt+=1;
                                pre="BT";
                            }
                        }
                    }
                } else {
                    kizuna2Data.total += Integer.parseInt(history.get("start"));
                    kizuna2Data.hamariG += Integer.parseInt(history.get("start"));
                }
            }
            kizuna2DataList.add(kizuna2Data);
        }
        return kizuna2DataList;
    }
    
    static public List<List<Object>> genKizuna2Table(List<Kizuna2>kizuna2List){
        List<List<Object>> objectList = new ArrayList<>();
        int totalTotal=0;
        int totalibc=0;
        int totaldbc=0;
        int totaltbc=0;
        int totalibt=0;
        int totaldbt=0;
        int totaltbt=0;
        int totaltobc=0;
        int totaltobt=0;
        for(Kizuna2 kizuna2:kizuna2List){
            List<Object> list = new ArrayList<>();
            list.add(kizuna2.no);
            list.add(kizuna2.total);
            list.add(kizuna2.ibc);
            list.add(kizuna2.ibt);
            list.add(kizuna2.dbc);
            list.add(kizuna2.dbt);
            list.add(kizuna2.tbc);
            list.add(kizuna2.tbt);
            list.add(kizuna2.getToBC());
            list.add(kizuna2.getToBT());
            list.add(kizuna2.rate);
            list.add(kizuna2.hamariG);
            list.add(kizuna2.hamariBc);
            //list.add(kizuna2.diff);
            objectList.add(list);
            
            totalTotal+=kizuna2.total;
            totalibc+=kizuna2.ibc;
            totaldbc+=kizuna2.dbc;
            totaltbc+=kizuna2.tbc;
            totalibt+=kizuna2.ibt;
            totaldbt+=kizuna2.dbt;
            totaltbt+=kizuna2.tbt;
            totaltobc+=kizuna2.getToBC();
            totaltobt+=kizuna2.getToBT();
        }
        List<Object> totalList = new ArrayList<>();
        totalList.add("");
        totalList.add(totalTotal);
        totalList.add(totalibc);
        totalList.add(totalibt);
        totalList.add(totaldbc);
        totalList.add(totaldbt);
        totalList.add(totaltbc);
        totalList.add(totaltbt);
        totalList.add(totaltobc);
        totalList.add(totaltobt);

        objectList.add(totalList);
      
        return objectList;
    } 

    static public  List<Saraban2>  getSaraban2List(Map<Integer,List<Map<String,String>>> historys) {
        Iterator<Integer> NOItr = historys.keySet().iterator();
        List<Saraban2> saraban2List = new ArrayList<>();
        while (NOItr.hasNext()){
            Saraban2 saraban2 = new Saraban2();
            saraban2.no = NOItr.next();
            Iterator<Map<String,String>> historyItr = historys.get(saraban2.no).iterator();
            String pre="";
            while (historyItr.hasNext()){
                Map<String,String> history = historyItr.next();
                if(!history.get("cnt").equals("end")){
                    if(Integer.parseInt(history.get("start"))>2){
                        saraban2.total+=Integer.parseInt(history.get("start"));
                        if(history.get("sts").equals("BIG")){
                            saraban2.bb+=1;
                            pre="BB";
                        }else if(history.get("sts").equals("REG")){
                            saraban2.nrb+=1;
                            pre="RB";
                        }else{
                            saraban2.hamariG=Integer.parseInt(history.get("start"));
                        }
                    }else if(history.get("sts").equals("REG") && pre.equals("BB") ){
                        saraban2.rb+=1;
                        pre="RB";
                    }
                }
                else{
                    saraban2.total+=Integer.parseInt(history.get("start"));
                    saraban2.hamariG+=Integer.parseInt(history.get("start"));
                }
            }
            saraban2List.add(saraban2);
        }
        return saraban2List;
    }

    static public List<List<Object>> genSaraban2Table(List<Saraban2>saraban2List){
        List<List<Object>> objectList = new ArrayList<>();
        int totalTotal=0;
        int totalbb=0;
        int totalrb=0;
        int totalnrb=0;

        for(Saraban2 saraban2:saraban2List){
            List<Object> list = new ArrayList<>();
            list.add(saraban2.no);
            list.add(saraban2.total);
            list.add(saraban2.bb);
            list.add(saraban2.rb);
            list.add(saraban2.nrb);
            list.add(saraban2.hamariG);
            //list.add(kizuna2.diff);
            objectList.add(list);

            totalTotal+=saraban2.total;
            totalbb+=saraban2.bb;
            totalrb+=saraban2.rb;
            totalnrb+=saraban2.nrb;
        }
        List<Object> totalList = new ArrayList<>();
        totalList.add("");
        totalList.add(totalTotal);
        totalList.add(totalbb);
        totalList.add(totalrb);
        totalList.add(totalnrb);

        objectList.add(totalList);

        return objectList;
    }
}
