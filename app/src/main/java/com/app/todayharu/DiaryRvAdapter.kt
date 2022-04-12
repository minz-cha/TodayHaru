package com.app.todayharu

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DiaryRvAdapter(private val context: Context) :
    RecyclerView.Adapter<DiaryRvAdapter.Holder>() {
    var diaryList = listOf<Diary>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view =LayoutInflater.from(context).inflate(R.layout.item_recycler, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return diaryList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(diaryList[position])
    }

    inner class Holder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        private val listDate = itemView?.findViewById<TextView>(R.id.listDate)
        private val listContent = itemView?.findViewById<TextView>(R.id.listContent)

        fun bind(diary: Diary) {
            listDate?.text = diary.date
            listContent?.text = diary.content
        }
    }
}