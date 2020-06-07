package com.example.myapplication6;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class WebAccessThread extends Thread {
    CallbackInstance callbackInstance;
    String url;

    interface CallbackInstance{
        void CallbackMethod(Document document);
    }

    WebAccessThread(String url,CallbackInstance callbackInstance){
        this.url=url;
        this.callbackInstance=callbackInstance;
    }

    @Override
    public void run() {
        try {
            Document document = Jsoup.connect(url).timeout(8000).get();
            callbackInstance.CallbackMethod(document);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
