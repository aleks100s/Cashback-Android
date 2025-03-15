package com.alextos.cashback.app.onboarding

import com.alextos.cashback.R

data class OnboardingData(
    val image: Int
)

val onboardingPages = listOf(
    OnboardingData(R.drawable.onboarding_01),
    OnboardingData(R.drawable.onboarding_02),
    OnboardingData(R.drawable.onboarding_03),
    OnboardingData(R.drawable.onboarding_04),
    OnboardingData(R.drawable.onboarding_05),
)
