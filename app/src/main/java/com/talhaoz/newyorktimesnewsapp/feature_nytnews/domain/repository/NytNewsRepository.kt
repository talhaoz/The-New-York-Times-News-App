package com.talhaoz.newyorktimesnewsapp.feature_nytnews.domain.repository

import com.talhaoz.newyorktimesnewsapp.feature_nytnews.data.remote.dto.NewsDto


interface NytNewsRepository {

    suspend fun getNews(queryKey: String): NewsDto

}