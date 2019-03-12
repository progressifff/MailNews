package com.progressifff.mailnews.presentation.presenters

import android.annotation.SuppressLint
import com.progressifff.mailnews.domain.NewsListUseCases
import com.progressifff.mailnews.presentation.views.NewsListView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

open class NewsListPresenter @Inject constructor(private val newsListInteractor: NewsListUseCases) : BasicNewsListPresenter<NewsListView>(){

    @SuppressLint("CheckResult")
    override fun loadNewsList(page:Int) {
        newsListInteractor.getNewsListBy(page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::onNewsListReceived, this::onError)
    }
}
