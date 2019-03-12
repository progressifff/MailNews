package com.progressifff.mailnews.presentation.presenters

import android.support.v7.widget.LinearLayoutManager
import com.progressifff.mailnews.*
import com.progressifff.mailnews.Constants.LIMIT_NEWS_LIST
import com.progressifff.mailnews.presentation.models.NewsItem
import com.progressifff.mailnews.presentation.views.NewsListItem
import com.progressifff.mailnews.presentation.views.NewsListView
import kotlin.properties.Delegates

abstract class BasicNewsListPresenter<V: NewsListView> : BasePresenter<V>() {
    protected var newsListState by Delegates.observable<NewsListState>(DefaultNewsListState()) { _, _, _ -> onNewsListStateUpdated() }
    val newsCount: Int get() = newsListState.data.size
    private var previousNewsCount = newsCount
    private var isUpdatePending: Boolean = false

    override fun bindView(v: V) {
        super.bindView(v)

        if(isUpdatePending){
            processNewNewsListState()
            isUpdatePending = false
        }
    }

    open fun onBindNewsListItem(newsListItem: NewsListItem, itemPosition: Int){
        val newsItem = newsListState.data[itemPosition]
        newsListItem.setCategory(newsItem.categories.joinToString())
        newsListItem.setTitle(newsItem.title)
        newsListItem.setDescription(newsItem.description)
        newsListItem.setPubDate(newsItem.pubDate)
    }

    fun resetNewsList(){
        updateNewsList(true)
    }

    fun updateNewsList(reset: Boolean = false){
        if (reset) {
            newsListState = LoadingNewsListState(newsListState.pageNum, false, newsListState.data)
            loadNewsList()
        }
        else {
            newsListState = PaginatingNewsListState(newsListState.pageNum, false, newsListState.data)
            loadNewsList(newsListState.pageNum)
        }
    }

    fun onNewsItemSelected(index: Int){
        view!!.showNewsItem(newsListState.data[index])
    }

    private fun onNewsListStateUpdated() {
        if(view != null){
            processNewNewsListState()
        }
        else isUpdatePending = true
    }

    private fun processNewNewsListState(){
        when (newsListState) {
            is LoadingNewsListState -> view!!.updateNewsListRefresher(false)
            is DefaultNewsListState -> {
                view!!.updateNewsListRefresher(true)
                if(newsListState.data.isEmpty()){
                    view!!.showEmptyNewsMsg()
                }
                else {
                    view!!.insertNewsItemsRange(previousNewsCount, newsListState.data.size)
                }
            }
            is ErrorNewsListState -> {
                view!!.updateNewsListRefresher(true)
                view!!.hideLoadNextPageProgressBar()
                view!!.showLoadNewsListErrorMsg()
            }
        }
    }

    private fun loadNextNewsPage(){
        view!!.showLoadNextPageProgressBar()
        updateNewsList()
    }

    protected fun onNewsListReceived(newsList: List<NewsItem>) {
        val currentPageNum: Int
        val currentNewsList: ArrayList<NewsItem>

        if(newsListState is LoadingNewsListState){
            currentPageNum = 1
            previousNewsCount = 0
            currentNewsList = arrayListOf()
            view?.resetNewsList()
        }
        else{
            currentPageNum = newsListState.pageNum + 1
            previousNewsCount = newsListState.data.size
            currentNewsList = newsListState.data
        }

        currentNewsList.addAll(newsList)
        newsListState = DefaultNewsListState(currentPageNum, newsList.size < LIMIT_NEWS_LIST, currentNewsList)
    }

    protected fun onError(error: Throwable) {
        error.printStackTrace()
        newsListState = ErrorNewsListState(error.message ?: "", newsListState.pageNum, newsListState.allItemsLoaded, newsListState.data)
    }

    protected abstract fun loadNewsList(page:Int = 0)

    inner class OnScrollListener(layoutManager: LinearLayoutManager) : PaginationScrollListener(layoutManager) {
        override fun isLoading() = (newsListState is LoadingNewsListState) || (newsListState is PaginatingNewsListState)
        override fun loadMoreItems() = loadNextNewsPage()
        override fun getTotalPageCount() = LIMIT_NEWS_LIST
        override fun isLastPage() = newsListState.allItemsLoaded
    }
}