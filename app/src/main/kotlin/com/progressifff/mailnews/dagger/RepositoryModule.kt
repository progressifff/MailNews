package com.progressifff.mailnews.dagger

import com.progressifff.mailnews.data.*
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Provides
    @Singleton
    fun providesNewsRepository(newsNetApi: NewsNetApi): NewsRepository = NewsLoader(newsNetApi)

    @Provides
    @Singleton
    fun providesNewsDBRepository(newsDao: NewsDao): NewsDBRepository = NewsDBRepository(newsDao)
}