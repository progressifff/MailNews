package com.progressifff.mailnews.dagger

import javax.inject.Scope

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class NewsListScope

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class NewsItemScope

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class FavoriteNewsScope