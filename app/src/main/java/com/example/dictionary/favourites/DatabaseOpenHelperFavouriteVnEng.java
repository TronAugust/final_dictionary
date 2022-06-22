package com.example.dictionary.favourites;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;
public class DatabaseOpenHelperFavouriteVnEng extends SQLiteAssetHelper {
    private static final String DATABASE_NAME = "db_fav_va.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseOpenHelperFavouriteVnEng(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
}
