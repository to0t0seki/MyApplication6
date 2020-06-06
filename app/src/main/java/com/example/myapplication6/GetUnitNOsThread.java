package com.example.myapplication6;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class GetUnitNOsThread extends Thread {
    String modelNO;
    String hallNO;
    CallbackInstance callbackInstance;

    interface CallbackInstance{
        void callbackMethod(List<String> unitNOs);
    }

    public GetUnitNOsThread setCallbackInstance(String hallNO,String modelNO,CallbackInstance callbackInstance){
        this.hallNO=hallNO;
        this.modelNO=modelNO;
        this.callbackInstance=callbackInstance;
        return this;
    }
    @Override
    public void run() {
        List<String> unitNOs = getUnitlNOs(hallNO,modelNO);
        callbackInstance.callbackMethod(unitNOs);
    }

    static public List<String> getUnitlNOs(String hallNO,String modelNO){
        List<String> unitNOs = new ArrayList<>();
        String url = "https://papimo.jp/h/"+hallNO+ "/hit/index_sort/"+ modelNO+"/1-20-268759/";
        try{
            Document document = Jsoup.connect(url).timeout(10000).get();
            Elements unitNOElemets = document.select(".unit_no");
            for (Element element : unitNOElemets) {
                unitNOs.add(element.text());
            }
        }catch (IOException e){
            System.out.println(e);
        }
        return unitNOs;
    }
}
