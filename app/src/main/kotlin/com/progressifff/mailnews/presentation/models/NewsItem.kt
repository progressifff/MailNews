package com.progressifff.mailnews.presentation.models

import android.os.Parcel
import android.os.Parcelable
import com.progressifff.mailnews.data.NewsItemDTO
import java.util.*

class NewsItem(
    var guid: String = "",
    var categories: List<String> = arrayListOf(),
    var title: String = "",
    var description: String = "",
    var pubDate: Date = Date()
) : Parcelable{

    constructor(parcel: Parcel) : this() {
        guid = parcel.readString()!!
        parcel.readList(categories, String::class.java.classLoader)
        title = parcel.readString()!!
        description = parcel.readString()!!
        pubDate = parcel.readSerializable()!! as Date
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(guid)
        parcel.writeList(categories)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeSerializable(pubDate)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<NewsItem> {
        override fun createFromParcel(parcel: Parcel): NewsItem {
            return NewsItem(parcel)
        }

        override fun newArray(size: Int): Array<NewsItem?> {
            return arrayOfNulls(size)
        }

        fun fromNewsDTO(newsItemDTO: NewsItemDTO) = NewsItem(
                newsItemDTO.guid,
                newsItemDTO.categories,
                newsItemDTO.title,
                newsItemDTO.description.replace("<[^>]*>".toRegex(), ""),
                newsItemDTO.pubDate
            )
    }
}