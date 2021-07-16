package com.example.practiceproject

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log


class MyDatabaseHelper(content: Context, name: String, version: Int) :
    SQLiteOpenHelper(content, name, null, version) {
    private val createuser = "create table user (" +
            "user_id integer primary key autoincrement," +
            "name text ," +
            "sno integer," +
            "iphone integer," +
            "number text," +
            "password text)"

    private val createbodydata = "create table bodydata(" +
            "number integer," +
            "height integer," +
            "weight float," +
            "time text)"

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(createuser)
        db.execSQL(createbodydata)
        Log.d("Loginactivity", "succeed")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("drop table if exists user")
        db.execSQL("drop table if exists bodydata")
        onCreate(db)
    }
}