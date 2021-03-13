package com.example.test_2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import org.w3c.dom.Text;

public class Splash extends AppCompatActivity {
    Animation topAnimation,bottomAnimation,middleAnimation;
    View first,second,third,fourth,fifth,sixth,log_wksu;
    TextView S,bottom_text;
    DatabaseReference reference;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash_activity);
        topAnimation = AnimationUtils.loadAnimation(this,R.anim.animate_top);
        bottomAnimation = AnimationUtils.loadAnimation(this,R.anim.bottom_anime);
        middleAnimation = AnimationUtils.loadAnimation(this,R.anim.anime_middle);
        first = findViewById(R.id.first_line);
        second = findViewById(R.id.second_line);
        third = findViewById(R.id.three_line);
        fourth = findViewById(R.id.fourth_line);
        fifth = findViewById(R.id.fifth_line);
        sixth = findViewById(R.id.six_line);
        S = findViewById(R.id.S_text);
        bottom_text = findViewById(R.id.textlog);
        //animation----->
        first.setAnimation(topAnimation);
        second.setAnimation(topAnimation);
        third.setAnimation(topAnimation);
        fourth.setAnimation(topAnimation);
        fifth.setAnimation(topAnimation);
        sixth.setAnimation(topAnimation);
        S.setAnimation(middleAnimation);
        bottom_text.setAnimation(bottomAnimation);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Start_Animate();
            }
        };
        Thread t1 = new Thread(runnable);
        t1.start();
    }
    private void Start_Animate(){
       if(FirebaseAuth.getInstance().getCurrentUser()!=null){
           try {
               Thread.sleep(2500);
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
           Intent MainActivity = new Intent(Splash.this, LoginMain.class);
           startActivity(MainActivity);
           finish();
           Log.d("MyLog", "Start_Animate: true");
       }else {
           try {
               Thread.sleep(1500);
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
           Intent MainActivity = new Intent(Splash.this, LoginMain.class);
           startActivity(MainActivity);
           finish();
           Log.d("MyLog", "Start_Animate: ");

       }
    }

}

