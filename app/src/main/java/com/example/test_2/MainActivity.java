package com.example.test_2;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SimpleExpandableListAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawer;
    Button btn, btn2;
    public static Toolbar toolbar;
    static SharedPreferences Table;
    static Context context;
    private static final String ListDays="Days",ListChild="ListChild";
    static ExpandableListView ListDays_ID;
    static SimpleExpandableListAdapter adapter;
    static ArrayList<ArrayList<Map<String, String>>> childData;
    static ArrayList<Map<String, String>> groupData;
    static ArrayList<Map<String, String>> childDataItem;
    private RelativeLayout Setting_Container;
    static Map<String, String> m;
    static String[] groups;
    static ProgressBar bar;
    static Spinner spinner;
    static ConstraintLayout content_layout;
    private DatabaseReference firebaseDatabase;
    TextView navUsername,setting_click;
    private LinearLayout MainLiner;
    GetDataBase toolbar_text;
    View view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        content_layout=findViewById(R.id.content_cordinatal);
        Start();
        CheckUserOnline(false);
    }
    private boolean CheckUserOnline(boolean IsOnline){
        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            IsOnline=true;
        }else {
         Table.edit().clear().commit();
         Table.edit().apply();
         Intent Login_main = new Intent(MainActivity.this,LoginMain.class);
         startActivity(Login_main);
         finish();
        }
        return IsOnline;
    }
    private void Start(){
        toolbar = findViewById(R.id.toolbar);
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        navUsername = (TextView) headerView.findViewById(R.id.profilename);
        setting_click =headerView.findViewById(R.id.setting_textview);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        btn = findViewById(R.id.button_taber_1);
        btn2 = findViewById(R.id.button2);
        ListDays_ID = findViewById(R.id.ListDays);
        Table = getSharedPreferences("TABLE", MODE_PRIVATE);
        context=MainActivity.this;
        ClickButton();
        bar=findViewById(R.id.ProgresBarLoad);
        spinner=findViewById(R.id.spinner);
        AdapterViewList(this,false,false);
        NewWeek newWeek = new NewWeek(null,Table,context);
        newWeek.setSpinner();
        GetValue();
        setHeaderName(view);
        MainLiner = findViewById(R.id.MainLiner);
        Setting_Container = findViewById(R.id.SettingFragment);
    }
    private void setHeaderName(View view){
        if(isOnline(MainActivity.this)==true) {
            firebaseDatabase = FirebaseDatabase.getInstance().getReference("Users");
            firebaseDatabase.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Users user = snapshot.getValue(Users.class);
                    navUsername.setText(user.Name);
                    Toast.makeText(MainActivity.this, "Name = " + user.Name, Toast.LENGTH_LONG).show();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }else{Toast.makeText(MainActivity.this, "Не подключен к интернету",Toast.LENGTH_LONG).show();}
    }
    private void  GetValue(){
        String VALUE_data_Fac,VALUE_data_Spec,VALUE_data_Group;
        if(Table.getString("ValueFac","404").equals("404")&&Table.getString("ValueSpec","404").equals("404")&&Table.getString("ValueGroup","404").equals("404")){
            Bundle arguments = getIntent().getExtras();
            VALUE_data_Fac = arguments.get("ValueFac").toString();
            VALUE_data_Spec = arguments.get("ValueSpec").toString();
            VALUE_data_Group = arguments.get("ValueGroup").toString();
                SharedPreferences.Editor editor = Table.edit();
                editor.putString("ValueFac", VALUE_data_Fac);
                editor.putString("ValueSpec", VALUE_data_Spec);
                editor.putString("ValueGroup", VALUE_data_Group);
                editor.apply();
            }else
            Toast.makeText(context,"Уже существует данные",Toast.LENGTH_LONG);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_list:
                GetDataBase.GetGroup();
                MainLiner.setVisibility(View.VISIBLE);
                Setting_Container.setVisibility(View.INVISIBLE);
                AdapterViewList(this,false,false);
                break;
            case R.id.nav_chat:
                Intent intent = new Intent(MainActivity.this,Chat.class);
                startActivity(intent);
                break;
            case R.id.nav_book:
                break;
            case R.id.setting_listview:
                toolbar.setTitle("Настройка");
                RelativeLayout r = findViewById(R.id.SettingFragment);
                r.bringToFront();
                SettingFragment setting = new SettingFragment();
                FragmentTransaction fr = getSupportFragmentManager().beginTransaction();
                fr.replace(R.id.SettingFragment,setting);
                fr.commit();
                MainLiner.setVisibility(View.INVISIBLE);
                Setting_Container.setVisibility(View.VISIBLE);
                break;

        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    void ClickButton() {
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Table.edit().clear().commit();
                Table.edit().apply();
                AdapterViewList(context,false,false);
                Intent NewStart = new Intent(MainActivity.this,LoginMain.class);
                startActivity(NewStart);
                finish();
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isOnline(MainActivity.this);
                if (isOnline(MainActivity.this) == true) {
                    ParsingList pars= new ParsingList(Table,context,"TT_2_15_02_2021",Table.getString("ValueFac","404"),
                            Table.getString("ValueSpec","404"),
                            Table.getString("ValueGroup","404"));
                    pars.StartThreadParsing();
                }
            }
        });
    }
    public static boolean isOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            Toast.makeText(context, "Не подключен", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    public static Boolean AdapterViewList(Context context,boolean check, boolean errors) {
        if(check){
            bar.setVisibility(View.GONE);
            check=false;
            AlertDialog.Builder dialog = new AlertDialog.Builder(context);
            dialog.setTitle("Готово");
            dialog.setMessage("Расписание успешно загружено, теперь расписание можно смотреть оффлайн");
            AlertDialog dialog1=dialog.create();
            dialog1.show();
        }
        if(errors)
        {
            bar.setVisibility(View.GONE);
            AlertDialog.Builder dialog = new AlertDialog.Builder(context);
            dialog.setTitle("Ошибка");
            dialog.setMessage("Перезагрузите приложения, если ничего не случилось то проблема с сервером");
            AlertDialog dialog1=dialog.create();
            dialog1.show();
        }
            groups = new String[]{Table.getString("Day", "Нет данных"), Table.getString("Day1", "Нет данных"), Table.getString("Day2", "Нет данных"),
            Table.getString("Day3", "Нет данных"), Table.getString("Day4", "Нет данных")};

            String[] Monday = new String[]{Table.getString("Monday", "Пусто")};
            String[] Tuesday = new String[]{Table.getString("Tuesday", "Пусто")};
            String[] Wednesday = new String[]{Table.getString("Wednesday", "Пусто")};
            String[] Thursday = new String[]{Table.getString("Thursday", "Пусто")};
            String[] Friday = new String[]{Table.getString("Friday", "Пусто")};
            groupData = new ArrayList<>();
            for (String group : groups) {
                m = new HashMap<>();
                m.put(ListDays, group);
                groupData.add(m);
            }
            childData = new ArrayList<>();
            // создаем коллекцию элементов для первой группы
            childDataItem = new ArrayList<>();
            // заполняем список атрибутов для каждого элемента
            for (String list : Monday) {
                m = new HashMap<String, String>();
                m.put(ListChild, list);
                childDataItem.add(m);
            }
            // добавляем в коллекцию коллекций
            childData.add(childDataItem);

            // создаем коллекцию элементов для второй группы
            childDataItem = new ArrayList<Map<String, String>>();
            for (String list : Tuesday) {
                m = new HashMap<String, String>();
                m.put(ListChild, list);
                childDataItem.add(m);
            }
            childData.add(childDataItem);

            childDataItem = new ArrayList<>();
            for (String monday : Wednesday) {
                m = new HashMap<>();
                m.put(ListChild, monday);
                childDataItem.add(m);
            }
            childData.add(childDataItem);

            childDataItem = new ArrayList<Map<String, String>>();
            for (String list : Thursday) {
                m = new HashMap<>();
                m.put(ListChild, list);
                childDataItem.add(m);
            }
            childData.add(childDataItem);

            childDataItem = new ArrayList<Map<String, String>>();
            for (String list : Friday) {
                m = new HashMap<>();
                m.put(ListChild, list);
                childDataItem.add(m);
            }
            childData.add(childDataItem);

            String groupFrom[] = new String[]{ListDays};
            // список ID view-элементов, в которые будет помещены атрибуты групп
            int groupTo[] = new int[]{android.R.id.text1};

            String childFrom[] = new String[]{ListChild};
            int childTo[] = new int[]{android.R.id.text1};

            adapter = new SimpleExpandableListAdapter(
                    context,
                    groupData,
                    android.R.layout.simple_expandable_list_item_1,
                    groupFrom,
                    groupTo,
                    childData,
                    android.R.layout.simple_list_item_1,
                    childFrom,
                    childTo);
            ListDays_ID.setAdapter(adapter);
            return false;
    }

}

