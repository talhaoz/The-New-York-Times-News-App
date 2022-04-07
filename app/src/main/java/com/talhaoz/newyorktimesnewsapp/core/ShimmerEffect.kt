package com.talhaoz.newyorktimesnewsapp.core

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import com.talhaoz.newyorktimesnewsapp.feature_nytnews.util.Constants.INITIAL_SHIMMER
import com.talhaoz.newyorktimesnewsapp.feature_nytnews.util.Constants.SHIMMER_DURATION
import com.talhaoz.newyorktimesnewsapp.feature_nytnews.util.Constants.TARGET_SHIMMER
import com.talhaoz.newyorktimesnewsapp.ui.theme.ShimmerColorShades

@Composable
private fun ShimmerItem(
    brush: Brush,
) {
    // Column composable containing spacer shaped like a rectangle,
    // set the [background]'s [brush] with the brush receiving from [ShimmerAnimation]
    // Composable which is the Animation you are gonna create.
    Column(
        Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
    ) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .background(brush = brush)
        )
    }
}

@Composable
fun ShimmerAnimation() {

    /*
     Create InfiniteTransition
     which holds child animation like [Transition]
     animations start running as soon as they enter
     the composition and do not stop unless they are removed
    */
    val transition = rememberInfiniteTransition()
    val translateAnim by transition.animateFloat(
        /*
         Specify animation positions,
         initial Values 0F means it
         starts from 0 position
        */
        initialValue = INITIAL_SHIMMER,
        targetValue = TARGET_SHIMMER,
        animationSpec = infiniteRepeatable(


            // Tween Animates between values over specified [durationMillis]
            tween(durationMillis = SHIMMER_DURATION, easing = FastOutSlowInEasing),
            RepeatMode.Reverse
        )
    )

    /*
      Create a gradient using the list of colors
      Use Linear Gradient for animating in any direction according to requirement
      start=specifies the position to start with in cartesian like system Offset(10f,10f) means x(10,0) , y(0,10)
      end = Animate the end position to give the shimmer effect using the transition created above
    */
    val brush = Brush.linearGradient(
        colors = ShimmerColorShades,
        start = Offset(10f, 10f),
        end = Offset(translateAnim, translateAnim)
    )

    ShimmerItem(brush = brush)
}