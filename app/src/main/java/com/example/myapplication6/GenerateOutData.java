package com.example.myapplication6;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


public class GenerateOutData {

    static public OutData jagData(Map<Integer,Map<String,Integer>>map){
        List<Column> columns = new ArrayList<>();
        String[] headerName = {"NO","Total","BB","RB","Diff"};
        for (String s:headerName){
            switch (s){
                case "NO":
                    Column column1 = new Column();
                    column1.name= s;
                    column1.total=false;
                    column1.width=80;
                    columns.add(column1);
                    break;
                case "Total":
                case "Diff":
                    Column column2 = new Column();
                    column2.name= s;
                    column2.total=true;
                    column2.width=150;
                    columns.add(column2);
                    break;
                case "BB":
                case "RB":
                    Column column3 = new Column();
                    column3.name= s;
                    column3.total=true;
                    column3.width=80;
                    columns.add(column3);
                    break;
            }
        }
        OutData outData = new OutData();
        outData.columns = columns;
        outData.data = map;
        return  outData;
    }

//    static public  OutData kizuna2(Map<Integer,List<Map<String,String>>> historys) {
//
//
//        List<Column> columns = new ArrayList<>();
//        String[] headerName = {"NO","Total","IBC","IBT","DBC","DBT","TBC","TBT","toBC","toBT","RATE","HG","HBC"};
//        for (String s:headerName){
//            switch (s){
//                case "NO":
//                    Column column1 = new Column();
//                    column1.name= s;
//                    column1.total=false;
//                    column1.width=80;
//                    columns.add(column1);
//                    break;
//                case "Total":
//                    Column column2 = new Column();
//                    column2.name= s;
//                    column2.total=true;
//                    column2.width=150;
//                    columns.add(column2);
//                    break;
//                case "IBC":
//                case "DBC":
//                case "TBC":
//                case "IBT":
//                case "DBT":
//                case "TBT":
//                case "toBC":
//                case "toBT":
//                    Column column3 = new Column();
//                    column3.name= s;
//                    column3.total=true;
//                    column3.width=80;
//                    columns.add(column3);
//                    break;
//                case "RATE":
//                case "HG":
//                    Column column4 = new Column();
//                    column4.name= s;
//                    column4.total=false;
//                    column4.width=150;
//                    columns.add(column4);
//                    break;
//                case "HBC":
//                    Column column5 = new Column();
//                    column5.name= s;
//                    column5.total=false;
//                    column5.width=80;
//                    columns.add(column5);
//                    break;
//
//            }
//        }
//
//        OutData outData = new OutData();
//        outData.columns = columns;
//
//        Map<Integer,Map<String,Integer>> dataMap = new TreeMap<>();
//        List<Column> columns1 = new ArrayList<>();
//        Iterator<Integer> NOItr = historys.keySet().iterator();
//        while (NOItr.hasNext()){
//            int NO = NOItr.next();
//            Iterator<Map<String,String>> historyItr = historys.get(NO).iterator();
//            Map<String,Integer> mapFI = new LinkedHashMap<>();
//            String pre="";
//            int totalGame = 0;
//            int IBC = 0;
//            int DBC = 0;
//            int TBC = 0;
//            int IBT = 0;
//            int DBT = 0;
//            int TBT = 0;
//            int toBC = 0;
//            int toBT = 0;
//            int RATE = 0;
//            int hamariG = 0;
//            int hamariBC = 0;
//
//            while (historyItr.hasNext()) {
//                Map<String, String> history = historyItr.next();
//                if (!history.get("cnt").equals("end")) {
//                    if (Integer.parseInt(history.get("start")) > 1) {
//                        totalGame += Integer.parseInt(history.get("start"));
//                        hamariG += Integer.parseInt(history.get("start"));
//                        hamariBC += 1;
//                        if (hamariG > 805) {
//                            hamariG = 0;
//                            hamariBC = 0;
//                            TBC+=1;
//                            pre="TBC";
//                        } else {
//                            if(Integer.parseInt(history.get("out"))>34){
//                                DBC+=1;
//                                pre="DBC";
//                            }else{
//                                IBC+=1;
//                                pre="IBC";
//                            }
//                        }
//                        RATE+=Double.parseDouble(history.get("start")) * 0.144 + 26.7;
//                        toBC+=1;
//                    }else{
//                        hamariG=0;
//                        hamariBC=0;
//                        if(!pre.equals("BT")){
//                            if (pre.equals("TBC")){
//                                TBT+=1;
//                                pre="BT";
//                            }else if(pre.equals("DBC")){
//                                DBT+=1;
//                                pre="BT";
//                            }else{
//                                IBT+=1;
//                                pre="BT";
//                            }
//                            toBT+=1;
//                        }
//                    }
//                } else {
//                    totalGame += Integer.parseInt(history.get("start"));
//                    hamariG += Integer.parseInt(history.get("start"));
//                }
//            }
//            Column column = new Column();
//            column.name =
//            mapFI.put("TOTAL",totalGame);
//            mapFI.put("IBC",IBC);
//            mapFI.put("IBT",IBT);
//            mapFI.put("DBC",DBC);
//            mapFI.put("DBT",DBT);
//            mapFI.put("TBC",TBC);
//            mapFI.put("TBT",TBT);
//            mapFI.put("toBC",toBC);
//            mapFI.put("toBT",toBT);
//            mapFI.put("RATE",RATE);
//            mapFI.put("HG",hamariG);
//            mapFI.put("HBC",hamariBC);
//            dataMap.put(NO,mapFI);
//        }
//
//        outData.data = dataMap;
//        return outData;
//    }

//    static public  OutData  saraban2(Map<Integer,List<Map<String,String>>> historys) {
//        Map<String,Map<String,Object>> headerMap = new LinkedHashMap<>();
//        Map<String,Object> headerMapdetail = new HashMap<>();
//        headerMapdetail.put("width","80");
//        headerMap.put("NO",headerMapdetail);
//        headerMapdetail = new HashMap<>();
//        headerMapdetail.put("width","130");
//        headerMap.put("TOTAL",headerMapdetail);
//        headerMapdetail = new HashMap<>();
//        headerMapdetail.put("width","80");
//        headerMap.put("BB",headerMapdetail);
//        headerMap.put("RB",headerMapdetail);
//        headerMap.put("NRB",headerMapdetail);
//        headerMapdetail = new HashMap<>();
//        headerMapdetail.put("width","130");
//        headerMap.put("LAST",headerMapdetail);
//        OutData outData = new OutData();
//        outData.headerData = headerMap;
//
//        Map<Integer,Map<String,Integer>> mapNOs = new TreeMap<>();
//        Iterator<Integer> NOItr = historys.keySet().iterator();
//        while (NOItr.hasNext()){
//            int NO = NOItr.next();
//            Iterator<Map<String,String>> historyItr = historys.get(NO).iterator();
//            Map<String,Integer> mapFI = new LinkedHashMap<>();
//            String pre="";
//            int totalGame = 0;
//            int firstLukyBB = 0;
//            int firstLukyRB = 0;
//            int rushFromBB = 0;
//            int lastG = 0;
//            while (historyItr.hasNext()){
//                Map<String,String> history = historyItr.next();
//                if(!history.get("cnt").equals("end")){
//                    if(Integer.parseInt(history.get("start"))>2){
//                        totalGame+=Integer.parseInt(history.get("start"));
//                        if(history.get("sts").equals("BIG")){
//                            firstLukyBB+=1;
//                            pre="BB";
//                        }else if(history.get("sts").equals("REG")){
//                            firstLukyRB+=1;
//                            pre="RB";
//                        }else{
//                            lastG=Integer.parseInt(history.get("start"));
//                        }
//                    }else if(history.get("sts").equals("REG") && pre.equals("BB") ){
//                        rushFromBB+=1;
//                        pre="RB";
//                    }
//                }
//                else{
//                    totalGame+=Integer.parseInt(history.get("start"));
//                }
//            }
//            mapFI.put("Total",totalGame);
//            mapFI.put("BB",firstLukyBB);
//            mapFI.put("RB",rushFromBB);
//            mapFI.put("NRB",firstLukyRB);
//            mapFI.put("LastG",lastG);
//            mapNOs.put(NO,mapFI);
//        }
//        outData.data = mapNOs;
//        return outData;
//    }
}
