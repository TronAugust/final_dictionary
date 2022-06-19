package com.example.dictionary.Definition;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.example.dictionary.favourites.DatabaseAccessFavouriteVnEng;
import com.example.dictionary.Model.DatabaseAccessVIET_ANH;
import com.example.dictionary.R;

import java.util.List;


public class DefinitionActivityVIET_ANH extends AppCompatActivity {
    private TextView tvDefinition, tvWord;
    Button button;
    DatabaseAccessFavouriteVnEng database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_definition);
        tvDefinition = findViewById(R.id.tv_definition);
        tvWord = findViewById(R.id.tv_word);

        button=findViewById(R.id.button);
        button.setText("ADD");
        //Lấy dữ liệu form kia gởi qua
        Intent intent = getIntent();
        String word = intent.getStringExtra("word");
        tvWord.setText(word);
        //query định nghĩa của từ từ csdl
        DatabaseAccessVIET_ANH dbAccess = DatabaseAccessVIET_ANH.getInstance(this);
        dbAccess.open();
        String definition = dbAccess.getDefinition(word);
        dbAccess.close();
        //Hiển thị trên textView
        tvDefinition.setText(Html.fromHtml(definition));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                database = DatabaseAccessFavouriteVnEng.getInstance(getApplicationContext());
                database.open();
                List<String> fav_list = database.getWordsFavoutire();
                if(fav_list.contains(word))
                    Toast.makeText(getApplicationContext(),"ban da them vao favourite : ))",Toast.LENGTH_SHORT).show();
                else {
                    database.insertData(word, definition);
                }
                database.close();

            }
        });

}}
