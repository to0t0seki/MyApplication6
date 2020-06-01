package com.example.myapplication6;



import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class KizunaThread extends Thread {
    CallbackInstance callbackInstance;



    @Override
    public void run() {
        Map<String,Map<String,Integer>> resultMap = new TreeMap<>();
        Map<String,Integer> diffMap = getDiff();
        try {
            Document document = Jsoup.connect(" https://papimo.jp/h/00041817/hit/index_sort/220010002/1-20-266966").timeout(10000).get();
            Elements elements = document.select(".unit_no");


            for (Element element : elements) {
                String serialNO = element.text();
                Document oneDocument = Jsoup.connect("https://papimo.jp/h/00041817/hit/view/"+ serialNO + "/20200601").timeout(10000).get();


                Elements historyTR = oneDocument.select(".history tr");
                Elements e = oneDocument.select(".data tr").eq(0);
                String s = oneDocument.select(".data tr").eq(0).select("td").eq(5).text();
                if (historyTR.size() != 0) {
                    historyTR.remove(0);
                    Collections.reverse(historyTR);
                    List<List<String>> historyList = new ArrayList<>();
                    for (Element TRElement : historyTR) {
                        List<String> list = new ArrayList<>();
                        list.add(TRElement.select(".cnt").text());
                        list.add(TRElement.select(".time").text());
                        list.add(TRElement.select(".start").text().replace(",", ""));
                        list.add(TRElement.select(".out").text().replace(",", "")==""?"0":TRElement.select(".out").text().replace(",", ""));
                        list.add(TRElement.select(".sts").text());
                        historyList.add(list);
                    }
                    int last = Integer.parseInt(oneDocument.select("#tab-data-some tbody tr").eq(0).select("td").eq(6).text());
                    resultMap.put(serialNO,creatDetailData(historyList, last));
                }else if(oneDocument.select(".data tr").eq(1).select("td").eq(5).text()!="-"){
                    System.out.println(1);
                }
           }

        } catch (IOException e) {
            System.out.println(e);
        }
        for (String s:resultMap.keySet()){
            Map<String,Integer>m = resultMap.get(s);
            m.put("DIFF",diffMap.get(s));
            resultMap.put(s,m);
        }
        callbackInstance.callbackMethod(resultMap);

    }

    public Map<String,Integer>  getDiff(){
        Map<String,Integer> map = new TreeMap<>();
        try{
            Document document = Jsoup.connect("https://papimo.jp/h/00041817/hit/index_sort/220010002/1-20-266966/81/1").timeout(10000).get();
            Elements rowElements = document.select("#table-sort tr");
            rowElements.remove(0);
            for(Element element:rowElements){
                if(element.select("td").eq(1).text().replace(",","")!=""){
                    map.put(element.select("td").eq(0).text(),Integer.parseInt(element.select("td").eq(1).text().replace(",","")));
                }
            }
        }catch (IOException e){
            System.out.println(e);
        }
        return map;

    }

    public Map<String, Integer> creatDetailData(List<List<String>> list1, int last){
        int Ibc = 0;
        int Dbc = 0;
        int Tbc = 0;
        String BCType = null;

        int Ibt = 0;
        int Dbt = 0;
        int Tbt = 0;


        int hamariG = 0;
        int hamariBC = 0;
        double rate = 0;
        boolean through7 = false;
        boolean duringBT = false;
        for (List<String> list:list1) {
            if(list.get(4).equals("BIG") && Integer.parseInt(list.get(2)) > 1 && 806 >  hamariG + Integer.parseInt(list.get(2)) && hamariBC<6){

                int i = Integer.parseInt(list.get(3));
                if(Integer.parseInt(list.get(3))>34){
                    //同色の場合
                    Dbc+=1;
                    BCType = "D";
                }else {
                    //異色の場合
                    Ibc+=1;
                    BCType = "I";
                }
                rate += Double.parseDouble(list.get(2)) * 0.144 + 26.7;
                hamariG += Integer.parseInt(list.get(2));
                hamariBC += 1;

                duringBT = false;
            }else if(list.get(4).equals("BIG") && Integer.parseInt(list.get(2)) > 1){
                //天井処理
                Tbc += 1;
                rate += Double.parseDouble(list.get(2)) * 0.144 + 26.7;
                hamariG = 0;
                hamariBC = 0;
                BCType = "T";
            }else {
                if(duringBT == false){
                    duringBT = true;
                    hamariG = 0;
                    hamariBC = 0;
                    if(BCType=="D"){
                        Dbt+=1;
                    }else if(BCType=="I"){
                        Ibt+=1;
                    }else {
                        Tbt += 1;
                    }
                }
            }
        }
        Map<String,Integer> map = new TreeMap<>();
        map.put("IBC",Ibc);
        map.put("DBC",Dbc);
        map.put("TBC",Tbc);
        map.put("toBC",Ibc+Dbc+Tbc);
        map.put("IBT",Ibt);
        map.put("DBT",Dbt);
        map.put("TBT",Tbt);
        map.put("toBT",Ibt+Dbt+Tbt);
        map.put("HG",hamariG + last);
        map.put("HBC",hamariBC);
        map.put("RATE",(int) rate);
        return map;
    }

    interface CallbackInstance{
        void callbackMethod(Map<String, Map<String, Integer>> resultMap);
    }

    public KizunaThread setCallbackInstance(CallbackInstance callbackInstance) {
        this.callbackInstance = callbackInstance;
        return this;
    }

}

