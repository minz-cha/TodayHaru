package com.app.todayharu

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.CalendarView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class CalendarActivity : AppCompatActivity() {

    lateinit var dbHelper: DBHelper
    lateinit var sqlDB: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        calendar.maxDate = System.currentTimeMillis()
        window.statusBarColor = Color.parseColor("#000000")

        dbHelper = DBHelper.getInstance(this)
        sqlDB = dbHelper.readableDatabase
        val calendar = findViewById<CalendarView>(R.id.calendar)
        val date = findViewById<TextView>(R.id.title)
        val goToList = findViewById<Button>(R.id.goToList)

        calendar.setOnDateChangeListener { _, year, month, dayOfMonth ->
            date.text = String.format("%d.%02d.%02d", year, month + 1, dayOfMonth)

            if (dbHelper.onFindDiary(date.text.toString())) {
                val intent = Intent(this, DiaryDetail::class.java)
                intent.putExtra("diaryData", date.text)
                startActivityForResult(intent, 100)
            } else {

                val intent = Intent(this, WriteDiaryActivity::class.java)
                intent.putExtra("dateData", date.text)
                startActivity(intent)
            }
        }

        goToList.setOnClickListener {
            val intent = Intent(this, DiaryList::class.java)
            startActivity(intent)
        }
    }
}