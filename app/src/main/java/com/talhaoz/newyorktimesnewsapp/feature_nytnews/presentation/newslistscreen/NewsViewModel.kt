package com.talhaoz.newyorktimesnewsapp.feature_nytnews.presentation.newslistscreen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.talhaoz.newyorktimesnewsapp.feature_nytnews.data.remote.dto.NewsDto
import com.talhaoz.newyorktimesnewsapp.feature_nytnews.domain.use_case.GetNewsUseCase
import com.talhaoz.newyorktimesnewsapp.feature_nytnews.util.ApiResult
import com.talhaoz.newyorktimesnewsapp.feature_nytnews.util.Constants.ALL_NEWS
import com.talhaoz.newyorktimesnewsapp.feature_nytnews.util.Constants.BUSINESS_NEWS
import com.talhaoz.newyorktimesnewsapp.feature_nytnews.util.Constants.FASHION_NEWS
import com.talhaoz.newyorktimesnewsapp.feature_nytnews.util.Constants.SCIENCE_NEWS
import com.talhaoz.newyorktimesnewsapp.feature_nytnews.util.Constants.SPORTS_NEWS
import com.talhaoz.newyorktimesnewsapp.feature_nytnews.util.getPageState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val getNewsUseCase: GetNewsUseCase,
) : ViewModel() {

    private val _state = mutableStateOf<NewsListState>(NewsListState.NewsListScreenLoading)
    var state: State<NewsListState> = _state

    var prevPage = mutableStateOf<PageState>(PageState.IdleScreen)

    private val newsCache = mutableMapOf<String, NewsDto>()

    val newsCategoryList = listOf(
        ALL_NEWS,
        BUSINESS_NEWS,
        SPORTS_NEWS,
        SCIENCE_NEWS,
        FASHION_NEWS
        )
    
    init {
        loadNews()
    }

    fun loadNews(queryKey: String = ALL_NEWS) {
        val currPageStr =  queryKey.getPageState()
        if(prevPage.value != currPageStr) {
            prevPage.value = currPageStr
            _state.value = NewsListState.NewsListScreenLoading
            if(newsCache.containsKey(queryKey).not()) {
                viewModelScope.launch {
                    when (val result = getNewsUseCase.invoke(queryKey)) {
                        is ApiResult.Success -> {
                            newsCache[queryKey] = result.data!!
                            _state.value =
                                NewsListState.NewsListScreenLoaded(newsDto = result.data, queryKey.getPageState())
                        }
                        is ApiResult.Error -> {
                            if (result.message != null) {
                                _state.value = NewsListState.NewsListScreenNetworkError(result.message)
                                Timber.e(Throwable(result.message))
                            } else {
                                _state.value =
                                    NewsListState.NewsListScreenNetworkError("Unknown Error has occurred.")
                                Timber.e(Throwable("Unknown Error has occurred."))
                            }
                        }
                    }
                }
            } else {
                newsCache[queryKey]?.let {
                    _state.value =
                        NewsListState.NewsListScreenLoaded(newsDto = it, queryKey.getPageState())
                }
            }
        }

    }
}
