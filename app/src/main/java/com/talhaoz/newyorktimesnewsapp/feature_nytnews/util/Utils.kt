package com.talhaoz.newyorktimesnewsapp.feature_nytnews.util

import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.talhaoz.newyorktimesnewsapp.feature_nytnews.presentation.newslistscreen.PageState
import java.text.SimpleDateFormat
import java.time.*
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
fun String.getPassedTime(): String {
    return getDifference(this)
}

@RequiresApi(Build.VERSION_CODES.O)
fun getDifference(localDateTime: String): String {
    val zone: ZoneId = ZoneId.of("America/New_York")
    val newsDate = ZonedDateTime.parse(localDateTime, DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(zone))

    val currentTimeAtZone = ZonedDateTime.now(zone)

    val minutes = ChronoUnit.MINUTES.between(newsDate,currentTimeAtZone)
    var timeDiffStr = "$minutes mins ago"
    if(minutes > 60) {
        val hours = ChronoUnit.HOURS.between(newsDate,currentTimeAtZone)
        val estimatedMins = minutes % 60
        timeDiffStr = "$hours hr and $estimatedMins mins ago"
    }

    return timeDiffStr
}

fun String.getPageState() : PageState {
    return when(this){
        Constants.ALL_NEWS -> PageState.AllNewsScreen
        Constants.BUSINESS_NEWS -> PageState.BusinessNewsScreen
        Constants.SCIENCE_NEWS -> PageState.ScienceNewsScreen
        Constants.SPORTS_NEWS -> PageState.SportsNewsScreen
        else -> {
            PageState.FashionNewsScreen
        }
    }
}

fun PageState.getScreenName() : String {
    return when(this){
        PageState.AllNewsScreen -> Constants.RECENT_NEWS_SCREEN
        PageState.BusinessNewsScreen -> Constants.BUSINESS_NEWS_SCREEN
        PageState.ScienceNewsScreen -> Constants.SCIENCE_NEWS_SCREEN
        PageState.SportsNewsScreen -> Constants.SPORTS_NEWS_SCREEN
        else -> {
            Constants.FASHION_NEWS_SCREEN
        }
    }
}

/*Constants.ALL_NEWS -> Constants.RECENT_NEWS_SCREEN
Constants.BUSINESS_NEWS ->
Constants.SCIENCE_NEWS ->
Constants.SPORTS_NEWS ->
else -> {

}*/

