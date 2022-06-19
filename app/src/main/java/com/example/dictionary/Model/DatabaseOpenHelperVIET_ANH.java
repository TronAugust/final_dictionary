package com.example.dictionary.Model;
import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DatabaseOpenHelperVIET_ANH extends SQLiteAssetHelper {
    private static final String DATABASE_NAME = "viet_anh.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseOpenHelperVIET_ANH (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
}
