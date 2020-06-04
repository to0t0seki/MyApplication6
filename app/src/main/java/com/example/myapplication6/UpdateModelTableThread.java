package com.example.myapplication6;


import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

import com.example.myapplication6.Database.AppDatabase;
import com.example.myapplication6.Database.ModelTable;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class UpdateModelTableThread extends Thread {
    Context context;
    Handler handler;
    AppDatabase appDatabase;
    String hallNO;
    String[] status;

    UpdateModelTableThread(Context context, Handler handler, AppDatabase appDatabase,String hallNO, String[] status){
        this.context = context;
        this.handler = handler;
        this.appDatabase = appDatabase;
        this.hallNO = hallNO;
        this.status = status;
    }

    @Override
    public void run() {
        for (String s:status){
            List<ModelTable> modelTables = modelTables = getWebData(hallNO,s);
            upDateDatabase(modelTables,appDatabase);
        }


        handler.post(()->{
            Toast.makeText(context, "更新しました。", Toast.LENGTH_LONG).show();
        });
    }

    static public List<ModelTable> getWebData(String hallNO,String status){

        String url = "https://papimo.jp/h/"+hallNO+"/hit/index_machine/"+status+"/";

        List<ModelTable> list = new ArrayList<>();
        try {
            Document document = Jsoup.connect(url).timeout(10000).get();
            String maxpage = document.select("#max_page").val();
            for (int i = 0; i < Integer.parseInt(maxpage); i++) {
                document = Jsoup.connect(url + "?page=" + String.valueOf((i + 1))).timeout(10000).get();
                Elements elements = document.select(".item li a");
                for (Element element : elements) {
                    ModelTable modelTable = new ModelTable();
                    int indexname = element.select(".name").text().indexOf("台") + 1;
                    int indexurl = element.attr("href").indexOf("index_sort") + 11;
                    modelTable.modelNAME = element.select(".name").text().substring(indexname);
                    modelTable.modelNO = Integer.parseInt(element.attr("href").substring(indexurl, indexurl + 9));
                    list.add(modelTable);
                }
        }
        }catch (IOException e){
            System.out.println(e);
        }
        return list;
    }

    static public void upDateDatabase(List<ModelTable> modelTables,AppDatabase appDatabase){
        appDatabase.allDao().insertModelTable(modelTables);
    }
}
