package com.progressifff.mailnews.presentation.views

interface FavoriteNewsView : NewsListView {
    fun deleteNewsItem(newsItemId: String, newsItemIndex: Int)
}