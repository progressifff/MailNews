package com.progressifff.mailnews.presentation.presenters

import android.annotation.SuppressLint
import com.progressifff.mailnews.RxBus
import com.progressifff.mailnews.RxEvent
import com.progressifff.mailnews.domain.FavoriteNewsUseCases
import com.progressifff.mailnews.presentation.views.FavoriteNewsView
import com.progressifff.mailnews.presentation.views.NewsListItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class FavoriteNewsPresenter @Inject constructor(private val favoriteNewsInteractor: FavoriteNewsUseCases, private val eventBus: RxBus) : BasicNewsListPresenter<FavoriteNewsView>(){
    private lateinit var favoriteNewsChangedDisposable: Disposable

    override fun bindView(v: FavoriteNewsView) {
        super.bindView(v)
        favoriteNewsChangedDisposable = eventBus.listen(RxEvent.FavoriteNewsItemChanged::class.java).subscribe(::handleRxEvent)
    }

    override fun unbindView() {
        super.unbindView()
        favoriteNewsChangedDisposable.dispose()
    }

    @SuppressLint("CheckResult")
    override fun loadNewsList(page: Int) {
        favoriteNewsInteractor.getNewsListBy(page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::onNewsListReceived, this::onError)
    }

    override fun onBindNewsListItem(newsListItem: NewsListItem, itemPosition: Int){
        val newsItem = newsListState.data[itemPosition]
        newsListItem.setCategory(newsItem.categories.joinToString())
        newsListItem.setTitle(newsItem.title)
        newsListItem.setDescription(newsItem.description)
        newsListItem.setPubDate(newsItem.pubDate)
    }

    @SuppressLint("CheckResult")
    fun onDeleteNewsFromFavorites(index: Int){
        if(index >= 0 && index < newsListState.data.size){
            val news = newsListState.data.removeAt(index)
            if(newsListState.data.isEmpty()){
                view?.showEmptyNewsMsg()
            }
            val newsId = news.guid
            favoriteNewsInteractor.delete(newsId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe {
                view?.deleteNewsItem(newsId, index)
            }
        }
    }

    private fun handleRxEvent(event: RxEvent.FavoriteNewsItemChanged){
        resetNewsList()
    }
}