package com.example.test_2;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Sign  extends AppCompatActivity {
    private Button Sign,SignOut;
    private EditText EdMail,EdPass;
    public FirebaseAuth AuthSign;
    private String getName;
    DatabaseReference reference;
    private View blue_br;
    private ImageView logo;
    private String UserId;
    private Animation top_animation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_sign);
        top_animation = AnimationUtils.loadAnimation(this,R.anim.animate_top);
        Sign = findViewById(R.id.SigButton);
        SignOut = findViewById(R.id.SignOut);
        AuthSign=FirebaseAuth.getInstance();
        blue_br=findViewById(R.id.blue_bg_sign);
        logo=findViewById(R.id.logo_sign);
        blue_br.setAnimation(top_animation);
        logo.setAnimation(top_animation);
        EdMail=findViewById(R.id.EmailAddress);
        EdPass=findViewById(R.id.TextPassword);
        addClick();
    }
    private void addClick(){
        Sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignAuth();
            }
        });
        SignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                if(FirebaseAuth.getInstance().getCurrentUser()==null){
                    Toast.makeText(Sign.this, "Вы вышли", Toast.LENGTH_LONG).show();
                }else Toast.makeText(Sign.this, "Не удалось выйти", Toast.LENGTH_LONG).show();
            }
        });
    }
    private void GetNameProfile(){
        reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener(){
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Users Name = snapshot.getValue(Users.class);
                            if(Name !=null){
                                getName=Name.Name;
                                Toast.makeText(com.example.test_2.Sign.this,"Вы вошли как "+Name.Name,Toast.LENGTH_LONG).show();
                            }else {
                                Log.d("MyLog", "this error");
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.d("MyLog", "this error");
                        }
                    });
    }
    public void SignAuth() {

        String Mail = EdMail.getText().toString().trim();
        String Pass = EdPass.getText().toString().trim();
        if (!EdMail.getText().toString().isEmpty()&&!EdPass.getText().toString().isEmpty()) {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(Mail, Pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                            GetNameProfile();
                            Intent ListTimeTable = new Intent(com.example.test_2.Sign.this, SelectedListTimetables.class);
                            startActivity(ListTimeTable);
                            finish();
                        } else {
                            Toast.makeText(Sign.this, "Ошибка вас нету в бд", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(Sign.this, "Вы не авторизованы", Toast.LENGTH_LONG).show();
                        EdMail.getError();
                    }
                }
            });

        }else{
            EdMail.setError("Пусто");EdPass.setError("Пусто");}
    }
}
