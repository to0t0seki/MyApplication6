package com.example.myapplication6;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GetIndex_sort extends Thread {
    CallbackInstance callbackInstance;
    String hallNO;
    String modelNO;
    int date;


    interface CallbackInstance{
        void callbackMethod(List<Index_sortData> list);
    }

    GetIndex_sort(String hallNO,String modelNO,int date,CallbackInstance callbackInstance){
        this.hallNO=hallNO;
        this.modelNO=modelNO;
        this.date=date;
        this.callbackInstance=callbackInstance;
    }

    @Override
    public void run() {
        Map<String,Map<Integer,Map<Integer,Integer>>> map = getData(hallNO,modelNO,date);
        List<Index_sortData>list = generateIndex_sortData(map);
        callbackInstance.callbackMethod(list);
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
