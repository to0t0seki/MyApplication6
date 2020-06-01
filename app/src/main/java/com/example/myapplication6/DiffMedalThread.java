package com.example.myapplication6;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class DiffMedalThread extends Thread {
    CallbackInstance callbackInstance;

    interface CallbackInstance{
        public void callbackMethod(Map<String,Integer> map,int total);
    }

    public DiffMedalThread setCallbackInstance(CallbackInstance callbackInstance){
        this.callbackInstance=callbackInstance;
        return this;
    }

    @Override
    public void run() {
        int total =0;
        Map<String,Integer> totalMap = new HashMap<>();
        //HashMap型<機種名,URLにも使われる機種NO>で返す
        Map<String,String>map = getMachineList();
        for(String s:map.keySet()){
            //URLNOを与えてアクセス
            int i =getTotal(map.get(s));
            totalMap.put(s,i);
            total +=i;
        }
        callbackInstance.callbackMethod(totalMap,total);
    }

    public Map<String,String> getMachineList(){
        Map<String,String> map = new HashMap<>();
        String url = "https://papimo.jp/h/00041817/hit/index_machine/1-20-260540/";
        try {
            Document document = Jsoup.connect(url).timeout(10000).get();
            String maxpage = document.select("#max_page").val();
            for (int i = 0; i < Integer.parseInt(maxpage); i++) {
                document = Jsoup.connect(url + "?page=" + String.valueOf((i + 1))).timeout(10000).get();
                Elements elements = document.select(".item li a");
                for (Element element : elements) {
                    int indexname = element.select(".name").text().indexOf("台") + 1;
                    int indexurl = element.attr("href").indexOf("index_sort") + 11;
                    map.put(element.select(".name").text().substring(indexname),element.attr("href").substring(indexurl, indexurl + 9));
                }
            }
        }catch (IOException e){
            System.out.println(e);
        }
        return map;
    }

    public int getTotal(String urlNO){
        String url = "https://papimo.jp/h/00041817/hit/index_sort/"+urlNO+"/1-20-268759/81/1";
        int total=0;
        Document document=null;
        try {
            document = Jsoup.connect(url).timeout(10000).get();
        }catch (IOException e){
            System.out.println(e);
        }
        Elements trElements = document.select("#table-sort tr");
        trElements.remove(0);
        for(Element e:trElements){
            if(e.select("td").eq(1).text()!=""){
                total +=Integer.parseInt(e.select("td").eq(1).text().replace(",",""));
            }
        }
        return total;
    }
}
