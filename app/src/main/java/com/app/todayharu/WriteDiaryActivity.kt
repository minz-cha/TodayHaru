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
    lateinit var btnRegister: Button
    var imm: InputMethodManager? = null
    var i: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write)

        todayDate = findViewById(R.id.todayDate)
        etWrite = findViewById(R.id.etWrite)
        btnRegister = findViewById(R.id.btnRegister)

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

        btnRegister.setOnClickListener {
            sqlDB = dbHelper.writableDatabase
            if (etWrite.text.isEmpty()) {
                startToast("글을 입력해주세요.")
            } else {
                dbHelper.onInsertDiary(todayDate.text.toString(), etWrite.text.toString())
                sqlDB.close()
                val intent = Intent(this, CalendarActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent)
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