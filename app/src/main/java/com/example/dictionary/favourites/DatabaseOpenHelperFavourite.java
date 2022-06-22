package com.example.dictionary.favourites;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;
public class DatabaseOpenHelperFavourite extends SQLiteAssetHelper {
    private static final String DATABASE_NAME = "db_fav.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseOpenHelperFavourite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }
}
