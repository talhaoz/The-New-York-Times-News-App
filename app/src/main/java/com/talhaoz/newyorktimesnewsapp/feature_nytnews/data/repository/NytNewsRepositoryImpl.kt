package com.talhaoz.newyorktimesnewsapp.feature_nytnews.data.repository

import com.talhaoz.newyorktimesnewsapp.feature_nytnews.data.remote.NytNewsApi
import com.talhaoz.newyorktimesnewsapp.feature_nytnews.data.remote.dto.NewsDto
import com.talhaoz.newyorktimesnewsapp.feature_nytnews.domain.repository.NytNewsRepository
import javax.inject.Inject

class NytNewsRepositoryImpl @Inject constructor(
    private val nytNewsApi: NytNewsApi
    ) : NytNewsRepository {

    override suspend fun getNews(queryKey: String): NewsDto {
        return nytNewsApi.getNews(queryKey)
    }
}