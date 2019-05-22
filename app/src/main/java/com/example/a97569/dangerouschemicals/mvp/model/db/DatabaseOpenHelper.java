package com.example.a97569.dangerouschemicals.mvp.model.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 *******************************************************************************
 * @FileName:  DatabaseOpenHelper
 * @Package com.yidaoyun.newenergyvehicleandroid.mvp.model.db
 * @Description:  SQLite数据库辅助类
 * @author: lpz
 * @date:   2018/10/16  14:30
 * @version V1.0
 *******************************************************************************
 */
public class DatabaseOpenHelper extends SQLiteOpenHelper {

    public static final Integer VERSION=1;

    public DatabaseOpenHelper(Context context,String name){
        this(context,name,VERSION);
    }

    private DatabaseOpenHelper(Context context,String name,int version){
        this(context,name,null,version);
    }

    private DatabaseOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql="create table records(id integer primary key autoincrement,name varchar(200))";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
