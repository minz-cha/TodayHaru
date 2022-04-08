package com.app.todayharu

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DiaryRvAdapter(val context: Context, val diaryList: ArrayList<Diary>) :
    RecyclerView.Adapter<DiaryRvAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view =LayoutInflater.from(context).inflate(R.layout.item_recycler, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return diaryList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder?.bind(diaryList[position], context)
    }

    inner class Holder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        val listDate = itemView?.findViewById<TextView>(R.id.listDate)
        val listContent = itemView?.findViewById<TextView>(R.id.listContent)

        fun bind(diary: Diary, context: Context) {
            listDate?.text = diary.date
            listContent?.text = diary.content
        }
    }


}