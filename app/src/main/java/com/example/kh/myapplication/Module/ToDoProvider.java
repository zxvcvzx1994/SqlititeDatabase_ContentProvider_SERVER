package com.example.kh.myapplication.Module;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.kh.myapplication.Sqlite.MySqlite;

/**
 * Created by kh on 7/5/2017.
 */

public class ToDoProvider extends ContentProvider {
    public static final String AUTHORITY = "com.example.kh.myapplication";

    public static final String PATH_TODO_LIST = "TODO_LIST";
    public static final String PATH_TODO_PLACE = "TODO_PLACE";
    public static final String PATH_TODO_COUNT = "TODO_COUNT";

    public static final Uri CONTENT_URI_1 = Uri.parse("content://"+AUTHORITY+"/"+PATH_TODO_LIST);
    public static final Uri CONTENT_URI_2 = Uri.parse("content://"+AUTHORITY+"/"+PATH_TODO_PLACE);
    public static final Uri CONTENT_URI_3 = Uri.parse("content://"+AUTHORITY+"/"+PATH_TODO_COUNT);

    public static final String CONTENT_TYPE_1= ContentResolver.CURSOR_DIR_BASE_TYPE+"/"+PATH_TODO_LIST;
    public static final String CONTENT_TYPE_2= ContentResolver.CURSOR_DIR_BASE_TYPE+"/"+PATH_TODO_PLACE;
    public static final String CONTENT_TYPE_3= ContentResolver.CURSOR_DIR_BASE_TYPE+"/"+PATH_TODO_COUNT;

    public static final int TODO_LIST = 1;
    public static final int TODO_PLACE = 2;
    public static final int TODO_COUNT = 3;

    public static final String MIME_TYPE_1  = ContentResolver.CURSOR_DIR_BASE_TYPE+"/"+"vnd.com.example.kh.myapplication.list";
    public static final String MIME_TYPE_2  = ContentResolver.CURSOR_DIR_BASE_TYPE+"/"+"vnd.com.example.kh.myapplication.place";
    public static final String MIME_TYPE_3  = ContentResolver.CURSOR_ITEM_BASE_TYPE+"/"+"vnd.com.example.kh.myapplication.count";
    private static final UriMatcher MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
        static {
            MATCHER.addURI(AUTHORITY,PATH_TODO_LIST, TODO_LIST);
            MATCHER.addURI(AUTHORITY,PATH_TODO_PLACE, TODO_PLACE);
            MATCHER.addURI(AUTHORITY,PATH_TODO_COUNT, TODO_COUNT);
        }

    private MySqlite mySqlite;


    public Uri insertTodo(Uri uri, ContentValues contentValues){
        long id= mySqlite.insert(contentValues);
        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(CONTENT_TYPE_1+"/"+id);
    }

    public int updateTodo(ContentValues contentValues, String whereClause, String[] argSelection){
        return mySqlite.update(contentValues, whereClause, argSelection);
    }

    public int deleteTodo(String whereClause, String[] whereArgument){
        return mySqlite.delete(whereClause,whereArgument);
    }


    @Override
    public boolean onCreate() {
        mySqlite  = MySqlite.getMySqlite(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
      Cursor cursor = null;
        switch (MATCHER.match(uri)){
            case TODO_LIST: cursor=  mySqlite.getCursorforSpecificPlace();
                break;
            case TODO_PLACE:  cursor= mySqlite.getCursorforSpecificPlace(selectionArgs[0]);
                break;
            case TODO_COUNT:cursor=  mySqlite.getCount();
                break;
            default: cursor = null;
                break;
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (MATCHER.match(uri)){
            case TODO_LIST:
                return  MIME_TYPE_1;
            case TODO_PLACE: return MIME_TYPE_2;
            case TODO_COUNT: return MIME_TYPE_3;
        }
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        Uri insert = null;
        switch (MATCHER.match(uri)){
            case TODO_LIST: insert = insertTodo(uri,values);
                default: new UnsupportedOperationException("Insert operation not supported"); break;
        }
        return insert;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int delete = 0;
        switch (MATCHER.match(uri)){
            case TODO_LIST: delete = deleteTodo(selection, selectionArgs);
                break;
            default: new UnsupportedOperationException("Delete operation not supported"); break;
        }
        return delete;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
      int update= 0;
        switch (MATCHER.match(uri)){
            case TODO_LIST: update = updateTodo(values, selection,selectionArgs);
                break;
            default: new UnsupportedOperationException("Update operation not supported"); break;
        }
        return update;
    }
}
