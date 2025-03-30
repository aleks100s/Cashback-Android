package com.alextos.cashback.features.payments.di

import com.alextos.cashback.features.payments.scenes.payment_detail.PaymentDetailViewModel
import com.alextos.cashback.features.payments.scenes.payments.PaymentsViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val paymentsModule = module {
    viewModelOf(::PaymentsViewModel)
    viewModelOf(::PaymentDetailViewModel)
}