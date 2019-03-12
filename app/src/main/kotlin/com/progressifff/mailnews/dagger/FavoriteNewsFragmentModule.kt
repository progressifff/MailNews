package com.progressifff.mailnews.dagger

import com.progressifff.mailnews.RxBus
import com.progressifff.mailnews.domain.FavoriteNewsUseCases
import com.progressifff.mailnews.presentation.presenters.FavoriteNewsPresenter
import dagger.Module
import dagger.Provides

@Module
class FavoriteNewsFragmentModule {
    @Provides
    @FavoriteNewsScope
    fun providesFavoriteNewsListPresenter(favoriteNewsUseCases: FavoriteNewsUseCases, eventBus: RxBus): FavoriteNewsPresenter = FavoriteNewsPresenter(favoriteNewsUseCases, eventBus)
}