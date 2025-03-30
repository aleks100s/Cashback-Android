package com.alextos.cashback.features.payments.scenes.payment_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.alextos.cashback.R
import com.alextos.cashback.common.UiText
import com.alextos.cashback.core.domain.models.Payment
import com.alextos.cashback.core.domain.repository.CardRepository
import com.alextos.cashback.core.domain.repository.PaymentRepository
import com.alextos.cashback.core.domain.services.AnalyticsEvent
import com.alextos.cashback.core.domain.services.AnalyticsService
import com.alextos.cashback.core.domain.services.ToastService
import com.alextos.cashback.features.payments.PaymentsRoute
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID

class PaymentDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val paymentRepository: PaymentRepository,
    private val cardRepository: CardRepository,
    private val analyticsService: AnalyticsService,
    private val toastService: ToastService
): ViewModel() {
    private val paymentId = savedStateHandle.toRoute<PaymentsRoute.PaymentDetail>().paymentId

    private val _state = MutableStateFlow(PaymentDetailState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            cardRepository.getAllCardsFlow()
                .collect { cards ->
                    _state.update { it.copy(availableCards = cards) }
                }
        }

        viewModelScope.launch(Dispatchers.IO) {
            paymentId?.let { id ->
                paymentRepository.getPayment(id)
                    .mapNotNull { it }
                    .collect { payment ->
                        _state.update { state ->
                            state.copy(
                                isEditMode = true,
                                amount = "${payment.amount}",
                                selectedCard = payment.card,
                                date = payment.date
                            )
                        }
                    }
            }
        }
    }

    fun onAction(action: PaymentDetailAction) {
        when(action) {
            is PaymentDetailAction.CardSelected -> {
                analyticsService.logEvent(if (state.value.isEditMode) AnalyticsEvent.EditPaymentSelectCardButtonTapped else AnalyticsEvent.AddPaymentSelectCardButtonTapped)
                _state.update { it.copy(selectedCard = action.card) }
            }

            is PaymentDetailAction.AmountChanged -> {
                _state.update { it.copy(amount = action.text) }
            }

            is PaymentDetailAction.DateChanged -> {
                analyticsService.logEvent(if (state.value.isEditMode) AnalyticsEvent.EditPaymentSelectDateButtonTapped else AnalyticsEvent.AddPaymentSelectDateButtonTapped)
                _state.update { it.copy(date = action.date) }
            }

            is PaymentDetailAction.SaveButtonTapped -> {
                analyticsService.logEvent(if (state.value.isEditMode) AnalyticsEvent.EditPaymentSaveButtonTapped else AnalyticsEvent.AddPaymentSaveButtonTapped)
                val payment = Payment(
                    id = paymentId ?: UUID.randomUUID().toString(),
                    amount = state.value.amount.toIntOrNull() ?: 0,
                    date = state.value.date,
                    card = state.value.selectedCard
                )
                viewModelScope.launch(Dispatchers.IO) {
                    paymentRepository.save(payment)
                }
                toastService.showToast(UiText.StringResourceId(if (state.value.isEditMode) R.string.add_payment_edit_success else  R.string.add_payment_save_success))
            }
        }
    }
}