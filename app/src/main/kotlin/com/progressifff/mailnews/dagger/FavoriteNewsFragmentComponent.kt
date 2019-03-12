package com.progressifff.mailnews.dagger

import com.progressifff.mailnews.presentation.FavoritesNewsFragment
import dagger.Subcomponent

@Subcomponent(modules = [FavoriteNewsFragmentModule::class])
@FavoriteNewsScope
interface FavoriteNewsFragmentComponent {
    fun inject(favoriteNewsFragment: FavoritesNewsFragment)
}