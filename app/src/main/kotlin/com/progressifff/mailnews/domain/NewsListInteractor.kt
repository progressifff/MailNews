package com.progressifff.mailnews.domain

import com.progressifff.mailnews.Constants.LIMIT_NEWS_LIST
import com.progressifff.mailnews.data.NewsRepository
import com.progressifff.mailnews.presentation.models.NewsItem
import io.reactivex.Single
import java.lang.Exception

class NewsListInteractor(private val newsRepository: NewsRepository) : NewsListUseCases{
    override fun getNewsListBy(page: Int): Single<List<NewsItem>> {
        val offset = LIMIT_NEWS_LIST * page
        var itemsCount = offset + LIMIT_NEWS_LIST
        return newsRepository.getNewsList(itemsCount)
            .map { news ->
                if(offset >= news.items.size){
                    throw Exception("Failed to load more items")
                }
                itemsCount = if(itemsCount > news.items.size) news.items.size else itemsCount
                val result = arrayListOf<NewsItem>()
                result.ensureCapacity(itemsCount)
                for(i in offset until itemsCount){
                    val newsDTO = news.items[i]
                    result.add(NewsItem.fromNewsDTO(newsDTO))
                }
                return@map result
            }
    }
}