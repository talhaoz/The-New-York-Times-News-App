package com.talhaoz.newyorktimesnewsapp.feature_nytnews.domain.use_case

import com.talhaoz.newyorktimesnewsapp.feature_nytnews.data.remote.dto.NewsDto
import com.talhaoz.newyorktimesnewsapp.feature_nytnews.domain.repository.NytNewsRepository
import com.talhaoz.newyorktimesnewsapp.feature_nytnews.util.ApiResult
import javax.inject.Inject

class GetNews @Inject constructor(
    private val repository: NytNewsRepository
){
    suspend  operator fun invoke(queryKey: String): ApiResult<NewsDto> {
        val response = try {
            repository.getNews(queryKey)
        } catch (e: Exception) {
            return ApiResult.Error("An unknown error occurred. $e")
        }
        return ApiResult.Success(response);
    }
}