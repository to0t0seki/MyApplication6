package com.example.myapplication6;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

import com.example.myapplication6.Database.AppDatabase;
import com.example.myapplication6.Database.TotalMedalTable;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DiffMedalUpdateThread extends Thread {
    int date;
    AppDatabase appDatabase;
    Handler handler;
    Context context;
    String status;

    DiffMedalUpdateThread(int date,AppDatabase appDatabase,Handler handler,Context context,String status){
        this.date = date;
        this.appDatabase = appDatabase;
        this.handler = handler;
        this.context = context;
        this.status = status;
    }


    @Override
    public void run() {

        List<String>list = getModelNOs(status);
        List<TotalMedalTable> totalMedalTables = getTotalAll(list,status,date);
        insertDatabase(totalMedalTables,appDatabase,handler,context);
    }

    static public void insertDatabase(List<TotalMedalTable> totalMedalTables, AppDatabase appDatabase, Handler handler, Context context){
        appDatabase.allDao().insert(totalMedalTables);
        handler.post(()->{
            Toast.makeText(context, "更新しました。", Toast.LENGTH_LONG).show();
        });

    }

    static public List<String> getModelNOs(String status){
        List<String> modelList = new ArrayList<>();
        String url = "https://papimo.jp/h/00041817/hit/index_machine/"+status+"/";
        try {
            Document document = Jsoup.connect(url).timeout(5000).get();
            String maxpage = document.select("#max_page").val();
            for (int i = 0; i < Integer.parseInt(maxpage); i++) {
                document = Jsoup.connect(url + "?page=" + String.valueOf((i + 1))).timeout(10000).get();
                Elements elements = document.select(".item li a");
                for (Element element : elements) {
                    int startNAME = element.select(".name").text().indexOf("台") + 1;
                    int startURL = element.attr("href").indexOf("index_sort") + 11;
                   // map.put(element.select(".name").text().substring(startNAME),element.attr("href").substring(startURL, startURL + 9));
                    modelList.add(element.attr("href").substring(startURL, startURL + 9));
                }
            }
        }catch (IOException e){
            System.out.println(e);
        }
        return modelList;
    }

    static private Map<Integer,Map<Integer,Integer>> getTotal(String modelNo,String status,int date){
        Map<Integer,Map<Integer,Integer>> NoMap = new HashMap<>();
        String url = "https://papimo.jp/h/00041817/hit/index_sort/"+modelNo+"/" + status + "/81/1";
        try {
            Document document = Jsoup.connect(url).timeout(5000).get();
            Elements trElements = document.select("#table-sort tr");
            trElements.remove(0);
            for(Element trElement:trElements){
                Elements tdElements = trElement.select("td");
                int No = Integer.parseInt(tdElements.remove(0).text());
                Map<Integer,Integer> dateMap = new HashMap<>();
                int datetmp = date;
                for(Element tdElement:tdElements){
                    if(tdElement.text()!=""){
                        dateMap.put(datetmp,Integer.parseInt(tdElement.text().replace(",","")));
                    }
                    datetmp--;
                }
                NoMap.put(No,dateMap);
            }
        }catch (IOException e){
            System.out.println(e);
        }
        return NoMap;
    }

    static public List<TotalMedalTable> getTotalAll(List<String>list,String status,int date){
        List<TotalMedalTable> totalMedalTables = new ArrayList<>();
        for(String modelNO:list) {
            Map<Integer,Map<Integer,Integer>> unitNoMap =getTotal(modelNO,status,date);
            Map<Integer,Map<Integer,Integer>> newMap = ChangeMap3.change(unitNoMap);

            for(Map.Entry<Integer,Map<Integer,Integer>> dateEntry:newMap.entrySet()){
                int total=0;
                for(Map.Entry<Integer,Integer> noEntry :dateEntry.getValue().entrySet()){
                    total =noEntry.getValue();

                }
                TotalMedalTable totalMedalTable = new TotalMedalTable();
                totalMedalTable.date=dateEntry.getKey();
                totalMedalTable.diff=total;
                totalMedalTable.modelNO=Integer.parseInt(modelNO);
                totalMedalTables.add(totalMedalTable);
            }
        }
        return totalMedalTables;
    }
}

