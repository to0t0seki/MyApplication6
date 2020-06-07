package com.example.myapplication6;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

public class GetIndex_sort {
    CallbackInstance callbackInstance;

    interface CallbackInstance{
        void callbackMethod(Map map);
    }

    GetIndex_sort(CallbackInstance callbackInstance){
        this.callbackInstance=callbackInstance;
    }

    public void getData(String hall, String modelNO, String category, int date){
        SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance();
        int today =Integer.parseInt(simpleDateFormat.format(calendar.getTime()));
        int tdNO = today - date + 1;
        String url = "https://papimo.jp/h/"+hall+"/hit/index_sort/"+modelNO+"/1-20-269695/"+category;
        new WebAccessThread(url,(document)->{
            Elements trElements = document.select("#table-sort tr");
            trElements.remove(0);
            Map<String,Integer> mapdetail = new TreeMap<>();
            for(Element e:trElements){
                if(e.select("td").eq(tdNO).text()!=""){
                    mapdetail.put(e.select("td").eq(0).text(),Integer.parseInt(e.select("td").eq(tdNO).text().replace(",","")));
                }
            }
            callbackInstance.callbackMethod(mapdetail);
        }).start();
    }

    public void getDataAll(String hall,String modelNO,int date){
        Map<String,Map<String,Integer>> map = new LinkedHashMap<>();
            new GetIndex_sort((map81)->{
                new GetIndex_sort((map1)->{
                    new GetIndex_sort((map2)->{
                        new GetIndex_sort((map6)->{
                            map.put("81/0",map81);
                            map.put("1/0",map1);
                            map.put("2/0",map2);
                            map.put("6/0",map6);
                            callbackInstance.callbackMethod(map);
                        }).getData(hall,modelNO,"6/1",date);
                    }).getData(hall,modelNO,"2/0",date);
                }).getData(hall,modelNO,"1/0",date);
            }).getData(hall,modelNO,"81/1",date);
    }
}
