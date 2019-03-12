package com.progressifff.mailnews.presentation.presenters

import android.annotation.SuppressLint
import com.progressifff.mailnews.BasePresenter
import com.progressifff.mailnews.RxBus
import com.progressifff.mailnews.RxEvent
import com.progressifff.mailnews.domain.FavoriteNewsUseCases
import com.progressifff.mailnews.presentation.models.NewsItem
import com.progressifff.mailnews.presentation.views.NewsItemView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class NewsItemPresenter @Inject constructor(private val favoriteNewsInteractor: FavoriteNewsUseCases,
                                            private val eventBus: RxBus) : BasePresenter<NewsItemView>(){
    var newsItem = NewsItem()
    var isNewsItemFavorite = false
    var isLoaded = false

    override fun bindView(v: NewsItemView){
        super.bindView(v)
        view!!.invalidateFavoriteMenuItem()
        view!!.updateNewsPageRefresher(isLoaded)
        if(!isLoaded){
            view!!.loadNewsPage(newsItem)

            val d = favoriteNewsInteractor.exists(newsItem.guid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { isFavorite ->
                    isNewsItemFavorite = isFavorite
                    view?.invalidateFavoriteMenuItem()
                }
        }
    }

    fun onNewsPageLoaded(){
        isLoaded = true
        view!!.updateNewsPageRefresher(isLoaded)
    }

    fun onNewsPageLoading(){
        isLoaded = false
    }

    fun onRefreshNewsPage(){
        view!!.loadNewsPage(newsItem)
    }

    @SuppressLint("CheckResult")
    fun onFavoriteMenuItemSelected(checked: Boolean){
        if(checked) favoriteNewsInteractor.addNewsItem(newsItem).subscribeOn(Schedulers.io()).subscribe()
        else favoriteNewsInteractor.delete(newsItem.guid).subscribeOn(Schedulers.io()).subscribe()
        eventBus.publish(RxEvent.FavoriteNewsItemChanged())
        isNewsItemFavorite = checked
    }
}