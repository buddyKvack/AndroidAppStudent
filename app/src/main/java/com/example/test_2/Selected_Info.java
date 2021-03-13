package com.example.test_2;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import java.util.List;

public class Selected_Info extends SelectedListTimetables implements AdapterView.OnItemSelectedListener{
    List<String> Info;
    List<String> value;
    public Selected_Info() {
    }
    public void Start(List<String> Info,List<String> value){
        this.value=value;
        ArrayAdapter<String> seleted_adapter;
                try {
                    spinner.setOnItemSelectedListener(this);
                    seleted_adapter=new ArrayAdapter<>(Timetables,R.layout.spinner_item_color,Info);
                    seleted_adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                    spinner.setAdapter(seleted_adapter);
                    spinner.setPrompt("Факультет");
                    seleted_adapter.notifyDataSetChanged();
                }catch (Exception e)
                {e.getMessage(); }
    }
    public void Start1(List<String> Info,List<String> value){
        this.value=value;
        ArrayAdapter<String> seleted_adapter;
        try {
            spinner1.setOnItemSelectedListener(this);
            seleted_adapter=new ArrayAdapter<>(Timetables,R.layout.spinner_item_color,Info);
            seleted_adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
            spinner1.setAdapter(seleted_adapter);
            spinner1.setPrompt("Специальность");
            seleted_adapter.notifyDataSetChanged();
        }catch (Exception e)
        {e.getMessage();
            Log.d("MyLog", "SpinnerSelected: "+"Error 123");
        }return;
    }
    public void Start2(List<String> group, List<String> valueGroup){
        this.value=valueGroup;
        ArrayAdapter<String> seleted_adapter;
        try {
            spinner2.setOnItemSelectedListener(this);
            seleted_adapter=new ArrayAdapter<>(Timetables,R.layout.spinner_item_color,group);
            seleted_adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
            spinner2.setAdapter(seleted_adapter);
            spinner2.setPrompt("Группа");
            seleted_adapter.notifyDataSetChanged();
            ProgressBarTimaTable.setVisibility(View.GONE);
            progresstext.setText("Загрузка завершен");
        }catch (Exception e)
        {e.getMessage();
            e.getMessage();
        }
    }
int i=-2;
    static int cout;
    ParsingList pasrs = new ParsingList();
    ParsingSelectSpinner ParsingSelect = new ParsingSelectSpinner();
    private boolean isSpinnerInitial=true;
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Log.d("MyLog", "onItemSelected: " + value.get(position));
            if (parent.getId() == R.id.spinnerSelectedData) {
                ParsingSelect.GetDataSpec(value.get(position));
                SharedPreferences.Editor edit1 = LoginMain.table.edit();
                edit1.putString("ValueFac", String.valueOf(value.get(position)));
                edit1.apply();
            } else if (parent.getId() == R.id.spinnerSelectedData2) {
                ParsingSelect.GetDataGroup(value.get(position));
                SharedPreferences.Editor edit2 = LoginMain.table.edit();
                edit2.putString("ValueSpec", String.valueOf(value.get(position)));
                edit2.apply();
            } else if (parent.getId() == R.id.spinnerSelectedData3) {
                SharedPreferences.Editor edit3 = LoginMain.table.edit();
                edit3.putString("ValueGroup", String.valueOf(value.get(position)));
                edit3.apply();
                Log.d("MyLog", "Start: " + LoginMain.table.getString("ValueGroup", "Error"));
            }
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
