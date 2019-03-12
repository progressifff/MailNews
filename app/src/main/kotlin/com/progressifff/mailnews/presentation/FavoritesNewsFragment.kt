package com.progressifff.mailnews.presentation

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import com.progressifff.mailnews.*
import com.progressifff.mailnews.presentation.models.NewsItem
import com.progressifff.mailnews.presentation.presenters.FavoriteNewsPresenter
import com.progressifff.mailnews.presentation.views.FavoriteNewsView
import java.lang.Exception
import javax.inject.Inject

class FavoritesNewsFragment : Fragment(), FavoriteNewsView {

    @Inject
    lateinit var presenter: FavoriteNewsPresenter

    @Inject
    lateinit var newsListAdapter: FavoriteNewsListAdapter

    private lateinit var newsListRefresher: SwipeRefreshLayout
    private lateinit var newsList: RecyclerView
    private lateinit var noFavoriteNewsMsg: LinearLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        App.instance.plusFavoriteNewsFragmentComponent()
        val view = inflater.inflate(R.layout.favorites_fragment, container, false)
        initPresenter(savedInstanceState)
        initializeFavoriteNewsListView(view)
        initializeFavoriteNewsListRefresherView(view)
        noFavoriteNewsMsg = view.findViewById(R.id.noFavoritesMsg)
        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        App.instance.clearFavoriteNewsFragmentComponent()
    }

    override fun onStart() {
        super.onStart()
        presenter.bindView(this)
    }

    override fun onStop() {
        super.onStop()
        presenter.unbindView()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        PresenterManager.savePresenter(presenter, outState)
    }

    override fun updateNewsListRefresher(hide: Boolean) {
        newsListRefresher.isRefreshing = !hide
    }

    override fun insertNewsItemsRange(offset: Int, count: Int) {
        if(noFavoriteNewsMsg.visibility == View.VISIBLE){
            showNewsList()
        }
        newsListAdapter.notifyItemRangeInserted(offset, count)
    }

    override fun resetNewsList() {
        if(noFavoriteNewsMsg.visibility == View.VISIBLE){
            showNewsList()
        }
        newsListAdapter.notifyDataSetChanged()
    }

    override fun showNewsItem(newsItem: NewsItem) {
        val showNewsItemIntent = Intent(activity, NewsItemActivity::class.java)
        showNewsItemIntent.putExtra(Constants.NEWS_ITEM_KEY, newsItem)
        startActivity(showNewsItemIntent)
    }

    override fun showLoadNewsListErrorMsg() {
        Toast.makeText(App.instance, R.string.failed_to_load_news_list, Toast.LENGTH_SHORT).show()
    }

    override fun deleteNewsItem(newsItemId: String, newsItemIndex: Int) {
        newsListAdapter.notifyItemRemoved(newsItemIndex)
    }

    override fun showEmptyNewsMsg() {
        newsList.visibility = View.INVISIBLE
        noFavoriteNewsMsg.visibility = View.VISIBLE
    }

    private fun showNewsList(){
        newsList.visibility = View.VISIBLE
        noFavoriteNewsMsg.visibility = View.INVISIBLE
    }

    override fun showLoadNextPageProgressBar() {
        newsListAdapter.addLoadingViewFooter()
    }

    override fun hideLoadNextPageProgressBar() {
        newsListAdapter.removeLoadingViewFooter()
    }

    private fun initializeFavoriteNewsListView(v: View) {
        val linearLayoutManager = LinearLayoutManager(activity)
        val divider = com.progressifff.mailnews.DividerItemDecoration(activity!!)
        newsList = v.findViewById(R.id.favoriteNewsList)
        newsList.apply {
            layoutManager = linearLayoutManager
            adapter = newsListAdapter
            addOnScrollListener(presenter.OnScrollListener(linearLayoutManager))
            addItemDecoration(divider)
        }
    }

    private fun initializeFavoriteNewsListRefresherView(v: View) {
        newsListRefresher = v.findViewById(R.id.favoriteNewsListRefresher)
        newsListRefresher.setOnRefreshListener { presenter.resetNewsList() }
    }

    private fun initPresenter(savedInstanceState: Bundle?){
        val createPresenter: ()->Unit = {
            App.favoriteNewsFragmentComponent!!.inject(this)
            presenter.updateNewsList(true)
        }
        if(savedInstanceState == null){
            createPresenter()
        }
        else{
            try{
                presenter = PresenterManager.restorePresenter(savedInstanceState)
                newsListAdapter = FavoriteNewsListAdapter(presenter)
            } catch (e: Exception){
                e.printStackTrace()
                createPresenter()
            }
        }
    }
}