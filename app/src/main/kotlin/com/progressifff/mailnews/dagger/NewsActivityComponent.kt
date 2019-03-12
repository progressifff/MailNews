package com.progressifff.mailnews.dagger

import com.progressifff.mailnews.presentation.NewsItemActivity
import dagger.Subcomponent

@Subcomponent(modules = [NewsActivityModule::class])
@NewsItemScope
interface NewsActivityComponent {
    fun inject(newsItemActivity: NewsItemActivity)
}