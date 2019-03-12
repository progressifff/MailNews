package com.progressifff.mailnews.domain

import com.progressifff.mailnews.presentation.models.NewsItem
import io.reactivex.Completable
import io.reactivex.Single

interface FavoriteNewsUseCases {
    fun getNewsListBy(page: Int) : Single<List<NewsItem>>
    fun addNewsItem(newsItem: NewsItem): Completable
    fun delete(guid: String): Completable
    fun getNewsItemIndex(guid: String): Single<Long>
    fun newsCount(guid: String): Single<Int>
    fun exists(guid: String): Single<Boolean>
}