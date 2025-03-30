package com.alextos.cashback.features.payments.scenes.payment_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alextos.cashback.R
import com.alextos.cashback.common.UiText
import com.alextos.cashback.core.domain.models.Payment
import com.alextos.cashback.core.domain.repository.CardRepository
import com.alextos.cashback.core.domain.repository.PaymentRepository
import com.alextos.cashback.core.domain.services.AnalyticsEvent
import com.alextos.cashback.core.domain.services.AnalyticsService
import com.alextos.cashback.core.domain.services.ToastService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PaymentDetailViewModel(
    private val paymentRepository: PaymentRepository,
    private val cardRepository: CardRepository,
    private val analyticsService: AnalyticsService,
    private val toastService: ToastService
): ViewModel() {
    private val _state = MutableStateFlow(PaymentDetailState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            cardRepository.getAllCardsFlow()
                .collect { cards ->
                    _state.update { it.copy(availableCards = cards) }
                }
        }
    }

    fun onAction(action: PaymentDetailAction) {
        when(action) {
            is PaymentDetailAction.CardSelected -> {
                analyticsService.logEvent(AnalyticsEvent.AddPaymentSelectCardButtonTapped)
                _state.update { it.copy(selectedCard = action.card) }
            }

            is PaymentDetailAction.AmountChanged -> {
                _state.update { it.copy(amount = action.text) }
            }

            is PaymentDetailAction.DateChanged -> {
                analyticsService.logEvent(AnalyticsEvent.AddPaymentSelectDateButtonTapped)
                _state.update { it.copy(date = action.date) }
            }

            is PaymentDetailAction.SaveButtonTapped -> {
                analyticsService.logEvent(AnalyticsEvent.AddPaymentSaveButtonTapped)
                val payment = Payment(
                    amount = state.value.amount.toIntOrNull() ?: 0,
                    date = state.value.date,
                    card = state.value.selectedCard
                )
                viewModelScope.launch(Dispatchers.IO) {
                    paymentRepository.save(payment)
                }
                toastService.showToast(UiText.StringResourceId(R.string.add_payment_save_success))
            }
        }
    }
}