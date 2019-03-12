package com.progressifff.mailnews.data

import android.arch.persistence.room.*
import com.google.gson.annotations.SerializedName
import io.reactivex.Single
import retrofit2.http.*
import retrofit2.http.Query
import java.util.*

interface NewsNetApi {
    @GET("v1/api.json?rss_url=https://news.mail.ru/rss&api_key=qlcqcm5sk5gn8uk5wvzzytbzkgwqgsr9ielbsjs0&order_by=pubDate&order_dir=desc")
    fun getNewsList(@Query("count") count: Int) : Single<NewsDTO>
}

@Dao
interface NewsDao {
    @android.arch.persistence.room.Query("SELECT guid, categories, title, description, pubDate FROM news LIMIT :count OFFSET :offset")
    fun getNewsList(offset: Int, count: Int): Single<List<NewsItemDTO>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addNewsItem(newsItem: NewsItemDTO): Long

    @android.arch.persistence.room.Query("DELETE FROM news WHERE `guid` LIKE :guid")
    fun delete(guid: String): Int

    @android.arch.persistence.room.Query("SELECT `index` FROM news WHERE `guid` LIKE :guid")
    fun getNewsItemIndex(guid: String): Long

    @android.arch.persistence.room.Query("SELECT COUNT(*) from news")
    fun newsCount(): Single<Int>

    @android.arch.persistence.room.Query("SELECT (count(*) > 0) from news WHERE `guid` LIKE :guid")
    fun exists(guid: String): Single<Boolean>
}

data class NewsDTO(@SerializedName("items") var items: List<NewsItemDTO>)

@Entity(tableName = "news")
data class NewsItemDTO(
    @ColumnInfo(name = "guid")          @SerializedName("guid")           var guid: String,
    @ColumnInfo(name = "categories")    @SerializedName("categories")     var categories: List<String>,
    @ColumnInfo(name = "title")         @SerializedName("title")          var title: String,
    @ColumnInfo(name = "description")   @SerializedName("description")    var description: String,
    @ColumnInfo(name = "pubDate")       @SerializedName("pubDate")        var pubDate: Date
) {

    @PrimaryKey(autoGenerate = true)                                            var index: Int? = null
}
