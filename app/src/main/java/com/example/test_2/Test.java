package com.example.test_2;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;


import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

import static android.content.Context.MODE_PRIVATE;


public class Test {
    Context cont;
    Document doc;
    Elements table;
    Jsoup jsoup;
    Thread thread;
    Connection.Response response = null;
    final String SAVED_TEXT = "saved_text";
    public void Start(){

    }
    public void TestPars() {
//        jsoup.connect("http://e-portal.wksu.kz/schedule/search").userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/89.0.4389.82 Safari/537.36");
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    doc =jsoup.connect("http://e-portal.wku.edu.kz/schedule/search").userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/89.0.4389.82 Safari/537.36")
                            .data("yform_d1d66f2b: ","1")
                            .data("ScheduleStudentForm[branch]: ", "1")
                            .data("ScheduleStudentForm[faculty]: ", "66")
                            .data("ScheduleStudentForm[speciality]: ","310")
                            .data("ScheduleStudentForm[group]: ", "19166")
                            .data("ScheduleStudentForm[week]: ","TT_2_15_02_2021").post();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d("MyLog", "run: "+doc.text());
    }
}





