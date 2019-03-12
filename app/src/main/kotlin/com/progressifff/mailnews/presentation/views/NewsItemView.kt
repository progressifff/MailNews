package com.progressifff.mailnews.presentation.views

import com.progressifff.mailnews.presentation.models.NewsItem

interface NewsItemView {
    fun updateNewsPageRefresher(hide: Boolean)
    fun invalidateFavoriteMenuItem()
    fun loadNewsPage(newsItem: NewsItem)
}