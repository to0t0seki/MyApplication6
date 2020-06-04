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

        List<TotalMedalTable> totalMedalTables = new ArrayList<>();
        //modelNOListを返す。
        List<String>list = getMachineNOs(status);
        for(String modelNO:list){
            //URLNOを与えてアクセス
            int total =getTotal(modelNO,status);
            TotalMedalTable totalMedalTable = new TotalMedalTable();
            totalMedalTable.date=date;
            totalMedalTable.diff=total;
            totalMedalTable.modelNO=Integer.parseInt(modelNO);
            totalMedalTables.add(totalMedalTable);
        }
        insertDatabase(totalMedalTables,appDatabase,handler,context);
    }

    static public void insertDatabase(List<TotalMedalTable> totalMedalTables, AppDatabase appDatabase, Handler handler, Context context){
        appDatabase.allDao().insert(totalMedalTables);
        handler.post(()->{
            Toast.makeText(context, "更新しました。", Toast.LENGTH_LONG).show();
        });

    }

    static public List<String> getMachineNOs(String status){
        List<String> modelList = new ArrayList<>();
        String url = "https://papimo.jp/h/00041817/hit/index_machine/"+status+"/";
        try {
            Document document = Jsoup.connect(url).timeout(10000).get();
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

    static public int getTotal(String modelNO,String status){
        String url = "https://papimo.jp/h/00041817/hit/index_sort/"+modelNO+"/" + status + "/81/1";
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
