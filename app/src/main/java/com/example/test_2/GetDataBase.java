package com.example.test_2;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.test_2.BaseGroup;
import com.example.test_2.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GetDataBase extends MainActivity {
   static DatabaseReference ref;
    public static void GetGroup(){
        ref = FirebaseDatabase.getInstance().getReference("Users");
        ref.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("GroupList").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                BaseGroup group = snapshot.getValue(BaseGroup.class);
                toolbar.setTitle(group.Group);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}
