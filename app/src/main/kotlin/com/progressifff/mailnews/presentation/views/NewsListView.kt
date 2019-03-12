package com.progressifff.mailnews.presentation.views

import com.progressifff.mailnews.presentation.models.NewsItem

interface NewsListView {
    fun showLoadNextPageProgressBar()
    fun hideLoadNextPageProgressBar()
    fun updateNewsListRefresher(hide: Boolean)
    fun insertNewsItemsRange(offset: Int, count: Int)
    fun resetNewsList()
    fun showNewsItem(newsItem: NewsItem)
    fun showLoadNewsListErrorMsg()
    fun showEmptyNewsMsg()
}