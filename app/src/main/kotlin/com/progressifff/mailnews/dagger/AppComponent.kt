package com.progressifff.mailnews.dagger

import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class, NetModule::class, RoomModule::class, RxBusModule::class, RepositoryModule::class, UseCasesModule::class])
@Singleton
interface AppComponent {
    fun plus(module: NewsListFragmentModule): NewsListFragmentComponent
    fun plus(module: NewsActivityModule): NewsActivityComponent
    fun plus(module: FavoriteNewsFragmentModule): FavoriteNewsFragmentComponent
}