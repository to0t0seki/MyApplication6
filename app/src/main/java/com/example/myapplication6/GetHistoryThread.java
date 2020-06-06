package com.example.myapplication6;

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
import java.util.TreeMap;

public class GetHistoryThread extends Thread {
    String hallNO;
    List<String>unitNOs;
    CallbackInstance callbackInstance;

    interface CallbackInstance{
        void callbackMethod(Map<Integer,List<Map<String,String>>> map) throws IllegalAccessException;
    }

    public GetHistoryThread setCallbackInstance(String hallNO,List<String>unitNOs,CallbackInstance callbackInstance){
        this.hallNO=hallNO;
        this.unitNOs=unitNOs;
        this.callbackInstance=callbackInstance;
        return this;
    }
    @Override
    public void run() {
        Map<Integer,List<Map<String,String>>> map = getHistorys(hallNO,unitNOs);
        try {
            callbackInstance.callbackMethod(map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    static public Map<Integer,List<Map<String,String>>> getHistorys(String hallNO,List<String> unitNOs){
        Map<Integer,List<Map<String,String>>>historys  = new HashMap<>();
        for(String unitNO:unitNOs){
            String url = "https://papimo.jp/h/"+hallNO+ "/hit/view/"+ unitNO + "/20200605";
            List<Map<String,String>> historyList = new ArrayList<>();
            try{
                Document document = Jsoup.connect(url).timeout(10000).get();
                Elements historyTR = document.select(".history tr");
                Elements e = document.select(".data tr").eq(0);
                if (historyTR.size() != 0) {
                    historyTR.remove(0);
                    Collections.reverse(historyTR);
                    for (Element TRElement : historyTR) {
                        Map<String,String> map = new TreeMap<>();
                        map.put("cnt",TRElement.select(".cnt").text());
                        map.put("time",TRElement.select(".time").text());
                        map.put("start",TRElement.select(".start").text().replace(",", ""));
                        map.put("out",TRElement.select(".out").text().replace(",", "")==""?"0":TRElement.select(".out").text().replace(",", ""));
                        map.put("sts",TRElement.select(".sts").text());
                        historyList.add(map);
                    }
                    String last = document.select("#tab-data-some tbody tr").eq(0).select("td").eq(6).text();
                    Map<String,String> map = new HashMap();
                    map.put("cnt","");
                    map.put("time","");
                    map.put("start",last);
                    map.put("out","");
                    map.put("sts","");
                    historyList.add(map);
                }else if(document.select(".data tr").eq(1).select("td").eq(5).text()!="-"){
                    Map<String,String> map = new HashMap();
                    map.put("cnt","");
                    map.put("time","");
                    map.put("start",document.select(".data tr").eq(1).select("td").eq(5).text());
                    map.put("out","");
                    map.put("sts","");
                    historyList.add(map);
                }
            }catch (IOException e){
                System.out.println(e);
            }
            historys.put(Integer.parseInt(unitNO),historyList);
        }
        return historys;
    }
}
