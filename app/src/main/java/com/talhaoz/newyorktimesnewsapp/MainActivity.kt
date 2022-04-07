package com.talhaoz.newyorktimesnewsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.pager.ExperimentalPagerApi
import com.talhaoz.newyorktimesnewsapp.feature_nytnews.presentation.newsdetail.NewsDetailScreen
import com.talhaoz.newyorktimesnewsapp.feature_nytnews.presentation.newslistscreen.NewsListScreen
import com.talhaoz.newyorktimesnewsapp.ui.theme.NewYorkTimesNewsAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalPagerApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            NewYorkTimesNewsAppTheme {

                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "news_list_screen") {

                    composable("news_list_screen") {
                        NewsListScreen(navController = navController)
                    }

                    composable("single_news_detail_screen/{pageUrl}",
                        arguments = listOf(
                            navArgument("pageUrl") { type = NavType.StringType })) {

                            val pageUrl = remember {
                                it.arguments?.getString("pageUrl")
                            }

                        if (pageUrl != null) {
                            NewsDetailScreen(
                                pageUrl
                            )
                        }
                    }

                }
            }
        }
    }
}