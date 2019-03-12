package com.progressifff.mailnews

import android.support.v7.widget.RecyclerView
import com.progressifff.mailnews.presentation.presenters.BasicNewsListPresenter

abstract class BasicNewsListAdapter<T: BasicNewsListPresenter<*>> (protected val newsListPresenter: T) : PaginationAdapter() {

    override fun onBindItemViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if (holder is NewsViewHolder) newsListPresenter.onBindNewsListItem(holder, position)
    }

    override fun getItemCount(): Int {
        return newsListPresenter.newsCount
    }
}
