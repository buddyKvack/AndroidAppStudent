package com.example.test_2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class test_layout extends AppCompatActivity {
    Button btn1,btn2;
    TextView TwoFrag2TextView;
    int count=0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_layout);
        btn1=findViewById(R.id.fragment_one);
        btn2=findViewById(R.id.fragment_two);
        testing();
    }

    private void testing (){

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    BlankFragmentOne b1 = new BlankFragmentOne();
                    FragmentTransaction fr = getSupportFragmentManager().beginTransaction();
                    fr.replace(R.id.containerFragment,b1);
                    fr.commit();

            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingFragment b1 = new SettingFragment();
                FragmentTransaction fr = getSupportFragmentManager().beginTransaction();
                fr.replace(R.id.containerFragment,b1);
                fr.commit();
            }
        });
    }

}
