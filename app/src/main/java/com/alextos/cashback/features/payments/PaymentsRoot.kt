package com.alextos.cashback.features.payments

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.alextos.cashback.features.payments.scenes.payment_detail.PaymentDetailScreen
import com.alextos.cashback.features.payments.scenes.payments.PaymentsScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun PaymentsRoot(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = PaymentsRoute.PaymentsGraph
    ) {
        navigation<PaymentsRoute.PaymentsGraph>(
            startDestination = PaymentsRoute.Payments
        ) {
            composable<PaymentsRoute.Payments> {
                PaymentsScreen(
                    viewModel = koinViewModel(),
                    onCreatePayment = {
                        navController.navigate(PaymentsRoute.CreatePayment)
                    }
                )
            }

            composable<PaymentsRoute.CreatePayment> {
                PaymentDetailScreen(
                    viewModel = koinViewModel(),
                    goBack = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}