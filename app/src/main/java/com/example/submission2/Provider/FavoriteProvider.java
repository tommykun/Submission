package com.example.submission2.Provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.submission2.Network.Database.DatabaseBuild;
import com.example.submission2.Network.Database.FavHelperDb;

import static com.example.submission2.Network.Database.DatabaseBuild.AUTHORITY;
import static com.example.submission2.Network.Database.DatabaseBuild.CONTENT_URI;

public class FavoriteProvider extends ContentProvider {

    public static final int MOVIE = 1;
    public static final int MOVIE_ID = 2;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        uriMatcher.addURI(AUTHORITY, DatabaseBuild.TABLE_NAME, MOVIE);
        uriMatcher.addURI(AUTHORITY,
                DatabaseBuild.TABLE_NAME+"/#",MOVIE_ID);
    }

    private FavHelperDb favHelperDb;

    @Override
    public boolean onCreate() {
        favHelperDb = new FavHelperDb(getContext());
        favHelperDb.open();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        Cursor cursor;
        switch (uriMatcher.match(uri)) {
            case MOVIE:
                cursor = favHelperDb.queryProvider();
                break;
            case MOVIE_ID:
                cursor = favHelperDb.queryByIdProvider(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;
        }

        if (cursor != null) {
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        long addItem;
        switch (uriMatcher.match(uri)) {
            case MOVIE:
                addItem = favHelperDb.insertProvider(values);
                break;
            default:
                addItem = 0;
                break;
        }

        if (addItem > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return Uri.parse(CONTENT_URI + "/" + addItem);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int deleteItem;
        switch (uriMatcher.match(uri)) {
            case MOVIE_ID:
                deleteItem = favHelperDb.deleteProvider(uri.getLastPathSegment());
                break;
            default:
                deleteItem = 0;
                break;
        }

        if (deleteItem > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return deleteItem;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
