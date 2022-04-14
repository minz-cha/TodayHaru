package com.app.todayharu

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import android.view.TextureView
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class DiaryList : AppCompatActivity() {

    lateinit var dbHelper: DBHelper
    lateinit var sqlDB: SQLiteDatabase
    lateinit var listDate: String
    lateinit var listContent: String
    lateinit var dailyNone: TextView
    var diaryList = ArrayList<DiaryData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)

        val diaryAdapter = DiaryRvAdapter(this)
        val diaryRv = findViewById<RecyclerView>(R.id.diaryRecyclerView)
        val lm = LinearLayoutManager(this)
        dailyNone = findViewById(R.id.dailyNone)

        dbHelper = DBHelper.getInstance(this)
        sqlDB = dbHelper.readableDatabase

        var cursor = sqlDB.rawQuery("SELECT * FROM diaryTBL ORDER BY date DESC;", null)

        while (cursor.moveToNext()) {
            listDate = cursor.getString(0)
            listContent = cursor.getString(1)
            diaryList.add(DiaryData(listDate, listContent))
        }
        diaryAdapter.diaryList = diaryList

        diaryRv.adapter = diaryAdapter
        diaryRv.layoutManager = lm
        diaryRv.setHasFixedSize(true)

        cursor.close()
        sqlDB.close()

        if (diaryList.size > 0) {
            dailyNone.visibility = View.GONE
        }

        diaryAdapter.setOnItemClickListener(object : DiaryRvAdapter.OnItemClickListener {
            override fun onItemClick(v: View, diary: DiaryData, pos: Int) {
                val intent = Intent(applicationContext, DiaryDetail::class.java)
                intent.putExtra("diaryData", diary)
                startActivity(intent)
            }
        })
    }
}