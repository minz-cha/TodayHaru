package com.app.todayharu

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class WriteDiaryActivity : AppCompatActivity() {
    lateinit var todayDate : TextView
    lateinit var etWrite: EditText
    var imm: InputMethodManager? = null
    var i: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write)

        todayDate = findViewById(R.id.todayDate)
        etWrite = findViewById(R.id.etWrite)
        imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

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
    }

    fun hideKeyboard() {
        imm?.hideSoftInputFromWindow(etWrite.windowToken, 0)
    }

    fun showKeyboard() {
        imm?.showSoftInput(etWrite, 0)
    }
}