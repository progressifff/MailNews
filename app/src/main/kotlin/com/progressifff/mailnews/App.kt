package com.progressifff.mailnews

import android.app.Application
import com.progressifff.mailnews.dagger.*

class App : Application(){
    companion object {
        private lateinit var INSTANCE: App
        val instance: App get() = INSTANCE
        lateinit var appComponent: AppComponent
        var newsListFragmentComponent: NewsListFragmentComponent? = null
        var newsActivityComponent: NewsActivityComponent? = null
        var favoriteNewsFragmentComponent: FavoriteNewsFragmentComponent? = null
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this

        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .netModule(NetModule())
            .rxBusModule(RxBusModule())
            .roomModule(RoomModule(this))
            .repositoryModule(RepositoryModule())
            .useCasesModule(UseCasesModule())
            .build()
    }

    fun plusNewsListFragmentComponent(): NewsListFragmentComponent{
        if (newsListFragmentComponent == null) {
            newsListFragmentComponent = appComponent.plus(NewsListFragmentModule())
        }
        return newsListFragmentComponent!!
    }

    fun clearNewsListFragmentComponent() {
        newsListFragmentComponent = null
    }

    fun plusNewsActivityComponent(): NewsActivityComponent{
        if (newsActivityComponent == null) {
            newsActivityComponent = appComponent.plus(NewsActivityModule())
        }
        return newsActivityComponent!!
    }

    fun clearNewsActivityComponent() {
        newsActivityComponent = null
    }

    fun plusFavoriteNewsFragmentComponent(): FavoriteNewsFragmentComponent{
        if (favoriteNewsFragmentComponent == null) {
            favoriteNewsFragmentComponent = appComponent.plus(FavoriteNewsFragmentModule())
        }
        return favoriteNewsFragmentComponent!!
    }

    fun clearFavoriteNewsFragmentComponent() {
        favoriteNewsFragmentComponent = null
    }
}