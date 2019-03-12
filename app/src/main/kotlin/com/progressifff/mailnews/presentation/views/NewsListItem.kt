package com.progressifff.mailnews.presentation.views

import java.util.*

interface NewsListItem {
    fun setCategory(category: String)
    fun setTitle(title: String)
    fun setDescription(description: String)
    fun setPubDate(date: Date)
}