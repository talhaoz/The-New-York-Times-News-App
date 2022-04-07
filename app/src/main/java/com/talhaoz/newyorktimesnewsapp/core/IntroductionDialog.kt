package com.talhaoz.newyorktimesnewsapp.core


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.talhaoz.newyorktimesnewsapp.R
import com.talhaoz.newyorktimesnewsapp.ui.theme.darkBackground
import com.talhaoz.newyorktimesnewsapp.ui.theme.lightGrayBackground
import com.talhaoz.newyorktimesnewsapp.ui.theme.white

@Composable
fun IntroductionDialog(
    dialogState: MutableState<Boolean>,
    positiveButtonEvent: () -> Unit
) {
    if (dialogState.value) {
        Dialog(
            onDismissRequest = { dialogState.value = false },
            properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
        ) {
            Card(
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize()
                    .padding(20.dp,0.dp)
            ) {
                Box(
                    modifier = Modifier
                        .wrapContentSize()
                        .background(darkBackground)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .padding(10.dp, 10.dp)
                            .wrapContentSize()
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_swipe),
                            contentDescription = "",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .padding(10.dp, 7.dp)
                                .size(25.dp)
                        )

                        Text(
                            text = stringResource(id = R.string.introduction_text),
                            color = white,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(10.dp,5.dp)
                        )

                        Button(
                            modifier = Modifier
                                .wrapContentWidth()
                                .padding(0.dp, 8.dp, 0.dp, 5.dp),
                            onClick = {
                                dialogState.value = false
                                positiveButtonEvent()
                            },
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = lightGrayBackground,
                                contentColor = white
                            )
                        ) {
                            Text(stringResource(id = R.string.ok))
                        }
                    }

                }
            }
        }
    }
}