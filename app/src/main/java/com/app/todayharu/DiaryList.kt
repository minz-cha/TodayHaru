package com.app.todayharu

import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class DiaryList : AppCompatActivity() {

    lateinit var dbHelper: DBHelper
    lateinit var sqlDB: SQLiteDatabase

    var diaryList = arrayListOf<Diary>(
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        val diaryAdpater = DiaryRvAdapter(this, diaryList)
        val diaryRv = findViewById<RecyclerView>(R.id.diaryRecyclerView)
        diaryRv.adapter = diaryAdpater

        val lm = LinearLayoutManager(this)
        diaryRv.layoutManager = lm
        diaryRv.setHasFixedSize(true)
    }
}