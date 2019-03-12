package com.progressifff.mailnews.dagger

import com.progressifff.mailnews.data.NewsDBRepository
import com.progressifff.mailnews.data.NewsRepository
import com.progressifff.mailnews.domain.*
import dagger.Module
import dagger.Provides

@Module
class UseCasesModule {
    @Provides
    fun providesNewsListUseCases(newsRepository: NewsRepository): NewsListUseCases = NewsListInteractor(newsRepository)

    @Provides
    fun providesFavoriteNewsCases(newsDbRepository: NewsDBRepository): FavoriteNewsUseCases = FavoriteNewsInteractor(newsDbRepository)
}