package com.talhaoz.newyorktimesnewsapp.feature_nytnews.presentation.newslistscreen

import com.talhaoz.newyorktimesnewsapp.feature_nytnews.data.remote.dto.NewsDto

sealed class NewsListState
{
    object NewsListScreenLoading : NewsListState()
    class NewsListScreenLoaded(
        val newsDto: NewsDto,
        val pageState: PageState,
    ) : NewsListState()
    class NewsListScreenNetworkError(val loadError: String) : NewsListState()
}


