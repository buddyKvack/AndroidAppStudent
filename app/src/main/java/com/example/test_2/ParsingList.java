package com.example.test_2;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
public class ParsingList extends MainActivity{
    static Thread t3;
    String getData,ValueFac,ValueSpec,ValueGroup;
    private Thread thread;
    private Runnable run;
    String[] WeekDay;
    Document doc;
    Elements table;
    Element table_1,Week;
    Jsoup jsoup;
    SharedPreferences Table;
    Context context;
    int error_count=0;
    boolean isOk=false;
    ParsingList (SharedPreferences Table, Context context,String getData,String ValueFac,String ValueSpec,String ValueGroup){
        this.Table =Table;
        this.context=context;
        this.getData=getData;
        this.ValueFac=ValueFac;
        this.ValueSpec=ValueSpec;
        this.ValueGroup=ValueGroup;
    }
    ParsingList(){
    }
    public void StartThreadParsing() {
        bar.setVisibility(View.VISIBLE);
        run = new Runnable() {
            @Override
            public void run() {
                Parsing();
                ParsWeekDay();
            }
        };
        thread = new Thread(run);
        thread.start();
        thread.isAlive();
    new Thread(new Runnable() {
        @Override
        public void run() {
            try {thread.join();
                if(doc!=null) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Days dayclass = new Days(list1, Table, context, day, thread,indexDay);
                            dayclass.Start();
                            NewWeek newWeek = new NewWeek(WeekDay,Table,context);
                            newWeek.SaveDateBase();
                        }
                    });
                }
            }catch (Exception e){ }
        }
    }).start();
    }
    String[] day;
    public String[] list1;
    int[] indexDay;
    public void Parsing() {
        if(error_count==4){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    MainActivity.AdapterViewList(context,false,true);
                }
            });
        }
        if(doc==null){
            try {
                doc = Jsoup.connect("http://e-portal.wku.edu.kz/schedule/search").userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/89.0.4389.82 Safari/537.36")
                        .data("yform_d1d66f2b: ","1")
                        .data("ScheduleStudentForm[branch]: ", "1")
                        .data("ScheduleStudentForm[faculty]: ", ValueFac)
                        .data("ScheduleStudentForm[speciality]: ", ValueSpec)
                        .data("ScheduleStudentForm[group]: ", ValueGroup)
                        .data("ScheduleStudentForm[week]: ",getData)
                        .post();
                table = doc.getElementsByTag("tbody");
                table_1 = table.get(0);
                list1 = new String[table_1.childrenSize()];
                day = new String[5];
                indexDay = new int[5];

                for (int i = 0; i < table_1.childrenSize(); i++) {
                    try {
                        list1[i] = ("\nУрок №" + table_1.children().get(i).child(0).text()+
                                "\nВремя: " + table_1.children().get(i).child(1).text() +
                                "\nПредмет: " + table_1.children().get(i).child(3).text() +
                                "\nАудитория: " + table_1.children().get(i).child(4).text() +
                                "\nПреподаватель: " + table_1.children().get(i).child(2).text());
                    } catch (Exception e) {}
                }
                for (int j = 0; j <=4; j++) {
                    try {
                        day[j] = table_1.getElementsByClass("schedule-table-day").get(j).text();
                    } catch (Exception e) {
                        e.getMessage();
                    }
                }
            }catch (IOException e) {
                if (error_count<=4) {
                    Log.d("MyLog", "Error= " + e);
                    error_count++;
                    Parsing();
                } else
                    e.printStackTrace();
            }
        }
    }
    public void ParsWeekDay() {
        table = null;
        if(doc!=null) {
            Week = doc.getElementById("yw0");
            WeekDay = new String[16];
            int cout = 0;
            if (error_count != 4) {
                try {
                    for (int i = 1; i <= WeekDay.length; i++) {
                        WeekDay[i] = Week.getElementById("ScheduleStudentForm_week").child(cout).text();
                        cout++;
                        if (WeekDay[i] == null) {
                            Log.d("MyLog", "Null");
                        }
                    }
                } catch (Exception e) {
                    e.getMessage();
                }
            }
            return;
        }else return;
    }
}