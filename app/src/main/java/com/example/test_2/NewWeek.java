package com.example.test_2;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class NewWeek extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Context context;
    SharedPreferences Table;
    String[] Week;
    ArrayAdapter adapter1;
    SharedPreferences.Editor EditTB;
    String setData;
    NewWeek(String[] Week, SharedPreferences Table, Context context){
        this.Week=Week;
        this.Table= Table;
        this.context=context;
  }
  protected void SaveDateBase(){
    EditTB=Table.edit();
        try {
            for (int i=1;i<Week.length;i++){
                EditTB.putString("id"+i,Week[i].toLowerCase());
                EditTB.apply();
            }
        }catch (Exception e){}
        setSpinner();
  }
   protected void setSpinner(){
       Week=new String[15];
       try {
          for (int i=1;i<Week.length;i++){
              Week[i]=Table.getString("id"+i,"Нет данных");
          }
      }catch (Exception e){ }
        Week[0]="-> "+Table.getString("Select","Выбери дату");
        adapter1= new ArrayAdapter(context,android.R.layout.simple_spinner_item,Week);
        adapter1.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        MainActivity.spinner.setAdapter(adapter1);
        MainActivity.spinner.setOnItemSelectedListener(this);
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        EditTB=Table.edit();
        if(position!=0){
        EditTB.putString("Select",parent.getItemAtPosition(position).toString());
        EditTB.apply();
        String txt=Table.getString("Select","Error");
        if(txt!="Error"){
            setData="TT_2_"+txt.replace('.','_');
            ParsingList pars= new ParsingList(Table,context,setData,MainActivity.Table.getString("ValueFac","404"),
                    MainActivity.Table.getString("ValueSpec","404"),
                    MainActivity.Table.getString("ValueGroup","404"));
            pars.StartThreadParsing();
        }
        Toast.makeText(context,parent.getItemAtPosition(position).toString(),Toast.LENGTH_LONG).show();
        }
        setSpinner();
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
