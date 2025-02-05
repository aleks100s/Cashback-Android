package com.alextos.cashback.app

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import com.alextos.cashback.app.navigation.ApplicationViewModel

val appModule = module {
    viewModelOf(::ApplicationViewModel)
}