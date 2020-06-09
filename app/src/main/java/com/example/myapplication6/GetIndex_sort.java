package com.example.myapplication6;


import android.widget.TableLayout;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GetIndex_sort extends Thread {
    CallbackInstance callbackInstance;
    String hallNO;
    String modelNO;
    int date;


    interface CallbackInstance{
        void callbackMethod(List<List<Object>> tableList);
    }

    GetIndex_sort(String hallNO,String modelNO,int date,CallbackInstance callbackInstance){
        this.hallNO=hallNO;
        this.modelNO=modelNO;
        this.date=date;
        this.callbackInstance=callbackInstance;
    }

    @Override
    public void run() {
        Map<String,Map<Integer,Map<Integer,Integer>>> map = getData(hallNO,modelNO,Global.date);
        List<Index_sortData>list = generateIndex_sortData(map);
        List<Index_sortData>dateList = getDateData(list,date);
        List<List<Object>> tablelist = generateTable(dateList);
        callbackInstance.callbackMethod(tablelist);
    }

    static public List<List<Object>>  generateTable(List<Index_sortData>list){
        Collections.sort(list,(i1,i2)->{return i1.no-i2.no;});
        List<List<Object>> tableList = new ArrayList<>();
        int totalTotal=0;
        int bbTotal=0;
        int rbTotal=0;
        int diffTotal=0;
        for (Index_sortData index_sortData:list){
            List<Object> TRList = new ArrayList<>();
            double k =(double) index_sortData.total/((index_sortData.bb*312 + index_sortData.rb*104)-index_sortData.diff)*50;
            k = ((double)Math.round(k * 100))/100;
            TRList.add(index_sortData.no);
            TRList.add(index_sortData.total);
            TRList.add(index_sortData.bb);
            TRList.add(index_sortData.rb);
            TRList.add(index_sortData.diff);
            TRList.add(k);

            totalTotal+=index_sortData.total;
            bbTotal+=index_sortData.bb;
            rbTotal+=index_sortData.rb;
            diffTotal+=index_sortData.diff;

            tableList.add(TRList);
        }
        List<Object> TRList = new ArrayList<>();
        TRList.add("");
        TRList.add(totalTotal);
        TRList.add(bbTotal);
        TRList.add(rbTotal);
        TRList.add(diffTotal);
        tableList.add(TRList);
        return tableList;


    }
    static public List<Index_sortData> getDateData(List<Index_sortData>list,int date){
        List<Index_sortData> newindex_sortData = new ArrayList<>();
        for(Index_sortData index_sortData:list){
            if(index_sortData.date==date){
                newindex_sortData.add(index_sortData);
            }
        }
        return newindex_sortData;
    }

    public static List<Index_sortData> generateIndex_sortData(Map<String,Map<Integer,Map<Integer,Integer>>> map){
        Map<Integer,Map<Integer,Map<String,Integer>>> newMap = ChangeMap4.change(map);
        List<Index_sortData> list = new ArrayList<>();
        for (int date:newMap.keySet()){
            for(int no:newMap.get(date).keySet()){
                Index_sortData index_sortData = new Index_sortData();
                index_sortData.date=date;
                index_sortData.no = no;
                index_sortData.diff = newMap.get(date).get(no).get("81/1");
                index_sortData.bb=newMap.get(date).get(no).get("1/0");
                index_sortData.rb=newMap.get(date).get(no).get("2/0");
                index_sortData.total=newMap.get(date).get(no).get("6/0");
                list.add(index_sortData);
            }
        }
        return list;
    }

    public Map<String,Map<Integer,Map<Integer,Integer>>> getData(String hall, String modelNO, int date){
        String[] categorys = {"1/0","2/0","6/0","81/1"};
        Map<String,Map<Integer,Map<Integer,Integer>>> cateMap = new HashMap<>();
        for(String category:categorys){
            String url = "https://papimo.jp/h/"+hall+"/hit/index_sort/"+modelNO+"/1-20-269695/"+category;
            try {
                Document document = Jsoup.connect(url).timeout(8000).get();
                Elements trElements = document.select("#table-sort tr");
                trElements.remove(0);
                Map<Integer,Map<Integer,Integer>> NOMap = new HashMap<>();
                for(Element trElement:trElements){
                    Elements tdElements = trElement.select("td");
                    String NO = tdElements.remove(0).text();
                    Map<Integer,Integer> dateMap = new HashMap<>();
                    int tmpDate = date;
                    for (Element tdElement:tdElements){
                        if(tdElement.text()!=""){
                            dateMap.put(tmpDate,Integer.parseInt(tdElement.text().replace(",","")));
                            tmpDate-=1;
                        }
                    }
                    NOMap.put(Integer.parseInt(NO),dateMap);
                }
                cateMap.put(category,NOMap);
            }catch (IOException e){
                System.out.println(e);
            }
        }
        return cateMap;
    }
}
