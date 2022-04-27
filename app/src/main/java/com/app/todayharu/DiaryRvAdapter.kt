package com.app.todayharu

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DiaryRvAdapter(private val diaryList: MutableList<DiaryData>) :
    RecyclerView.Adapter<DiaryRvAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_recycler, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return diaryList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(diaryList[position])
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val listDate = itemView.findViewById<TextView>(R.id.listDate)
        private val listContent = itemView.findViewById<TextView>(R.id.listContent)

        fun bind(item: DiaryData) {
            listDate?.text = item.date
            listContent?.text = item.content

            itemView.setOnClickListener {
                if (itemView.context is Activity) {
                    val intent = Intent(itemView.context, DiaryDetail::class.java)
                    intent.putExtra("diaryData", item.date)
                    var activity = (itemView.context) as Activity
                    activity.startActivityForResult(intent, 100)
                }
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(v: View, diary: DiaryData, pos: Int)
    }

    private lateinit var listener: OnItemClickListener

    fun setOnItemClickListener(itemClickListener: OnItemClickListener) {
        listener = itemClickListener
    }
}