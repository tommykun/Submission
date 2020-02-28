package com.example.submission2.Network.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static com.example.submission2.Network.Database.DatabaseBuild.FavColumns.COL_DESCRIPTION;
import static com.example.submission2.Network.Database.DatabaseBuild.FavColumns.COL_ID;
import static com.example.submission2.Network.Database.DatabaseBuild.FavColumns.COL_JUDUL;
import static com.example.submission2.Network.Database.DatabaseBuild.FavColumns.COL_POSTERPATH;
import static com.example.submission2.Network.Database.DatabaseBuild.FavColumns.COL_TYPE;
import static com.example.submission2.Network.Database.DatabaseBuild.TABLE_NAME;

public class DatabaseHelper extends SQLiteOpenHelper {


    public static final String DB_NAME = "subDB";
    private static final int DATABASE_VERSION = 2;

    final String SQL_CREATE_FAVORITE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
            _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COL_ID + " INTEGER, " +
            COL_JUDUL + " TEXT, " +
            COL_TYPE + " TEXT, " +
            COL_DESCRIPTION + " TEXT, " +
            COL_POSTERPATH + " TEXT " +
            "); ";

    DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_FAVORITE_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS '" + TABLE_NAME + "'");
        onCreate(db);
    }
}
