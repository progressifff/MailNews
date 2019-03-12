package com.progressifff.mailnews.data

import io.reactivex.Single

class NewsDBRepository(private val newsDAO: NewsDao){
    fun getNewsList(page: Int, limit: Int) : Single<List<NewsItemDTO>> = newsDAO.getNewsList(page * limit, limit)
    fun addNews(newsItem: NewsItemDTO) = newsDAO.addNewsItem(newsItem)
    fun delete(newsId: String) = newsDAO.delete(newsId)
    fun getNewsIndex(newsId: String): Long = newsDAO.getNewsItemIndex(newsId)
    fun newsCount(): Single<Int> = newsDAO.newsCount()
    fun exists(newsId: String): Single<Boolean> = newsDAO.exists(newsId)
}
