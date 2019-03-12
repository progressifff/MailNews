package com.progressifff.mailnews.domain

import com.progressifff.mailnews.presentation.models.NewsItem
import io.reactivex.Single

interface NewsListUseCases {
    fun getNewsListBy(
        page: Int
    ) : Single<List<NewsItem>>
}