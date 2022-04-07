package com.talhaoz.newyorktimesnewsapp.feature_nytnews.data.remote.dto

data class NewsDto(
    val results: List<SingleNewsDto>,
    val status: String
)