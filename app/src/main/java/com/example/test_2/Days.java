package com.example.test_2;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import java.util.ArrayList;
public class Days{
    private static final String KeyMonday="Monday",KeyTuesday="Tuesday",KeyWednesday="Wednesday",KeyThursday="Thursday",KeyFriday="Friday";
    String[] list;
    String[]day;
    Thread thread;
    ArrayList array1;
    ArrayList array2;
    ArrayList array3;
    ArrayList array4;
    ArrayList array5;
    public static SharedPreferences Table1;
    Context context;
    int[] indexDay;
    int index2 = 0;
    Days (String[] list,SharedPreferences Table,Context context,String[]day,Thread thread,int[] indexDay){
        Table1 =Table;
        this.list=list;
        this.context=context;
        this.day=day;
        this.thread=thread;
        this.indexDay=indexDay;
    }

void Start(){
    try {
        for (int i = 0; i <= list.length; i++) {
            if (list[i] == null) {
                indexDay[index2] = i;
                list[i]="";
                index2++;
            }
        }
    } catch (Exception e) {}
    array1 = new ArrayList();
    array2 = new ArrayList();
    array3 = new ArrayList();
    array4 = new ArrayList();
    array5 = new ArrayList();
    try {

        for (int i = 1; i < indexDay[1]; i++) {
            array1.add(list[i]);
        }
        for (int i = indexDay[1]; i < indexDay[2]; i++) {
            array2.add(list[i]);
        }
        for (int i = indexDay[2]; i < indexDay[3]; i++) {
            array3.add(list[i]);
        }

        if(indexDay[4]==0){
            for (int i = indexDay[3]; i <30; i++) {
                array4.add(list[i]);
            }
        }else {
            for (int i = indexDay[3]; i <indexDay[4]; i++) {
                array4.add(list[i]);
            }
        }
        if(indexDay[4]!=0){
            try {
                for (int i = indexDay[4]; i <50; i++) {
                    array5.add(list[i]);
                }
            }catch (Exception e){}
        }else {array5.add(0,"Пусто");}

    }catch (Exception e){}
    SaveBase();

}

   private void SaveBase()
{
    try {
        SharedPreferences.Editor editTable = Table1.edit();
        editTable.putString(KeyMonday, array1.toString());
        editTable.putString(KeyTuesday, array2.toString());
        editTable.putString(KeyWednesday, array3.toString());
        editTable.putString(KeyThursday, array4.toString());
        editTable.putString(KeyFriday, array5.toString());
        editTable.putString("Day", day[0]);
        editTable.putString("Day1", day[1]);
        editTable.putString("Day2", day[2]);
        editTable.putString("Day3", day[3]);
        editTable.putString("Day4", day[4]);
        editTable.apply();
        editTable.putBoolean("bool", true);
        editTable.apply();
        Log.d("MyLog", "ThredSaveBase" + thread.isAlive());
    }catch (Exception e){}
    if(Table1!=null)
    {
     MainActivity.AdapterViewList(context,true,false);
    }
    else{
    MainActivity.AdapterViewList(context,false,true);
    }
}
}

