package com.progressifff.mailnews.data

import io.reactivex.Single

interface NewsRepository {
    fun getNewsList(
        count: Int
    ) : Single<NewsDTO>
}