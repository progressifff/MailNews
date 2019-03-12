package com.progressifff.mailnews

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import com.progressifff.mailnews.presentation.presenters.FavoriteNewsPresenter
import javax.inject.Inject


class FavoriteNewsListAdapter @Inject constructor(favoriteNewsListPresenter: FavoriteNewsPresenter) : BasicNewsListAdapter<FavoriteNewsPresenter>(favoriteNewsListPresenter) {

    override fun onCreateItemViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.favorite_news_list_item, parent, false)
        return FavoriteNewsViewHolder(view)
    }

    inner class FavoriteNewsViewHolder(itemView: View) : NewsViewHolder(itemView) {
        private val deleteBtn = itemView.findViewById<ImageButton>(R.id.deleteBtn)!!
        init{
            itemView.setOnClickListener { newsListPresenter.onNewsItemSelected(adapterPosition) }
            deleteBtn.setOnClickListener { newsListPresenter.onDeleteNewsFromFavorites(adapterPosition) }
        }
}
}
