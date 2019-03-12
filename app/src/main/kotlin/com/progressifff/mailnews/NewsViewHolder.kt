package com.progressifff.mailnews

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.progressifff.mailnews.presentation.views.NewsListItem
import java.util.*
import java.text.SimpleDateFormat

open class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), NewsListItem {
    protected val title = itemView.findViewById<TextView>(R.id.title)!!
    protected val category = itemView.findViewById<TextView>(R.id.category)!!
    protected val description = itemView.findViewById<TextView>(R.id.description)!!
    protected val pubDate = itemView.findViewById<TextView>(R.id.pubDate)!!

    override fun setTitle(title: String) {
        this.title.text = title
    }

    override fun setCategory(category: String) {
        this.category.text = category
    }

    override fun setDescription(description: String) {
        this.description.text = description
    }

    @SuppressLint("SimpleDateFormat")
    override fun setPubDate(date: Date) {
        val pattern = "MM/dd/yyyy HH:mm:ss"
        val df = SimpleDateFormat(pattern)
        this.pubDate.text = df.format(date)
    }
}