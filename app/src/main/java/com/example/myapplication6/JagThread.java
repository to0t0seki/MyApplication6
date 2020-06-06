package com.example.myapplication6;

import androidx.annotation.NonNull;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import java.util.Map;
import java.util.TreeMap;

public class JagThread extends Thread {
    CallbackInstance callbackInstance;
    String modelNO;

    interface CallbackInstance{
        void callbackMethod(Map<String,Map<String,Integer>>map);
    }

    public JagThread setCallbackInstance(String modelNO,CallbackInstance callbackInstance) {
        this.callbackInstance = callbackInstance;
        this.modelNO = modelNO;
        return this;
    }

    @Override
    public void run() {
        Map<String,Map<String,Integer>> map = getDATA("00041817",modelNO);
        callbackInstance.callbackMethod(map);

    }

    static public Map<String,Map<String,Integer>> getDATA(String hallNO,String modelNO){
        String[] stutus = {"81/1","1/0","2/0","6/0"};
        Map<String,Map<String,Integer>> map = new TreeMap<>();
        for(String s:stutus){
            String url = "https://papimo.jp/h/"+hallNO+ "/hit/index_sort/"+ modelNO+"/1-20-268759/"+s;
            try{
                Document document = Jsoup.connect(url).timeout(10000).get();
                Elements trElements = document.select("#table-sort tr");
                trElements.remove(0);
                Map<String,Integer> mapdetail = new TreeMap<>();
                for(Element e:trElements){

                    if(e.select("td").eq(1).text()!=""){
                        mapdetail.put(e.select("td").eq(0).text(),Integer.parseInt(e.select("td").eq(1).text().replace(",","")));
                    }
                }
                map.put(s,mapdetail);
            }catch(IOException e){
                System.out.println(e);
            }
        }
        return map;
    }
}
