package com.talhaoz.newyorktimesnewsapp.feature_nytnews.data.remote

import com.talhaoz.newyorktimesnewsapp.feature_nytnews.data.remote.dto.NewsDto
import com.talhaoz.newyorktimesnewsapp.feature_nytnews.util.Constants.ALL_NEWS
import com.talhaoz.newyorktimesnewsapp.feature_nytnews.util.Constants.API_KEY
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NytNewsApi {

    @GET("content/{queryKey}.json?api-key=$API_KEY")
    suspend fun getNews(
        @Path("queryKey", encoded = true) queryKey: String,
        @Query("api_key") apiKey: String = API_KEY
    ): NewsDto


}