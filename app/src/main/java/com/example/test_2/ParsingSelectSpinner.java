package com.example.test_2;

import android.util.Log;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ParsingSelectSpinner extends SelectedListTimetables{
    Document document;
    Jsoup jsoup;
    Thread thread;
    Element getFaculty;
    List<String> listInfo,group,valueGroup,facultyList,valueFaculty,getValue;
    public void Post(String idFac,String idSpec) {
                try {
                    document = jsoup.connect("http://e-portal.wku.edu.kz/ru/schedule/search").userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64; rv:65.0) Gecko/20100101 Firefox/65.0")
//
                            .data("ScheduleStudentForm[branch]: ", "1")
                            .data("ScheduleStudentForm[faculty]: ", idFac)
                            .data("ScheduleStudentForm[speciality]: ", idSpec)
                            .post();
                } catch (IOException e) {
                    Log.d("MyLog", "Post: " + e.getMessage());
                }
    }
    public void GetDataSpinner(){
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Post("66","310");
            }
        });
        thread.start();
        Log.d("MyLog", "Post: "+thread.isAlive());
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
            getFaculty =document.getElementById("yw0");

        facultyList = new ArrayList<>();
        valueFaculty = new ArrayList<>();
        try {
            for (int i=0; i<40;i++) {
                Element a= document.getElementsByAttributeValue("id", "ScheduleStudentForm_faculty").get(0).child(i);
                valueFaculty.add(a.val());
                facultyList.add(getFaculty.getElementById("ScheduleStudentForm_faculty").child(i).text());
            }
        }catch (Exception e){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Selected_Info Info = new Selected_Info();
                    Info.Start(facultyList,valueFaculty);
                }
            });

        }
    }
    Element getSpeciality;
    public void GetDataSpec(String value){
        final String finalValue = value;
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
            Post(finalValue,"");
            }
        });
        t1.start();
        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        getSpeciality=document.getElementById("yw0");
        listInfo = new ArrayList<>();
        getValue = new ArrayList<>();
        try {
            for (int i=0; i<30;i++) {
                Element a= document.getElementsByAttributeValue("id", "ScheduleStudentForm_speciality").get(0).child(i);
                getValue.add(a.val());
                listInfo.add(getSpeciality.getElementById("ScheduleStudentForm_speciality").child(i).text());
            }
        }catch (Exception e){
            Selected_Info Info = new Selected_Info();
            Info.Start1(listInfo,getValue);
        }
    }
    Element getGroup;
    public void GetDataGroup(final String value){
        Log.d("MyLog", "GetDataGroup: "+LoginMain.table.getString("ValueFac","0"));
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
        Post(LoginMain.table.getString("ValueFac","0"),value);
            }
        });
        t2.start();
        try {
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        getGroup=document.getElementById("yw0");
        group = new ArrayList<>();
        valueGroup = new ArrayList<>();
        try {
            for (int i=0; i<30;i++) {
                Element a= document.getElementsByAttributeValue("id", "ScheduleStudentForm_group").get(0).child(i);
                valueGroup.add(a.val());
                group.add(getGroup.getElementById("ScheduleStudentForm_group").child(i).text());
            }
        }catch (Exception e){
            if(group!=null){
                Selected_Info Info = new Selected_Info();
                Info.Start2(group,valueGroup);
            }else {
                Log.d("MyLog", "GetDataGroup: Пусто");
            }

        }
    }
}
