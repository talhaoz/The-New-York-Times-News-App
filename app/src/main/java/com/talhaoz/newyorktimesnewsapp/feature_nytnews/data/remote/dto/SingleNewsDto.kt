package com.talhaoz.newyorktimesnewsapp.feature_nytnews.data.remote.dto

data class SingleNewsDto(
    val abstract: String,
    val byline: String,
    val multimedia: List<PhotoDto>?,
    val published_date: String,
    val section: String,
    val subsection: String,
    val thumbnail_standard: String,
    val title: String,
    val updated_date: String,
    val url: String
)