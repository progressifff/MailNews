package com.progressifff.mailnews.dagger

import com.progressifff.mailnews.presentation.NewsListFragment
import dagger.Subcomponent

@Subcomponent(modules = [NewsListFragmentModule::class])
@NewsListScope
interface NewsListFragmentComponent {
    fun inject(newsListActivity: NewsListFragment)
}