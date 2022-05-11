package com.talhaoz.newyorktimesnewsapp.feature_nytnews.presentation.newsdetail

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.web.LoadingState
import com.google.accompanist.web.WebView
import com.google.accompanist.web.WebViewState
import com.google.accompanist.web.rememberWebViewState
import com.talhaoz.newyorktimesnewsapp.core.FullScreenProgressBar
import com.talhaoz.newyorktimesnewsapp.feature_nytnews.presentation.newslistscreen.NewsViewModel
import com.talhaoz.newyorktimesnewsapp.ui.theme.darkBackground


@SuppressLint("SetJavaScriptEnabled")
@Composable
fun NewsDetailScreen(
    pageUrl: String
) {
    Surface(Modifier.fillMaxSize(), color = darkBackground) {
        val state = rememberWebViewState(pageUrl)

        WebView(
            state,
            onCreated = { it.settings.javaScriptEnabled = false }
        )

    }
}
