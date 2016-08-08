package com.example.common.dao;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper{
  private Class<?>[] modelClasses;

  public DBHelper(Context context, String databaseName, SQLiteDatabase.CursorFactory factory, int databaseVersion, Class<?>[] modelClasses){
    super(context, databaseName, factory, databaseVersion);
    this.modelClasses = modelClasses;
  }

  @Override
  public void onCreate(SQLiteDatabase db){
    TableHelper.createTablesByClasses(db, this.modelClasses);
  }

  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
    TableHelper.dropTablesByClasses(db, this.modelClasses);
    onCreate(db);
  }
}
