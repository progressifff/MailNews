package com.progressifff.mailnews.domain

import com.progressifff.mailnews.Constants
import com.progressifff.mailnews.data.NewsItemDTO
import com.progressifff.mailnews.data.NewsDBRepository
import com.progressifff.mailnews.presentation.models.NewsItem
import io.reactivex.Completable
import io.reactivex.Single

class FavoriteNewsInteractor(private val newsDBRepository: NewsDBRepository) : FavoriteNewsUseCases {

    override fun getNewsListBy(page: Int): Single<List<NewsItem>> {
        return newsDBRepository.getNewsList(page, Constants.LIMIT_NEWS_LIST)
            .map { news ->
                val result = arrayListOf<NewsItem>()
                result.ensureCapacity(news.size)
                for(newsDTO in news){
                    result.add(NewsItem.fromNewsDTO(newsDTO))
                }
                return@map result
            }
    }

    override fun addNewsItem(newsItem: NewsItem): Completable {
        return Completable.fromAction {
            newsDBRepository.addNews(NewsItemDTO(newsItem.guid,
                newsItem.categories,
                newsItem.title,
                newsItem.description,
                newsItem.pubDate)
            )
        }
    }

    override fun delete(guid: String): Completable =  Completable.fromAction { newsDBRepository.delete(guid) }

    override fun getNewsItemIndex(guid: String): Single<Long> = Single.fromCallable<Long> { newsDBRepository.getNewsIndex(guid) }

    override fun newsCount(guid: String): Single<Int> = newsDBRepository.newsCount()

    override fun exists(guid: String): Single<Boolean> = newsDBRepository.exists(guid)
}