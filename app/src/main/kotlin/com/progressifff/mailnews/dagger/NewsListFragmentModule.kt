package com.progressifff.mailnews.dagger

import com.progressifff.mailnews.domain.NewsListUseCases
import com.progressifff.mailnews.presentation.presenters.NewsListPresenter
import dagger.Module
import dagger.Provides

@Module
class NewsListFragmentModule {
    @Provides
    @NewsListScope
    fun providesNewsListPresenter(newsListUseCases: NewsListUseCases): NewsListPresenter = NewsListPresenter(newsListUseCases)
}