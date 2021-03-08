/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ak.apps.birthdaytimer

import android.os.Bundle
import android.os.CountDownTimer
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.Surface
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ak.apps.birthdaytimer.ui.theme.MyTheme
import java.util.Calendar

@ExperimentalAnimationApi
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    MainActivityUI()
                }
            }
        }
    }
}

val day = mutableStateOf("")
val month = mutableStateOf("")
val year = mutableStateOf("")

val dateErrorVisibility = mutableStateOf(0f)
val countDownTimerVisibility = mutableStateOf(false)

val countdownHours = mutableStateOf("")
val countdownMinutes = mutableStateOf("")
val countdownSeconds = mutableStateOf("")

private lateinit var countDownTimer: CountDownTimer

@ExperimentalAnimationApi
@Composable
fun MainActivityUI() {
    Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp)) {
        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Enter upcoming friend's birthday",
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal
        )

        Spacer(modifier = Modifier.height(15.dp))

        Row {
            OutlinedTextField(
                value = day.value,
                onValueChange = {
                    day.value = it
                },
                label = { Text(text = "Day(DD)") },
                modifier = Modifier.weight(1f),
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )

            Spacer(modifier = Modifier.width(20.dp))

            OutlinedTextField(
                value = month.value,
                onValueChange = {
                    month.value = it
                },
                label = { Text(text = "Month(MM)") },
                modifier = Modifier.weight(1f),
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )

            Spacer(modifier = Modifier.width(20.dp))

            OutlinedTextField(
                value = year.value,
                onValueChange = {
                    year.value = it
                },
                label = { Text(text = "Year(YYYY)") },
                modifier = Modifier.weight(1f),
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Please enter a date in future",
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            color = Color.Red,
            modifier = Modifier.alpha(dateErrorVisibility.value)
        )

        Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = {
                setCountdownTimer()
            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(5.dp)
        ) {
            Text(
                text = "Start Birthday Timer",
                fontSize = 18.sp
            )
        }

        Spacer(modifier = Modifier.height(50.dp))

        AnimatedVisibility(
            visible = countDownTimerVisibility.value,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .wrapContentWidth()
                .wrapContentHeight(),
        ) {
            Row(
                modifier = Modifier
                    .wrapContentHeight()
                    .wrapContentWidth()
            ) {
                Column(
                    modifier = Modifier
                        .wrapContentHeight()
                        .wrapContentWidth()
                ) {
                    Text(
                        text = "Hours",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .wrapContentHeight()
                            .wrapContentWidth()
                            .align(Alignment.CenterHorizontally)
                    )

                    Spacer(modifier = Modifier.height(5.dp))

                    Text(
                        text = countdownHours.value,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .wrapContentHeight()
                            .wrapContentWidth()
                            .align(Alignment.CenterHorizontally)
                    )
                }

                Spacer(modifier = Modifier.width(20.dp))

                Column(
                    modifier = Modifier
                        .wrapContentHeight()
                        .wrapContentWidth()
                ) {
                    Text(
                        text = "Minutes",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .wrapContentHeight()
                            .wrapContentWidth()
                            .align(Alignment.CenterHorizontally)
                    )

                    Spacer(modifier = Modifier.height(5.dp))

                    Text(
                        text = countdownMinutes.value,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .wrapContentHeight()
                            .wrapContentWidth()
                            .align(Alignment.CenterHorizontally)
                    )
                }

                Spacer(modifier = Modifier.width(20.dp))

                Column(
                    modifier = Modifier
                        .wrapContentHeight()
                        .wrapContentWidth()
                ) {
                    Text(
                        text = "Seconds",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .wrapContentHeight()
                            .wrapContentWidth()
                            .align(Alignment.CenterHorizontally)
                    )

                    Spacer(modifier = Modifier.height(5.dp))

                    Text(
                        text = countdownSeconds.value,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .wrapContentHeight()
                            .wrapContentWidth()
                            .align(Alignment.CenterHorizontally)
                    )
                }
            }
        }
    }
}

fun setCountdownTimer() {
    dateErrorVisibility.value = 0f

    if (::countDownTimer.isInitialized) {
        countDownTimer.cancel()
    }

    val timerCalendar = Calendar.getInstance()

    if (year.value.isNotEmpty() && month.value.isNotEmpty() && day.value.isNotEmpty()) {
        timerCalendar.set(
            year.value.toInt(), (month.value.toInt() - 1), day.value.toInt(), 0, 0, 0
        )

        val timerCalendarInMillis = timerCalendar.timeInMillis
        val currentTimeInMillis = System.currentTimeMillis()

        if (timerCalendarInMillis > currentTimeInMillis) {
            countDownTimerVisibility.value = true
            val millisInFuture = timerCalendarInMillis - currentTimeInMillis
            countDownTimer = object : CountDownTimer(millisInFuture, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    val secondsUntilFinished = millisUntilFinished / 1000
                    val timeHoursUntilFinished = secondsUntilFinished / 3600
                    val timeMinutesUntilFinished = (secondsUntilFinished % 3600) / 60
                    val timeSecondsUntilFinished = (secondsUntilFinished % 3600) % 60
                    countdownHours.value = timeHoursUntilFinished.toString()
                    countdownMinutes.value = timeMinutesUntilFinished.toString()
                    countdownSeconds.value = timeSecondsUntilFinished.toString()
                }

                override fun onFinish() {
                }

            }
            countDownTimer.start()
        } else {
            dateErrorVisibility.value = 1f
            countDownTimerVisibility.value = false
        }
    } else {
        dateErrorVisibility.value = 1f
        countDownTimerVisibility.value = false
    }
}

@ExperimentalAnimationApi
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyTheme {
        MainActivityUI()
    }
}