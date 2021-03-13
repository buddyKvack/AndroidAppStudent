package com.example.test_2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class LoginMain extends AppCompatActivity {
Button btnReg,btnSingIn,btnMain,btnSign;
private EditText txtEmail,pass,Name;
public static Context ContextLogin;
public static SharedPreferences table;
Context context;
private FirebaseAuth Auth;
static ConstraintLayout coordinatorLayout;
private View blue_br;
private ImageView logo;
private TextView t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_main);
        context=LoginMain.this;
        table=getSharedPreferences("TableValue",MODE_PRIVATE);
      init();
    }
    private void init(){
        ContextLogin=LoginMain.this;
        logo = findViewById(R.id.logo);
        blue_br=findViewById(R.id.blue_bg);
        blue_br.setAnimation(AnimationUtils.loadAnimation(this,R.anim.animate_top));
        logo.setAnimation(AnimationUtils.loadAnimation(this,R.anim.animate_top));
        t = findViewById(R.id.WelcomeText);
        btnReg=findViewById(R.id.ButtonRegister);
        btnSign=findViewById(R.id.Sign_MainLogin);
        btnMain = findViewById(R.id.NextMain);
        btnSingIn=findViewById(R.id.signIn);
        txtEmail= findViewById(R.id.EditEmailAddress);
        pass=findViewById(R.id.EditPassword);

        Name=findViewById(R.id.EditName);
        coordinatorLayout=findViewById(R.id.coordinatorLogin);
        Auth=FirebaseAuth.getInstance();
        addOnClickBtn();
    }
    private void addOnClickBtn(){
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!txtEmail.getText().toString().isEmpty() && !pass.getText().toString().isEmpty() && !Name.getText().toString().isEmpty() && isOnline(LoginMain.this)==true){
                    Auth();
                }else {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(LoginMain.this);
                    dialog.setTitle("Пусто");
                    dialog.setMessage("Поля пустые");
                    AlertDialog dialog1=dialog.create();
                    dialog1.show();
                }
            }
        });
        btnSingIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginMain.this,SelectedListTimetables.class);
                startActivity(i);
                finish();
            }
        });
        btnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent main = new Intent(LoginMain.this,MainActivity.class);
                startActivity(main);
                finish();
            }
        });
        btnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Sign = new Intent(LoginMain.this,Sign.class);
                startActivity(Sign);

            }
        });
    }
    public static boolean isOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()){
            return true;
        } else {
            Toast.makeText(context, "Нет подключений", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    public void Auth() {
        final String text_mail = txtEmail.getText().toString().trim();
        String text_password = pass.getText().toString().trim();
        final String text_name =Name.getText().toString().trim();
        Auth.createUserWithEmailAndPassword(text_mail, text_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Users user = new Users(text_mail,text_name);
                    FirebaseDatabase.getInstance().getReference("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                           if(task.isSuccessful()){
                               Toast.makeText(LoginMain.this,"Успешно",Toast.LENGTH_LONG).show();
                               Intent TimeTable = new Intent(LoginMain.this,SelectedListTimetables.class);
                               startActivity(TimeTable);
                               finish();
                           }else{
                               Toast.makeText(LoginMain.this,"Ошибка БД= "+task.getException().getMessage(),Toast.LENGTH_LONG).show();
                               return;
                           }
                        }
                    });
                } else {
                    Snackbar.make(coordinatorLayout,"Ошибка",Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }


}