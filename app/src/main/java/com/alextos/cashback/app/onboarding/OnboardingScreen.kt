package com.alextos.cashback.app.onboarding

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.alextos.cashback.R
import kotlinx.coroutines.launch

@Composable
fun OnboardingScreen(onFinish: () -> Unit) {
    val pagerState = rememberPagerState { onboardingPages.size }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f) // Растягиваем на весь экран
        ) { page ->
            OnboardingPage(page = onboardingPages[page])
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Индикаторы страниц (dots)
            OnboardingIndicators(pagerState)

            // Кнопка "Далее" или "Начать"
            Button(onClick = {
                if (pagerState.currentPage < onboardingPages.lastIndex) {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    }
                } else {
                    onFinish()
                }
            }) {
                Text(if (pagerState.currentPage == onboardingPages.lastIndex) stringResource(R.string.common_done) else stringResource(R.string.common_next))
            }
        }
    }
}