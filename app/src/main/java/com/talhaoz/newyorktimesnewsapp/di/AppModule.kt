package com.talhaoz.newyorktimesnewsapp.di

import com.talhaoz.newyorktimesnewsapp.feature_nytnews.data.remote.NytNewsApi
import com.talhaoz.newyorktimesnewsapp.feature_nytnews.data.repository.NytNewsRepositoryImpl
import com.talhaoz.newyorktimesnewsapp.feature_nytnews.domain.repository.NytNewsRepository
import com.talhaoz.newyorktimesnewsapp.feature_nytnews.domain.use_case.GetNews
import com.talhaoz.newyorktimesnewsapp.feature_nytnews.domain.use_case.NewsUseCases
import com.talhaoz.newyorktimesnewsapp.feature_nytnews.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideNytNewsRepository(
        nytNewsApi: NytNewsApi
    ) : NytNewsRepository = NytNewsRepositoryImpl(nytNewsApi)

    @Singleton
    @Provides
    fun provideNytNewsDao() : NytNewsApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(getHttpClient())
            .build()
            .create(NytNewsApi::class.java)
    }

    private fun getHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)

        return httpClient.build()
    }

    @Singleton
    @Provides
    fun provideNewsUseCases(repository: NytNewsRepository): NewsUseCases{
        return NewsUseCases(
            getNews = GetNews(repository)
        )
    }
}