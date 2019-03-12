package com.progressifff.mailnews.dagger

import com.progressifff.mailnews.RxBus
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RxBusModule {
    @Singleton
    @Provides
    fun eventBus(): RxBus = RxBus
}