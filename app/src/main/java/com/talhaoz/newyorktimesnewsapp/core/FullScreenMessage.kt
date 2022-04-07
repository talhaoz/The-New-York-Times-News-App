package com.talhaoz.newyorktimesnewsapp.core

import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.talhaoz.newyorktimesnewsapp.ui.theme.dimBackground
import com.talhaoz.newyorktimesnewsapp.ui.theme.white

@Composable
fun FullScreenMessage(
    message: String,
    messageState: MutableState<Boolean>
) {
    if (messageState.value) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .background(dimBackground)
            ) {

                Text(
                    text = message,
                    color = white,
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.SansSerif
                )

                Handler(Looper.getMainLooper()).postDelayed(
                    {
                        messageState.value = false
                    }, 1000
                )

            }
    }
}