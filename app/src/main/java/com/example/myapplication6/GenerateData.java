package com.example.myapplication6;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


public class GenerateData {

    static class OutData{
        Map<Integer,Map<String,Integer>> data;
        Map<String,Map<String,String>> headerData;
    }

    static public  OutData kizuna2(Map<Integer,List<Map<String,String>>> historys) {

        Map<String,Map<String,String>> headerMap = new LinkedHashMap<>();
        Map<String,String> headerMapdetail = new HashMap<>();
        headerMapdetail.put("width","80");
        headerMap.put("NO",headerMapdetail);
        headerMapdetail = new HashMap<>();
        headerMapdetail.put("width","110");
        headerMap.put("TOTAL",headerMapdetail);
        headerMapdetail = new HashMap<>();
        headerMapdetail.put("width","80");
        headerMap.put("IBC",headerMapdetail);
        headerMap.put("DBC",headerMapdetail);
        headerMap.put("TBC",headerMapdetail);
        headerMap.put("IBT",headerMapdetail);
        headerMap.put("DBT",headerMapdetail);
        headerMap.put("TBT",headerMapdetail);
        headerMap.put("toBC",headerMapdetail);
        headerMap.put("toBT",headerMapdetail);
        headerMapdetail = new HashMap<>();
        headerMapdetail.put("width","110");
        headerMap.put("RATE",headerMapdetail);
        headerMapdetail = new HashMap<>();
        headerMapdetail.put("width","80");
        headerMap.put("HG",headerMapdetail);
        headerMap.put("HBC",headerMapdetail);
        OutData outData = new OutData();
        outData.headerData = headerMap;

        Map<Integer,Map<String,Integer>> dataMap = new TreeMap<>();
        Iterator<Integer> NOItr = historys.keySet().iterator();
        while (NOItr.hasNext()){
            int NO = NOItr.next();
            Iterator<Map<String,String>> historyItr = historys.get(NO).iterator();
            Map<String,Integer> mapFI = new LinkedHashMap<>();
            String pre="";
            int totalGame = 0;
            int IBC = 0;
            int DBC = 0;
            int TBC = 0;
            int IBT = 0;
            int DBT = 0;
            int TBT = 0;
            int toBC = 0;
            int toBT = 0;
            int RATE = 0;
            int hamariG = 0;
            int hamariBC = 0;

            while (historyItr.hasNext()) {
                Map<String, String> history = historyItr.next();
                if (!history.get("cnt").equals("end")) {
                    if (Integer.parseInt(history.get("start")) > 1) {
                        totalGame += Integer.parseInt(history.get("start"));
                        hamariG += Integer.parseInt(history.get("start"));
                        hamariBC += 1;
                        if (hamariG > 805) {
                            hamariG = 0;
                            hamariBC = 0;
                            TBC+=1;
                            pre="TBC";
                        } else {
                            if(Integer.parseInt(history.get("out"))>34){
                                DBC+=1;
                                pre="DBC";
                            }else{
                                IBC+=1;
                                pre="IBC";
                            }
                        }
                        RATE+=Double.parseDouble(history.get("start")) * 0.144 + 26.7;
                        toBC+=1;
                    }else{
                        hamariG=0;
                        hamariBC=0;
                        if(!pre.equals("BT")){
                            if (pre.equals("TBC")){
                                TBT+=1;
                                pre="BT";
                            }else if(pre.equals("DBC")){
                                DBT+=1;
                                pre="BT";
                            }else{
                                IBT+=1;
                                pre="BT";
                            }
                            toBT+=1;
                        }
                    }
                } else {
                    totalGame += Integer.parseInt(history.get("start"));
                    hamariG += Integer.parseInt(history.get("start"));
                }
            }
            mapFI.put("TOTAL",totalGame);
            mapFI.put("IBC",IBC);
            mapFI.put("DBC",DBC);
            mapFI.put("TBC",TBC);
            mapFI.put("IBT",IBT);
            mapFI.put("DBT",DBT);
            mapFI.put("TBT",TBT);
            mapFI.put("toBC",toBC);
            mapFI.put("toBT",toBT);
            mapFI.put("RATE",RATE);
            mapFI.put("HG",hamariG);
            mapFI.put("HBC",hamariBC);
            dataMap.put(NO,mapFI);
        }

        outData.data = dataMap;
        return outData;
    }

    static public  OutData  saraban2(Map<Integer,List<Map<String,String>>> historys) {
        Map<String,Map<String,String>> headerMap = new LinkedHashMap<>();
        Map<String,String> headerMapdetail = new HashMap<>();
        headerMapdetail.put("width","80");
        headerMap.put("NO",headerMapdetail);
        headerMapdetail = new HashMap<>();
        headerMapdetail.put("width","110");
        headerMap.put("TOTAL",headerMapdetail);
        headerMapdetail = new HashMap<>();
        headerMapdetail.put("width","80");
        headerMap.put("BB",headerMapdetail);
        headerMap.put("RB",headerMapdetail);
        headerMap.put("NRB",headerMapdetail);
        headerMapdetail = new HashMap<>();
        headerMapdetail.put("width","110");
        headerMap.put("LAST",headerMapdetail);
        OutData outData = new OutData();
        outData.headerData = headerMap;

        Map<Integer,Map<String,Integer>> mapNOs = new TreeMap<>();
        Iterator<Integer> NOItr = historys.keySet().iterator();
        while (NOItr.hasNext()){
            int NO = NOItr.next();
            Iterator<Map<String,String>> historyItr = historys.get(NO).iterator();
            Map<String,Integer> mapFI = new LinkedHashMap<>();
            String pre="";
            int totalGame = 0;
            int firstLukyBB = 0;
            int firstLukyRB = 0;
            int rushFromBB = 0;
            int lastG = 0;
            while (historyItr.hasNext()){
                Map<String,String> history = historyItr.next();
                if(!history.get("cnt").equals("end")){
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
                else{
                    totalGame+=Integer.parseInt(history.get("start"));
                }
            }
            mapFI.put("Total",totalGame);
            mapFI.put("BB",firstLukyBB);
            mapFI.put("RB",rushFromBB);
            mapFI.put("NRB",firstLukyRB);
            mapFI.put("LastG",lastG);
            mapNOs.put(NO,mapFI);
        }
        outData.data = mapNOs;
        return outData;
    }
}
