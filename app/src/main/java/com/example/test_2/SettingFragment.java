package com.example.test_2;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;

public class SettingFragment extends Fragment {
    TextView text;
    private ListView setting_list;
    private LinearLayout MainLiner;
    private View view;
    private FirebaseAuth Auth;

    ArrayAdapter<String> adapter_setting;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.setting_fragment, container, false);
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        view=getView();
        setting_list = view.findViewById(R.id.setting_listview);
        String[] SettingText= {"Загрузить фото","Уведомления","Выйти из аккаунта"};
        adapter_setting= new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1,SettingText);
        setting_list.setAdapter(adapter_setting);
        Auth = FirebaseAuth.getInstance();
       setting_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            switch (position){
                case 0:
                    Log.d("MyLog", "onItemClick: 0");break;
                case 1:
                    Log.d("MyLog", "onItemClick: 1");break;
                case 2:if(SignOut(false)==true){
                    Toast.makeText(getContext(),"Вы вышли",Toast.LENGTH_LONG).show();
                }else Log.d("MyLog", "SignOut: Out12");
            }
           }
       });
    }
    private boolean SignOut(boolean check){
        Auth.signOut();
        if(Auth.getCurrentUser()==null){
            Intent LoginMain = new Intent(getContext(), com.example.test_2.LoginMain.class);
            startActivity(LoginMain);
            getActivity().finish();
            MainActivity.Table.edit().clear().commit();
            MainActivity.Table.edit().apply();
            check=true;
        }else Log.d("MyLog", "SignOut: Out");

        return check;
    }
    @Override
    public void onResume() {
        super.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
    }
    @Override
    public void onStop() {
        super.onStop();
    }

}