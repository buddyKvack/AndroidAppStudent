package com.example.test_2;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.security.acl.Group;
import java.util.List;

public class SelectedListTimetables extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selected_timetables);
        init();
    }

    public static Context Timetables;
    static com.airbnb.lottie.LottieAnimationView ProgressBarTimaTable;
    static Spinner spinner, spinner1, spinner2;
    Button btnListTable;
    Runnable run;
    Thread thread;
    TextView text;
    static TextView progresstext;
    public String Group;
    private DatabaseReference databaseReference;

    private void init() {
        ProgressBarTimaTable = findViewById(R.id.ProgresBarLoad);
        Timetables = SelectedListTimetables.this;
        spinner = findViewById(R.id.spinnerSelectedData);
        spinner1 = findViewById(R.id.spinnerSelectedData2);
        spinner2 = findViewById(R.id.spinnerSelectedData3);
        btnListTable = findViewById(R.id.BtnNext);
        text = findViewById(R.id.faculty);
        progresstext =findViewById(R.id.ProgressText);
        ParsingSelectSpinner parsingSelectSpinner = new ParsingSelectSpinner();
        ButtonNext();
        GetDataThread();
    }

    private void GetDataThread() {
        final ParsingSelectSpinner parsingSelectSpinner = new ParsingSelectSpinner();
        run = new Runnable() {
            @Override
            public void run() {
                parsingSelectSpinner.GetDataSpinner();
            }
        };
        thread = new Thread(run);
        thread.start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (thread.isAlive()) {
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.getMessage();
                    }
                    Log.d("MyLog", "init: " + thread.isAlive());
                }

            }
        }).start();
        ProgressBarTimaTable.setVisibility(View.VISIBLE);
    }

    public void ButtonNext() {
        btnListTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("MyLog", "Start:\n" + LoginMain.table.getString("ValueFac", "Error") + "\n" + LoginMain.table.getString("ValueSpec", "Error") + "\n" + LoginMain.table.getString("ValueGroup", "Error"));
                if(spinner2.getSelectedItem()!=null){
                    setTimetablesFireBase();
                }else {
                    Toast.makeText(SelectedListTimetables.this,"Не выбран элемент",Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    public void setTimetablesFireBase() {
        Group =spinner2.getSelectedItem().toString();
        BaseGroup group = new BaseGroup(Group);
        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        databaseReference.child("GroupList").setValue(group).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Intent next = new Intent(SelectedListTimetables.this, MainActivity.class);
                    next.putExtra("ValueFac", LoginMain.table.getString("ValueFac", "404"));
                    next.putExtra("ValueSpec", LoginMain.table.getString("ValueSpec", "404"));
                    next.putExtra("ValueGroup", LoginMain.table.getString("ValueGroup", "404"));
                    startActivity(next);
                    finish();
                }
            }
        });
    }
}