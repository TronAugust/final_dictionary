package com.example.dictionary.favourites;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dictionary.Definition.DefinitionActivityVIET_ANH;
import com.example.dictionary.R;

import java.util.List;

public class favouriteVnEng extends AppCompatActivity {
    private ListView listView;
    ArrayAdapter<String> adapter;
    //private TextView tvDefinition, tvWord;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favourite);
        listView=findViewById(R.id.fav_listView);

        DatabaseAccessFavouriteVnEng databaseAccess = DatabaseAccessFavouriteVnEng.getInstance(this);
        databaseAccess.open();
        List<String> fav_list = databaseAccess.getWordsFavoutire();
        databaseAccess.close();

        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,fav_list);
        this.listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent myIntent = new Intent(favouriteVnEng.this, DefinitionActivityVIET_ANH.class);
                // Bundle bundle=new Bundle();
                // bundle.putString("");
                String x = fav_list.get(position);
                myIntent.putExtra("word", x);
                startActivity(myIntent);


            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                databaseAccess.open();
                databaseAccess.deleteData(position);
                databaseAccess.close();
                adapter.notifyDataSetChanged();
                return true;
            }
        });


    }
}
