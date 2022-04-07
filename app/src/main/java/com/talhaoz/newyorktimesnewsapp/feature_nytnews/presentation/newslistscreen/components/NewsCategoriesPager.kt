package com.talhaoz.newyorktimesnewsapp.feature_nytnews.presentation.newslistscreen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.talhaoz.newyorktimesnewsapp.R
import com.talhaoz.newyorktimesnewsapp.core.*
import com.talhaoz.newyorktimesnewsapp.feature_nytnews.data.DataStoreManager
import com.talhaoz.newyorktimesnewsapp.feature_nytnews.data.remote.dto.NewsDto
import com.talhaoz.newyorktimesnewsapp.feature_nytnews.data.remote.dto.SingleNewsDto
import com.talhaoz.newyorktimesnewsapp.feature_nytnews.presentation.newslistscreen.NewsListState
import com.talhaoz.newyorktimesnewsapp.feature_nytnews.presentation.newslistscreen.NewsViewModel
import com.talhaoz.newyorktimesnewsapp.feature_nytnews.presentation.newslistscreen.PageState
import com.talhaoz.newyorktimesnewsapp.feature_nytnews.util.getPassedTime
import com.talhaoz.newyorktimesnewsapp.feature_nytnews.util.getScreenName
import com.talhaoz.newyorktimesnewsapp.ui.theme.darkBackground
import com.talhaoz.newyorktimesnewsapp.ui.theme.inactivegrey
import com.talhaoz.newyorktimesnewsapp.ui.theme.lightGrayBackground
import com.talhaoz.newyorktimesnewsapp.ui.theme.white
import kotlinx.coroutines.launch

@ExperimentalPagerApi
@Composable
fun NewsCategoriesPager(
    onClickEvent: (String) -> Unit,
    viewModel: NewsViewModel = hiltViewModel()) {

    val dataStoreManager = DataStoreManager(LocalContext.current)

    val coroutineScope = rememberCoroutineScope()
    val newsCategoryList = viewModel.newsCategoryList
    val state = viewModel.state.value
    val pagerState = rememberPagerState()
    val introductionDialogState = remember { mutableStateOf(false) }

    val scrollingPage by remember {
        derivedStateOf {
            if (pagerState.isScrollInProgress) {
                null
            } else
                pagerState.currentPage
        }
    }

    LaunchedEffect(Unit){
        dataStoreManager.readFromPreferences.collect { isFirstLaunch ->
            if(isFirstLaunch){
                introductionDialogState.value = true
            }
        }
    }

    IntroductionDialog(
        dialogState = introductionDialogState
    ) {
        coroutineScope.launch {
            dataStoreManager.writeToPreferences(false)
        }
    }

    LaunchedEffect(scrollingPage) {
        val scrolledPageNum = scrollingPage ?: return@LaunchedEffect
        snapshotFlow { scrolledPageNum }
            .collect { currentPageNum ->
                viewModel.loadNews(newsCategoryList[currentPageNum])
            }
    }

    HorizontalPager(
        state = pagerState,
        count = newsCategoryList.size,
        modifier = Modifier.fillMaxWidth()
    ) { pageNum ->

        Surface(color = darkBackground, modifier = Modifier.fillMaxSize()) {
            when (state) {
                is NewsListState.NewsListScreenLoaded -> {
                    NewsListWidget(newsDto = state.newsDto, state.pageState, onClickEvent)
                }
                is NewsListState.NewsListScreenLoading -> FullScreenProgressBar()
                is NewsListState.NewsListScreenNetworkError -> ErrorComponent()
            }
        }

        Box(
            contentAlignment = Alignment.BottomCenter,
            modifier = Modifier.fillMaxSize().padding(bottom = 15.dp)
        ) {
            HorizontalPagerIndicator(
                pagerState = pagerState,
                modifier = Modifier
                    .padding(16.dp),
                activeColor = white,
                inactiveColor = inactivegrey
            )
        }
    }
}

@Composable
fun NewsListWidget(
    newsDto: NewsDto,
    currentPageState: PageState,
    onClickEvent: (String) -> Unit,
    viewModel: NewsViewModel = hiltViewModel()
) {
    val toastDialogState = remember { mutableStateOf(false) }
    val listState: LazyListState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    LazyColumn(modifier = Modifier.padding(0.dp, 8.dp, 0.dp, 0.dp), state = listState) {
        items(newsDto.results.size) { itemIndex ->
            EventCard(
                singleNewsDto = newsDto.results[itemIndex],
                onClickEvent = onClickEvent
            )
        }
    }
    /*if(viewModel.prevPage.value != currentPageState){
        viewModel.prevPage.value = currentPageState
        toastDialogState.value = true
    }*/
    LaunchedEffect(currentPageState){
        toastDialogState.value = true
    }

    FullScreenMessage(message = currentPageState.getScreenName(), toastDialogState)

    SideEffect {
        coroutineScope.launch {
            listState.scrollToItem(0)
        }
    }

}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun EventCard(singleNewsDto: SingleNewsDto, onClickEvent: (String) -> Unit) {
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(9.dp, 5.dp, 9.dp, 12.dp)
            .clickable { onClickEvent.invoke(singleNewsDto.url) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(lightGrayBackground)
        ) {
            Card (
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            ) {
                var painter = painterResource(id = R.drawable.ic_new_york_times)
                if(singleNewsDto.multimedia != null){
                    val imageIndex = singleNewsDto.multimedia.size.minus(2)

                    if(imageIndex >= 0) {
                        painter = rememberImagePainter(data = singleNewsDto.multimedia[imageIndex].url)
                        if (painter.state !is ImagePainter.State.Success) {
                            ShimmerAnimation()
                        }
                    }
                }

                Image(
                    painter = painter,
                    contentDescription = "",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(0.8f)
                )
            }

            Text(
                singleNewsDto.published_date.getPassedTime(),
                fontSize = 11.sp,
                modifier = Modifier.padding(12.dp, 8.dp, 0.dp, 0.dp),
                color = white,
                fontWeight = FontWeight.Thin
            )

            Text(
                singleNewsDto.title,
                fontSize = 15.sp,
                modifier = Modifier.padding(12.dp, 5.dp, 12.dp, 8.dp),
                color = white,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

