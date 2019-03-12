package com.progressifff.mailnews.presentation

import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.webkit.WebView
import android.webkit.WebViewClient
import com.progressifff.mailnews.*
import com.progressifff.mailnews.presentation.models.NewsItem
import com.progressifff.mailnews.presentation.presenters.NewsItemPresenter
import com.progressifff.mailnews.presentation.views.NewsItemView
import kotlinx.android.synthetic.main.activity_news_item.*
import java.lang.Exception
import javax.inject.Inject

class NewsItemActivity : AppCompatActivity(), NewsItemView {

    @Inject
    lateinit var presenter: NewsItemPresenter

    private lateinit var newsItemRefresher: SwipeRefreshLayout
    private lateinit var newsItemWebView: WebView

    val loadNewsPageClient = object : WebViewClient(){
        override fun onPageFinished(view: WebView, url: String){
            presenter.onNewsPageLoaded()
        }

        override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?){
            presenter.onNewsPageLoading()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.instance.plusNewsActivityComponent()
        setContentView(R.layout.activity_news_item)
        newsItemWebView = findViewById(R.id.webview)
        newsItemWebView.webViewClient  = loadNewsPageClient
        initPresenter(savedInstanceState)
        initializeToolbar()
        initializeNewsItemRefresherView()
    }

    override fun onDestroy() {
        super.onDestroy()
        App.instance.clearNewsActivityComponent()
    }

    override fun onStart(){
        super.onStart()
        presenter.bindView(this)
    }

    override fun onStop() {
        super.onStop()
        presenter.unbindView()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.news_menu, menu)
        val favoriteMenuItem = menu!!.findItem(R.id.add_to_favorite)
        favoriteMenuItem.isChecked = presenter.isNewsItemFavorite
        favoriteMenuItem.icon = if(favoriteMenuItem.isChecked) getDrawable(R.drawable.ic_star_gold) else getDrawable(R.drawable.ic_star)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId){
            R.id.add_to_favorite -> {
                item.isChecked = !item.isChecked
                item.icon = if(item.isChecked) getDrawable(R.drawable.ic_star_gold) else getDrawable(R.drawable.ic_star)
                presenter.onFavoriteMenuItemSelected(item.isChecked)
            }
            android.R.id.home -> onBackPressed()
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        webview.saveState(outState)
        PresenterManager.savePresenter(presenter, outState!!)
    }

    override fun updateNewsPageRefresher(hide: Boolean) {
        if(newsItemRefresher.isRefreshing == hide){
            newsItemRefresher.isRefreshing = !hide
        }
    }

    override fun invalidateFavoriteMenuItem() {
        invalidateOptionsMenu()
    }

    private fun initPresenter(savedInstanceState: Bundle?){
        val createPresenter: ()->Unit = {
            App.newsActivityComponent!!.inject(this)
            presenter.newsItem = intent.getParcelableExtra(Constants.NEWS_ITEM_KEY)
        }

        if(savedInstanceState == null){
            createPresenter()
        }
        else{
            try{
                presenter = PresenterManager.restorePresenter(savedInstanceState)
                webview.restoreState(savedInstanceState)
            } catch (e: Exception){
                e.printStackTrace()
                createPresenter()
            }
        }
    }

    private fun initializeToolbar(){
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.setTitle(R.string.news_details)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeButtonEnabled(true)
    }

    private fun initializeNewsItemRefresherView() {
        newsItemRefresher = findViewById(R.id.newsItemRefresher)
        newsItemRefresher.setOnRefreshListener { presenter.onRefreshNewsPage() }
    }

    override fun loadNewsPage(newsItem: NewsItem) {
        newsItemWebView.loadUrl(newsItem.guid)
    }
}