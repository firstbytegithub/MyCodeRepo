package com.example.chengqi.mycoderepo.app;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

public class DemoContentProvider extends ContentProvider {
    private static String LOG_TAG = "DemoContentProvider";
    private static final String DATABASE_NAME = "idname.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "idnametable";

    private static final String AUTHORITY = "com.example.xuweiwei";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);

    private static final int TABLE = 1;
    private static final int ROW = 2;

    private static final UriMatcher surlmatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        surlmatcher.addURI(AUTHORITY, TABLE_NAME, TABLE);
        surlmatcher.addURI(AUTHORITY, TABLE_NAME + "/#", ROW);
    }

    private SQLiteDatabase sqlDB;
    private DatabaseHelper dbHelper;

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table " + TABLE_NAME + " (id INTEGER, name TEXT);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        }
    }

    public DemoContentProvider() {
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
//        return false;
        dbHelper = new DatabaseHelper(getContext());
        sqlDB = dbHelper.getWritableDatabase();
        return (sqlDB == null) ? false : true;
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
//        throw new UnsupportedOperationException("Not yet implemented");
        int uriType = surlmatcher.match(uri);

        Log.d(LOG_TAG, uri.toString());

//        sqlDB = dbHelper.getWritableDatabase();

        long id = 0;
        switch (uriType) {
            case TABLE:
                Log.d(LOG_TAG, "1");
                id = sqlDB.insert(TABLE_NAME, "", values);
                break;
            default:
//                throw new IllegalArgumentException("unknown URI:" + uri);
                break;
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(TABLE_NAME + "/" + id);
    }


    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
//        throw new UnsupportedOperationException("Not yet implemented");
        int rowsDeleted = 0;
        int uriType = surlmatcher.match(uri);

        switch (uriType) {
            case TABLE:
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                rowsDeleted = db.delete(TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("unknown URI:" + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }


    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
//        throw new UnsupportedOperationException("Not yet implemented");
        int uriType = surlmatcher.match(uri);
        int rowsupdated = 0;

        switch (uriType) {
            case TABLE:
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                rowsupdated = db.update(TABLE_NAME, values, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("unknown URI:" + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsupdated;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
//        throw new UnsupportedOperationException("Not yet implemented");
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(TABLE_NAME);

        int uriType = surlmatcher.match(uri);

        switch (uriType) {
            case TABLE:
                SQLiteDatabase db = dbHelper.getReadableDatabase();
                Cursor c = qb.query(db, projection, selection, null, null, null, sortOrder);
                c.setNotificationUri(getContext().getContentResolver(), uri);
                return c;
            case ROW:
                throw new IllegalArgumentException("unknown URI:" + uri);
        }
        return null;
    }

}
