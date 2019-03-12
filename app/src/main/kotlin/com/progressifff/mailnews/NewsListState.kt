package com.progressifff.mailnews

import com.progressifff.mailnews.presentation.models.NewsItem

sealed class NewsListState {
    abstract val pageNum: Int
    abstract val allItemsLoaded: Boolean
    abstract val data: ArrayList<NewsItem>
}

data class DefaultNewsListState(override val pageNum: Int = 0, override val allItemsLoaded: Boolean = false, override val data: ArrayList<NewsItem> = arrayListOf()) : NewsListState()
data class LoadingNewsListState(override val pageNum: Int = 0, override val allItemsLoaded: Boolean = false, override val data: ArrayList<NewsItem> = arrayListOf()) : NewsListState()
data class PaginatingNewsListState(override val pageNum: Int = 0, override val allItemsLoaded: Boolean = false, override val data: ArrayList<NewsItem> = arrayListOf()) : NewsListState()
data class ErrorNewsListState(val errorMessage: String, override val pageNum: Int, override val allItemsLoaded: Boolean, override val data: ArrayList<NewsItem>) : NewsListState()
