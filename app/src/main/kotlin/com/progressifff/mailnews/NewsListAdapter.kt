package com.progressifff.mailnews

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.progressifff.mailnews.presentation.presenters.NewsListPresenter
import javax.inject.Inject


class NewsListAdapter @Inject constructor(presenter: NewsListPresenter) : BasicNewsListAdapter<NewsListPresenter>(presenter) {
    override fun onCreateItemViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.news_list_item, parent, false)
        return SearchedNewsViewHolder(view)
    }

    inner class SearchedNewsViewHolder(itemView: View) : NewsViewHolder(itemView) {
        init{ itemView.setOnClickListener { newsListPresenter.onNewsItemSelected(adapterPosition) } }
    }
}
