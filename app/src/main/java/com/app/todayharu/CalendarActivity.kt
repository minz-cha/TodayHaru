package com.app.todayharu

import android.content.Intent
import android.os.Bundle
import android.widget.CalendarView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class CalendarActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val calendar = findViewById<CalendarView>(R.id.calendar)
        val date = findViewById<TextView>(R.id.date)

        calendar.setOnDateChangeListener { _, year, month, dayOfMonth ->
            date.text = String.format("%d / %d / %d", year, month + 1, dayOfMonth)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}