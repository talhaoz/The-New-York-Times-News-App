package com.talhaoz.newyorktimesnewsapp.feature_nytnews.domain.use_case

import com.talhaoz.newyorktimesnewsapp.feature_nytnews.data.remote.dto.NewsDto
import com.talhaoz.newyorktimesnewsapp.feature_nytnews.util.ApiResult

interface GetNewsUseCase {
    suspend operator fun invoke(queryKey: String): ApiResult<NewsDto>
}