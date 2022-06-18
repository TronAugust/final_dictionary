package com.example.dictionary;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.dictionary.Definition.DefinitionActivity;
import com.example.dictionary.Definition.DefinitionActivityVIET_ANH;
import com.example.dictionary.Model.DatabaseAccess;
import com.example.dictionary.Model.DatabaseAccessVIET_ANH;
import com.example.dictionary.favourites.DatabaseAccessFavourite;
import com.example.dictionary.favourites.favourite;
import com.example.dictionary.favourites.favouriteVnEng;
import com.example.dictionary.yourWord.yourWord;

import com.example.dictionary.R;

import com.google.android.material.navigation.NavigationView;

import androidx.navigation.ui.AppBarConfiguration;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private ListView listView,lv_item;
    ArrayAdapter<String> adapter;
    Toolbar toolbar;
    SearchView sv;
    DrawerLayout dr_layout;
    NavigationView navigationView;
    Menu menu;
    String tempt,key,way;
    ImageView search_volume;

    public   String  path="av";
    public static final String preference_file_key="preference_file_key";
    MenuItem eng_vn,vn_eng,fav,word,share,help;
    Activity activity;

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Context context=MainActivity.this;
        activity=MainActivity.this;

        search_volume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSpeechInput();
            }
        });
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                MainActivity.this.adapter.getFilter().filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                MainActivity.this.adapter.getFilter().filter(newText);
                return true;
            }
        });

    }

    public void getSpeechInput() {

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, 10);
        } else {
            Toast.makeText(this, "Your Device Don't Support Speech Input", Toast.LENGTH_SHORT).show();
        }
    }


    private void processIconNavigation(Context context){
        //CHANGE ENG_VN
        eng_vn.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
               // Intent myIntent = new Intent(MainActivity.this, eng_vn.class);
                // Bundle bundle=new Bundle();
                // bundle.putString("");

               // startActivity(myIntent);
                if(path=="av")   Toast.makeText(getApplicationContext(),"ban da o che do anh viet",Toast.LENGTH_LONG).show();
                else {
                    Toast.makeText(getApplicationContext(),"ban vua chon che do anh viet",Toast.LENGTH_LONG).show();
                    tempt="av";
                    saveStr(context,tempt);
                    activity.recreate();
                }
                return true;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 10:
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    //  Toast.makeText(this, result.get(0), Toast.LENGTH_SHORT).show();
                    sv.setQuery(result.get(0),false);
                }
                break;
        }
    }

    private void actionToolBar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.home_icon);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    dr_layout.openDrawer(Gravity.LEFT);
            }
        });
    }

    public  void optionENG_VN(Context context){
        SharedPreferences load_Str = context.getSharedPreferences(preference_file_key,MODE_PRIVATE);
        path=load_Str.getString(key,"");
        Toast.makeText(getApplicationContext(),"path hien tai la:"+path,Toast.LENGTH_LONG).show();
         tempt=path;
         if(path=="av"){
             anhviet();
             way="av";
         }
        else {
            vietanh();
            way="va";
         }
    }

    public  void saveStr(Context context,String option){
        SharedPreferences sharedPreferences=context.getSharedPreferences(preference_file_key,MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();

        editor.putString(key,option);
        editor.apply();
    }

    private void mapping(){
         toolbar = findViewById(R.id.toolbar);
        sv = findViewById(R.id.sv);
        this.listView = (ListView) findViewById(R.id.listView);
        lv_item=findViewById(R.id.lv_item);
        dr_layout=findViewById(R.id.drawer_layout);
        // mapping with icon in navigationView
        navigationView=findViewById(R.id.nav_view);
        menu=navigationView.getMenu();
        eng_vn=menu.findItem(R.id.eng_vn);
        vn_eng=menu.findItem(R.id.vn_eng);
        fav=menu.findItem(R.id.favourite);
        word=menu.findItem(R.id.word);
        search_volume=findViewById(R.id.search_volume);
    }

    private void anhviet(){
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
        List<String> anhViet = databaseAccess.getWords();
        databaseAccess.close();

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, anhViet);
        this.listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getApplicationContext(),"ban vua chon che do anh viet",Toast.LENGTH_LONG).show();

                Intent myIntent = new Intent(MainActivity.this, DefinitionActivity.class);
                // Bundle bundle=new Bundle();
                // bundle.putString("");
                String x = anhViet.get(position);
                myIntent.putExtra("word", x);
                startActivity(myIntent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}