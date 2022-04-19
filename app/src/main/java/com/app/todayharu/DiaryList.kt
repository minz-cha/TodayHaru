package com.app.todayharu

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_recycler_view.*

class DiaryList : AppCompatActivity() {

    lateinit var dbHelper: DBHelper
    lateinit var sqlDB: SQLiteDatabase
    lateinit var listDate: String
    lateinit var listContent: String
    lateinit var dailyNone: TextView
    lateinit var diaryRv: RecyclerView
    var diaryList = mutableListOf<DiaryData>()
    var filteredList = mutableListOf<DiaryData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)
        setSupportActionBar(toolbarList)
        supportActionBar!!.title = null

        diaryRv = findViewById<RecyclerView>(R.id.diaryRecyclerView)
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
        val diaryAdapter = DiaryRvAdapter(diaryList)
        diaryRv.adapter = diaryAdapter
        diaryRv.layoutManager = lm
        diaryRv.setHasFixedSize(true)

        cursor.close()

        if (diaryList.size > 0) {
            dailyNone.visibility = View.GONE
        }

        diaryAdapter.setOnItemClickListener(object : DiaryRvAdapter.OnItemClickListener {
            override fun onItemClick(v: View, diary: DiaryData, pos: Int) {
                val intent = Intent(applicationContext, DiaryDetail::class.java)
                intent.putExtra("diaryData", diary)
                startActivityForResult(intent, 100)
                overridePendingTransition(R.anim.slide_right_enter, R.anim.slide_right_exit)
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        sqlDB.close()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_search, menu)

        val searchView = menu.findItem(R.id.action_search).actionView as SearchView
        var searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = getString(R.string.search_view_hint)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                filteredList.clear()

                if (newText != null) {
                    (0 until diaryList.size)
                        .filter {
                            diaryList[it].content.toLowerCase().contains(newText.toLowerCase())
                        }
                        .mapTo(filteredList) { diaryList[it] }
                }

                val diaryAdapter = DiaryRvAdapter(filteredList)

                diaryAdapter.setOnItemClickListener(object : DiaryRvAdapter.OnItemClickListener {
                    override fun onItemClick(v: View, diary: DiaryData, pos: Int) {
                        val intent = Intent(applicationContext, DiaryDetail::class.java)
                        intent.putExtra("diaryData", diary)
                        startActivity(intent)
                    }
                })

                diaryRv.adapter = DiaryRvAdapter(filteredList)
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.d("query", query.toString())
                return true
            }
        })
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_left_enter, R.anim.slide_left_exit)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("result", "1234")
        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {
                Log.d("result", "1234")
                var cursor = sqlDB.rawQuery("SELECT * FROM diaryTBL ORDER BY date DESC;", null)
                diaryList.clear()
                while (cursor.moveToNext()) {
                    listDate = cursor.getString(0)
                    listContent = cursor.getString(1)
                    diaryList.add(DiaryData(listDate, listContent))
                }
                val diaryAdapter = DiaryRvAdapter(diaryList)
                diaryRv.adapter = diaryAdapter
                cursor.close()
            }
        }
    }
}