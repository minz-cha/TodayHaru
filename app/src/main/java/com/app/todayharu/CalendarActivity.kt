package com.app.todayharu

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.Button
import android.widget.CalendarView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class CalendarActivity : AppCompatActivity() {

    lateinit var dbHelper: DBHelper
    lateinit var sqlDB: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val calendar = findViewById<CalendarView>(R.id.calendar)
        val date = findViewById<TextView>(R.id.title)
        val btnGoList = findViewById<Button>(R.id.btnGoList)

        calendar.setOnDateChangeListener { _, year, month, dayOfMonth ->
            date.text = String.format("%d.%02d.%02d", year, month + 1, dayOfMonth)

            val intent = Intent(this, WriteDiaryActivity::class.java)
            intent.putExtra("dateData", date.text)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent)
        }

        btnGoList.setOnClickListener {
            intent = Intent(this, DiaryList::class.java)
            startActivity(intent)
        }
    }
}