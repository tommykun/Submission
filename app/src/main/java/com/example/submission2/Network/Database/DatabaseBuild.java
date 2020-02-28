package com.example.submission2.Network.Database;

import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseBuild {

    public static final String TABLE_NAME = "favTbl";

    public static final class FavColumns implements BaseColumns {
        public static final String COL_ID = "ID";
        public static final String COL_JUDUL = "JUDUL";
        public static final String COL_TYPE = "TYPE";
        public static final String COL_DESCRIPTION = "DESCRIPTION";
        public static final String COL_POSTERPATH = "POSTERPATH";

    }

    public static final String AUTHORITY = "com.example.submission2";
    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(TABLE_NAME)
            .build();


}
