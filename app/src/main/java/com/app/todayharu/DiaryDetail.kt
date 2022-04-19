package com.app.todayharu

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class DiaryDetail : AppCompatActivity() {

    lateinit var dbHelper: DBHelper
    lateinit var sqlDB: SQLiteDatabase
    lateinit var diaryList: DiaryData
    lateinit var btnChange: Button
    lateinit var btnDelete: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary_detail)
        diaryList = intent.getParcelableExtra("diaryData")!!
        dbHelper = DBHelper.getInstance(this)

        var dailyDate = findViewById<TextView>(R.id.dailyDate)
        var dailyContent = findViewById<TextView>(R.id.dailyContent)
        var i = 0

        dailyDate.text = diaryList.date
        dailyContent.text = diaryList.content

        btnChange = findViewById(R.id.btnChange)
        btnDelete = findViewById(R.id.btnDelete)

        btnChange.setOnClickListener {
            if (i == 0) {
                dailyContent.isEnabled = true
                i = 1
            } else if (i == 1) {
                try {
                    dailyContent.isEnabled = false
                    dbHelper.onUpdateDiary(dailyDate.text.toString(), dailyContent.text.toString())
                    i = 0
                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                    startToast("유효한 값을 입력해주세요.")
                }
            }

        }

        btnDelete.setOnClickListener {
            try {
                sqlDB = dbHelper.writableDatabase
                dbHelper.onDeleteDiary(dailyDate.text.toString())
                sqlDB.close()
                val intent = Intent(this, DiaryList::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent)
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
                startToast("유효한 값을 입력해주세요.")
            }
        }
    }

    fun startToast(msg: String) {
        Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onBackPressed() {
        val intent = Intent(this, DiaryList::class.java)
        setResult(RESULT_OK, intent)
        overridePendingTransition(R.anim.slide_left_enter, R.anim.slide_left_exit)
        finish()
    }
}