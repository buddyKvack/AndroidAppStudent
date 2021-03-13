package com.example.test_2;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.CellSignalStrength;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
public class Chat extends AppCompatActivity {
    FirebaseListAdapter<MessageApp> adapter;
    FloatingActionButton sendBtn;
    DatabaseReference reference;
    String Name;
    RelativeLayout Activity;
    final String UserID=FirebaseAuth.getInstance().getUid();
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_layout);
        sendBtn=findViewById(R.id.BtnSend);
        Activity=findViewById(R.id.Ativity_Chat);
        DisplayMessage();
        getName();
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextInputEditText text = findViewById(R.id.message_text);
                if (MainActivity.isOnline(Chat.this)) {
                    if (!text.getText().toString().isEmpty()) {
                        try {
                            FirebaseDatabase.getInstance().getReference("message").push().setValue(new MessageApp(getName(), text.getText()
                                    .toString()));
                        } catch (Exception e) {
                            Toast.makeText(Chat.this, "Не удалось отправить", Toast.LENGTH_LONG).show();
                        }
                        text.setText("");

                    }
                }else {
                    Snackbar.make(Activity,"Не подключен к интернету",Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    private void DisplayMessage(){
       if(FirebaseAuth.getInstance().getCurrentUser()!=null){
        ListView listMessage = findViewById(R.id.list_item_message);
        adapter = new FirebaseListAdapter<MessageApp>(Chat.this,MessageApp.class,R.layout.list_text, FirebaseDatabase.getInstance().getReference("message")) {
            @Override
            protected void populateView(View v, MessageApp model, int position) {
                TextView text_user,text_message;
                text_user=v.findViewById(R.id.message_user);
                text_message=v.findViewById(R.id.message_text);
                text_user.setText(model.getUser_text());
                text_message.setText(model.getMessage_text());
            }
        };
        listMessage.setAdapter(adapter);
       }
       else {
           Toast.makeText(Chat.this,"Вы не авторизованы",Toast.LENGTH_LONG).show();
           try {
               Thread.sleep(2000);
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
           Intent Sign_main = new Intent(Chat.this,Sign.class);
           startActivity(Sign_main);
           finish();
       }
    }

    private String getName(){
        reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(UserID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               Users user =snapshot.getValue(Users.class);
               if(user!=null)
               {
                    Name = user.Name;
               }
               else Log.d("MyLog", "onDataChange:false ");;
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return Name;
    }
}
