package com.app.todayharu

import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class WriteDiaryActivity : AppCompatActivity() {

    lateinit var dbHelper: DBHelper
    lateinit var sqlDB: SQLiteDatabase
    lateinit var todayDate: TextView
    lateinit var etWrite: EditText
    lateinit var etCheck1: EditText
    lateinit var etCheck2: EditText
    lateinit var btnChange: Button
    lateinit var btnRegister: Button
    lateinit var btnDelete: Button
    var imm: InputMethodManager? = null
    var i: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write)

        todayDate = findViewById(R.id.todayDate)
        etWrite = findViewById(R.id.etWrite)
        btnChange = findViewById(R.id.btnChange)
        btnRegister = findViewById(R.id.btnRegister)
        btnDelete = findViewById(R.id.btnDelete)
        etCheck1 = findViewById(R.id.etCheck1)
        etCheck2 = findViewById(R.id.etCheck2)

        imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        dbHelper = DBHelper.getInstance(this)

        todayDate.text = intent.getStringExtra("dateData")
        etWrite.setOnClickListener {
            if (i == 0) {
                showKeyboard()
                i = 1
            } else if (i == 1) {
                hideKeyboard()
                i = 0
            }
        }

        val btngoList = findViewById<Button>(R.id.gotoList)
        btngoList.setOnClickListener {
            val intent = Intent(this, DiaryList::class.java)
            startActivity(intent)
        }

        btnChange.setOnClickListener {
            try {
                sqlDB = dbHelper.writableDatabase
                dbHelper.onUpdateDiary(todayDate.text.toString(), etWrite.text.toString())
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
                startToast("유효한 값을 입력해주세요.")
            }
        }

        btnRegister.setOnClickListener {
            sqlDB = dbHelper.writableDatabase
            if (etWrite.text.isEmpty()) {
                startToast("글을 입력해주세요.")
            } else {
                dbHelper.onInsertDiary(todayDate.text.toString(), etWrite.text.toString())
                sqlDB.close()
                val intent = Intent(this, CalendarActivity::class.java)
                //intent.putExtra("dateData", todayDate.text)
                startActivity(intent)
            }
        }

        btnDelete.setOnClickListener {
            try {
                sqlDB = dbHelper.writableDatabase
                dbHelper.onDeleteDiary(todayDate.text.toString())
                sqlDB.close()
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
                startToast("유효한 값을 입력해주세요.")
                etCheck1.setText("")
                etCheck2.setText("")
            }
        }

    }

    fun hideKeyboard() {
        imm?.hideSoftInputFromWindow(etWrite.windowToken, 0)
    }

    fun showKeyboard() {
        imm?.showSoftInput(etWrite, 0)
    }

    fun startToast(msg: String) {
        Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
    }
}