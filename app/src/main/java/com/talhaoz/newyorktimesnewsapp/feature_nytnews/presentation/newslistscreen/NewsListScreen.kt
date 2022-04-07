package com.talhaoz.newyorktimesnewsapp.feature_nytnews.presentation.newslistscreen


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.talhaoz.newyorktimesnewsapp.R
import com.talhaoz.newyorktimesnewsapp.feature_nytnews.presentation.newslistscreen.components.NewsCategoriesPager
import com.talhaoz.newyorktimesnewsapp.ui.theme.darkBackground
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


@ExperimentalPagerApi
@Composable
fun NewsListScreen(
    navController: NavController
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(darkBackground)
            .padding(0.dp, 16.dp, 0.dp, 16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = R.drawable.ic_new_york_times),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(0.dp,5.dp)
                    .width(250.dp)
                    .height(30.dp)
                    .clip(CircleShape)
            )
            NewsCategoriesPager(onClickEvent = { pageUrl ->
                val encodedUrl = URLEncoder.encode(pageUrl, StandardCharsets.UTF_8.toString())
                navController.navigate("single_news_detail_screen/$encodedUrl")
            })
        }
    }
}





