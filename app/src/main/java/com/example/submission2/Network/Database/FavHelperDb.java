package com.example.submission2.Network.Database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.submission2.Model.MovieDataRes;
import com.example.submission2.Model.TvDataRes;

import java.util.ArrayList;
import java.util.List;

import static android.provider.BaseColumns._ID;
import static com.example.submission2.Network.Database.DatabaseBuild.FavColumns.COL_ID;
import static com.example.submission2.Network.Database.DatabaseBuild.TABLE_NAME;

public class FavHelperDb extends DatabaseHelper{

    private static DatabaseHelper dataBaseHelper;
    private Context context;
    private static SQLiteDatabase database;

    public FavHelperDb open() throws SQLException {
        dataBaseHelper = new DatabaseHelper(context);
        database = dataBaseHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dataBaseHelper.close();
        if (database.isOpen())
            database.close();
    }

    public FavHelperDb(Context context){
        super(context);
        this.context = context;
    }

    public boolean addFavMovie(String ID, String TYPE, String JUDUL, String DESCRIPTION, String POSTERPATH) {
        ContentValues cv = new ContentValues();
        cv.put("ID", ID);
        cv.put("TYPE", TYPE);
        cv.put("JUDUL", JUDUL);
        cv.put("DESCRIPTION", DESCRIPTION);
        cv.put("POSTERPATH",POSTERPATH);

        SQLiteDatabase db = this.getWritableDatabase();
        boolean create = db.insert(TABLE_NAME, null, cv) > 0;
        db.close();

        return create;
    }

    public boolean addFavTV(String ID, String TYPE, String JUDUL, String DESCRIPTION, String POSTERPATH) {
        ContentValues cv = new ContentValues();
        cv.put("ID", ID);
        cv.put("TYPE", TYPE);
        cv.put("JUDUL", JUDUL);
        cv.put("DESCRIPTION", DESCRIPTION);
        cv.put("POSTERPATH",POSTERPATH);

        SQLiteDatabase db = this.getWritableDatabase();
        boolean create = db.insert(TABLE_NAME, null, cv) > 0;
        db.close();

        return create;
    }

    public boolean delFavMovie(int id) {

        Log.d("DeletedMovie","Data :"+id);
        SQLiteDatabase db = this.getWritableDatabase();
        boolean delete = db.delete(TABLE_NAME, "ID=? ", new String[]{String.valueOf(id)}) > 0;
        db.close();

        return delete;
    }

    public boolean delFavTV(int id) {

        Log.d("DeletedTv","Data :"+id);
        SQLiteDatabase db = this.getWritableDatabase();
        boolean delete = db.delete(TABLE_NAME, "ID=? ", new String[]{String.valueOf(id)}) > 0;
        db.close();

        return delete;
    }

    public final List<MovieDataRes> getAllMovie() {
        Cursor cursor = null;
        String jenis = "Movie";
        int id = -1;
        String ID = "", TYPE = "", JUDUL = "", DESCRIPTION ="", POSTERPATH="";
        List<MovieDataRes> movieDataRes = new ArrayList<>();

        try {
            SQLiteDatabase db = this.getWritableDatabase();
            cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME+ " WHERE TYPE = ?", new String[]{jenis});
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    id = cursor.getInt(cursor.getColumnIndex("_id"));
                    ID = cursor.getString(cursor.getColumnIndex("ID"));
                    JUDUL = cursor.getString(cursor.getColumnIndex("JUDUL"));
                    TYPE = cursor.getString(cursor.getColumnIndex("TYPE"));
                    DESCRIPTION = cursor.getString(cursor.getColumnIndex("DESCRIPTION"));
                    POSTERPATH = cursor.getString(cursor.getColumnIndex("POSTERPATH"));

                    movieDataRes.add(new MovieDataRes(
                            id, ID, JUDUL, TYPE, DESCRIPTION, POSTERPATH
                    ));

                    cursor.moveToNext();
                }
            }

            return movieDataRes;

        } finally {
            cursor.close();
        }
    }

    public List<TvDataRes> getAllTv() {
        Cursor cursor = null;
        String jenis = "Tv";
        int id = -1;
        String ID = "", TYPE = "", JUDUL = "", DESCRIPTION ="", POSTERPATH="";
        List<TvDataRes> tvDataRes = new ArrayList<>();

        try {
            SQLiteDatabase db = this.getWritableDatabase();
            cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME+" WHERE TYPE = ?", new String[]{jenis});
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    id = cursor.getInt(cursor.getColumnIndex("_id"));
                    ID = cursor.getString(cursor.getColumnIndex("ID"));
                    JUDUL = cursor.getString(cursor.getColumnIndex("JUDUL"));
                    TYPE = cursor.getString(cursor.getColumnIndex("TYPE"));
                    DESCRIPTION = cursor.getString(cursor.getColumnIndex("DESCRIPTION"));
                    POSTERPATH = cursor.getString(cursor.getColumnIndex("POSTERPATH"));

                    tvDataRes.add(new TvDataRes(
                            id, ID, JUDUL, TYPE, DESCRIPTION, POSTERPATH
                    ));

                    cursor.moveToNext();
                }
            }

            return tvDataRes;
        } finally {
            cursor.close();
        }
    }

    public static Cursor queryByIdProvider(String id) {
        return database.query(TABLE_NAME,
                null,
                _ID + " = ?",
                new String[]{id},
                null,
                null,
                null,
                null);
    }

    public static Cursor queryProvider() {
        return database.query(TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                _ID + " DESC");
    }
    public static long insertProvider(ContentValues values) {
        return database.insert(TABLE_NAME, null, values);
    }
    public static int deleteProvider(String id) {
        String[] whereArgs = new String[]{String.valueOf(id)};
        return database.delete(TABLE_NAME, COL_ID + " = ?", whereArgs);
    }

}
