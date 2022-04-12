package com.app.todayharu

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text

class DiaryList : AppCompatActivity() {

    lateinit var dbHelper: DBHelper
    lateinit var sqlDB: SQLiteDatabase
    lateinit var listDate: String
    lateinit var listContent: String
    var diaryList = ArrayList<Diary>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)

        val diaryAdapter = DiaryRvAdapter(this)
        val diaryRv = findViewById<RecyclerView>(R.id.diaryRecyclerView)
        val lm = LinearLayoutManager(this)

        dbHelper = DBHelper.getInstance(this)
        sqlDB = dbHelper.readableDatabase

        var cursor = sqlDB.rawQuery("SELECT * FROM diaryTBL ORDER BY date DESC;", null)

        while (cursor.moveToNext()) {
            listDate = cursor.getString(0)
            listContent = cursor.getString(1)
            diaryList.add(Diary(listDate, listContent))
        }
        diaryAdapter.diaryList = diaryList

        diaryRv.adapter = diaryAdapter
        diaryRv.layoutManager = lm
        diaryRv.setHasFixedSize(true)

        cursor.close()
        sqlDB.close()
    }
}