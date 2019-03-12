package com.progressifff.mailnews.dagger

import android.app.Application
import android.arch.persistence.room.Room
import com.progressifff.mailnews.data.NewsDao
import com.progressifff.mailnews.data.NewsDataBase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule(application: Application) {

    private val newsDatabase = Room.databaseBuilder(application, NewsDataBase::class.java, "NewsDataBase").build()

    @Singleton
    @Provides
    fun providesNewsDatabase(): NewsDataBase = newsDatabase

    @Singleton
    @Provides
    fun providesNewsDao(newsDatabase: NewsDataBase): NewsDao = newsDatabase.newsDao()
}