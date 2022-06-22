package com.example.dictionary.favourites;

import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DatabaseAccessFavouriteVnEng {
    String table = "fav_table";
    private Context context;
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccessFavouriteVnEng instance;

    /**
     * Private constructor to aboid object creation from outside classes.
     *
     * @param context
     */
    private DatabaseAccessFavouriteVnEng(Context context) {
        this.openHelper = new DatabaseOpenHelperFavouriteVnEng(context);
        this.context=context;
    }

    /**
     * Return a singleton instance of DatabaseAccess.
     *
     * @param context the Context
     * @return the instance of DabaseAccess
     */
    public static DatabaseAccessFavouriteVnEng getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccessFavouriteVnEng(context);
        }
        return instance;
    }

    /**
     * Open the database connection.
     */
    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    /**
     * Close the database connection.
     */
    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

    /**
     * Read all words from anh_viet dictionary
     *
     * @return a List of word from dictionary
     */
    public List<String> getWordsFavoutire() {
        List<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM fav_table", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(1));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }
    public void insertData(String name,String definition){
        //Boolean x= database.execSQL("Insert into fav_table(name,definition) values('"+name+"','"+definition+"')");

        ContentValues newValues = new ContentValues();
        newValues.put("word", name);
        newValues.put("definition", definition);




        long rowInserted  = database.insert(table, null, newValues);
        if(rowInserted != -1)
            Toast.makeText(context.getApplicationContext(), "New row added, row id: " + rowInserted, Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(context.getApplicationContext(), "Something wrong", Toast.LENGTH_SHORT).show();


    }
    public void deleteData(int  position){
        database.delete(table,"ID=? ",new String[]{"'position'"});
        Toast.makeText(context.getApplicationContext(), " deleted success", Toast.LENGTH_SHORT).show();
    }
}
