package com.app.todayharu

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, "haruDB", null, 2) {
    companion object {
        var INSTANCE: DBHelper? = null
        fun getInstance(context: Context): DBHelper {
            if (INSTANCE == null)
                INSTANCE = DBHelper(context)
            return INSTANCE!!
        }
    }

    fun onGetDate(date: String): DiaryData {
        val cursor =
            readableDatabase.rawQuery("SELECT * FROM diaryTBL WHERE date=\"$date\"",
                null)
        cursor.moveToFirst()
        val date = cursor.getString(0)
        val content = cursor.getString(1)
        cursor.close()
        return DiaryData(date, content)
    }

    fun onFindDiary(date: String): Boolean {
        val cursor =
            readableDatabase.rawQuery("SELECT EXISTS (SELECT * FROM diaryTBL WHERE date=\"$date\" LIMIT 1) as success;",
                null)
        cursor.moveToFirst()
        if (cursor.getInt(0) == 1) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
    }

    fun onInsertDiary(date: String, content: String) {
        writableDatabase.execSQL(
            "INSERT INTO diaryTBL (date, content) Values (?, ?);",
            arrayOf(date, content)
        )
        writableDatabase.close()
    }

    fun onUpdateDiary(date: String, content: String) {
        writableDatabase.execSQL(
            "UPDATE diaryTBL SET content=\"$content\" WHERE date=\"$date\""
        )
    }

    fun onDeleteDiary(date: String) {
        writableDatabase.execSQL(
            "DELETE FROM diaryTBL WHERE date=\"$date\""
        )
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        p0!!.execSQL("CREATE TABLE diaryTBL (date VARCHAR(30) PRIMARY KEY, content VARCHAR(500) NOT NULL);")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0!!.execSQL("DROP TABLE IF EXISTS diaryTBL")
        onCreate(p0)
    }
}