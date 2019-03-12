package com.progressifff.mailnews.data

import io.reactivex.Single

class NewsLoader(private val newsNetApi: NewsNetApi) : NewsRepository{
    override fun getNewsList(
        count: Int
    ): Single<NewsDTO> = newsNetApi.getNewsList(count)
}