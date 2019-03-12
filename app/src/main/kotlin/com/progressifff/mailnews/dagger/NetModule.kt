package com.progressifff.mailnews.dagger

import com.google.gson.GsonBuilder
import com.progressifff.mailnews.Constants
import com.progressifff.mailnews.data.NewsNetApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class NetModule {

    @Provides
    fun providesNewsApi(retrofit: Retrofit) = retrofit.create(NewsNetApi::class.java)

    @Provides
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val gson = GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create()
        return Retrofit.Builder()
            .baseUrl(Constants.NEWS_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
    }

    @Provides
    fun providesOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }
}