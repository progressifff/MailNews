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
import com.progressifff.mailnews.presentation.presenters.NewsListPresenter
import com.progressifff.mailnews.presentation.views.NewsListView
import java.lang.Exception
import javax.inject.Inject

class NewsListFragment : Fragment(), NewsListView {

    @Inject
    lateinit var presenter: NewsListPresenter
    @Inject
    lateinit var newsListAdapter: NewsListAdapter

    private lateinit var newsListRefresher: SwipeRefreshLayout
    private lateinit var newsList: RecyclerView
    private lateinit var emptyNewsMsg: LinearLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        App.instance.plusNewsListFragmentComponent()
        val v = inflater.inflate(R.layout.news_fragment, container, false)
        initPresenter(savedInstanceState)
        initializeNewsListView(v)
        initializeNewsListRefresherView(v)
        emptyNewsMsg = v.findViewById(R.id.emptyNewsMsg)
        return v
    }

    override fun onDestroy() {
        super.onDestroy()
        App.instance.clearNewsListFragmentComponent()
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
        if(emptyNewsMsg.visibility == View.VISIBLE){
            showNewsList()
        }
        newsListAdapter.notifyItemRangeInserted(offset, count)
    }

    override fun resetNewsList() {
        if(emptyNewsMsg.visibility == View.VISIBLE){
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
        Toast.makeText(activity, R.string.failed_to_load_news_list, Toast.LENGTH_SHORT).show()
    }

    override fun showEmptyNewsMsg() {
        emptyNewsMsg.visibility = View.INVISIBLE
        emptyNewsMsg.visibility = View.VISIBLE
    }

    private fun showNewsList(){
        emptyNewsMsg.visibility = View.VISIBLE
        emptyNewsMsg.visibility = View.INVISIBLE
    }

    private fun initializeNewsListView(v: View) {
        val linearLayoutManager = LinearLayoutManager(activity)
        val divider = com.progressifff.mailnews.DividerItemDecoration(activity!!)
        newsList = v.findViewById(R.id.newsList)
        newsList.apply {
            layoutManager = linearLayoutManager
            adapter = newsListAdapter
            addOnScrollListener(presenter.OnScrollListener(linearLayoutManager))
            addItemDecoration(divider)
        }
    }

    private fun initializeNewsListRefresherView(v: View) {
        newsListRefresher = v.findViewById(R.id.newsListRefresher)
        newsListRefresher.setOnRefreshListener { presenter.resetNewsList() }
    }

    private fun initPresenter(savedInstanceState: Bundle?){
        val createPresenter: ()->Unit = {
            App.newsListFragmentComponent!!.inject(this)
            presenter.updateNewsList(true)
        }
        if(savedInstanceState == null){
            createPresenter()
        }
        else{
            try{
                presenter = PresenterManager.restorePresenter(savedInstanceState)
                newsListAdapter = NewsListAdapter(presenter)
            } catch (e: Exception){
                e.printStackTrace()
                createPresenter()
            }
        }
    }

    override fun showLoadNextPageProgressBar() {
        newsListAdapter.addLoadingViewFooter()
    }

    override fun hideLoadNextPageProgressBar() {
        newsListAdapter.removeLoadingViewFooter()
    }
}