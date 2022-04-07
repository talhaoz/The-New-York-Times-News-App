package com.talhaoz.newyorktimesnewsapp.feature_nytnews.presentation.newslistscreen

sealed class PageState{
    object AllNewsScreen : PageState()
    object BusinessNewsScreen : PageState()
    object ScienceNewsScreen : PageState()
    object SportsNewsScreen : PageState()
    object FashionNewsScreen : PageState()
    object IdleScreen : PageState()
}
