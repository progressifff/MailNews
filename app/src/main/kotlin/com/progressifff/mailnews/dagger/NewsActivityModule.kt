package com.progressifff.mailnews.dagger

import com.progressifff.mailnews.RxBus
import com.progressifff.mailnews.domain.FavoriteNewsUseCases
import com.progressifff.mailnews.presentation.presenters.NewsItemPresenter
import dagger.Module
import dagger.Provides

@Module
class NewsActivityModule {
    @Provides
    @NewsItemScope
    fun providesNewsItemPresenter(favoriteNewsUseCases: FavoriteNewsUseCases, eventBus: RxBus): NewsItemPresenter = NewsItemPresenter(favoriteNewsUseCases, eventBus)
}