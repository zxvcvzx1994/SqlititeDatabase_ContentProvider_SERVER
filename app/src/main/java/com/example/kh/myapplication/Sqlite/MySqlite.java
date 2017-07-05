package com.example.kh.myapplication.Sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.kh.myapplication.Module.HOCSINH;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kh on 7/4/2017.
 */

public class MySqlite {
    private SQLiteDatabase sqLiteDatabase;
    private Context context;
    private static  MySqlite mySqlite;
    private final static String DATABASE = "QUANLY.db";
    private final static int VERSION = 2;
    private final String TABLE ="SINHVIEN";
    private final  String TEN ="ten";
    private final String LOP = "lop";
    private final String ID ="id";
    private final String SQL = "CREATE TABLE "+TABLE+" ("+ID+" INTEGER PRIMARY KEY, "+TEN+" TEXT NOT NULL,"+LOP+" TEXT NULL)";
    private MySqlite(Context context){
        this.context = context;
        sqLiteDatabase = new MysqliteHelper(context, DATABASE, null,VERSION).getWritableDatabase();
    }

    public synchronized static MySqlite getMySqlite(Context context){
        if(mySqlite==null){
            mySqlite = new MySqlite(context);
        }
        return mySqlite;
    }

    public boolean insert(String name, String lop){
        ContentValues contentValues = new ContentValues();
        contentValues.put("ten",name);
        contentValues.put("lop",lop);
     return    sqLiteDatabase.insert(TABLE,null,contentValues)>0;
    }

    public long insert(ContentValues contentValues){
        return sqLiteDatabase.insert(TABLE,null, contentValues);
    }

    public boolean update(int id, String name, String lop){
        ContentValues contentValues = new ContentValues();
        contentValues.put("ten",name);
        contentValues.put("lop",lop);
        String wherearg =  "id = "+id;
        return sqLiteDatabase.update(TABLE,contentValues,wherearg,null)>0;
    }

    public int update(ContentValues contentValues, String whereClause, String[] strings){
        return sqLiteDatabase.update(TABLE,contentValues,whereClause,strings);
    }

    public boolean delete(int id){
        return sqLiteDatabase.delete(TABLE,"id ="+id, null)>0;
    }

    public int delete(String whereClause, String[] whereArgument ){
        return sqLiteDatabase.delete(TABLE, whereClause, whereArgument);
    }

    public List<HOCSINH> getAllData(){
         List<HOCSINH> list = new ArrayList<HOCSINH>();
        String query = "select * from "+TABLE;
            Cursor cursor  = sqLiteDatabase.query(TABLE,new String[]{ID,TEN,LOP},null,null,null,null,ID,null);
        if(cursor.getCount()>0 && cursor!=null){
            while (cursor.moveToNext()){
                HOCSINH hocsinh = new HOCSINH(cursor.getInt(0), cursor.getString(1), cursor.getString(2));
                list.add(hocsinh);
            }
        }
        return list;
    }

    public Cursor getCursorforSpecificPlace(String ten){
        Cursor cursor  = sqLiteDatabase.query(TABLE,new String[]{ID,TEN,LOP},TEN+" LIKE '%"+ten+"%'",null,null,null,ID,null);
        return cursor;
    }
    public Cursor getCursorforSpecificPlace(){
        Cursor cursor  = sqLiteDatabase.query(TABLE,new String[]{ID,TEN,LOP},null,null,null,null,ID,null);
        return cursor;
    }

    public Cursor getCount(){
        Cursor cursor  = sqLiteDatabase.rawQuery("select count(*) from "+TABLE,null);
        return cursor;
    }



    public class MysqliteHelper extends SQLiteOpenHelper{

        public MysqliteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            switch (oldVersion){
                case 1:
                    db.execSQL("ALTER TABLE "+TABLE+" ADD COLUMN "+ LOP+" TEXT NULL");
            }
        }
    }
}
