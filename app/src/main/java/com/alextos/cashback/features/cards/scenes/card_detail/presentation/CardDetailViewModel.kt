package com.alextos.cashback.features.cards.scenes.card_detail.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.alextos.cashback.R
import com.alextos.cashback.core.domain.services.ToastService
import com.alextos.cashback.features.cards.CardsRoute
import com.alextos.cashback.core.domain.repository.CardRepository
import com.alextos.cashback.features.cards.scenes.card_detail.domain.DeleteAllCashbackUseCase
import com.alextos.cashback.features.cards.scenes.card_detail.domain.DeleteCardUseCase
import com.alextos.cashback.common.UiText
import com.alextos.cashback.core.domain.models.currency.Currency
import com.alextos.cashback.core.domain.services.AnalyticsEvent
import com.alextos.cashback.core.domain.services.AnalyticsService
import com.alextos.cashback.core.domain.services.WidgetUpdateService
import com.alextos.cashback.features.cards.scenes.card_detail.domain.DeleteCashbackUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CardDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val deleteCardUseCase: DeleteCardUseCase,
    private val deleteAllCashbackUseCase: DeleteAllCashbackUseCase,
    private val deleteCashbackUseCase: DeleteCashbackUseCase,
    private val repository: CardRepository,
    private val toastService: ToastService,
    private val analyticsService: AnalyticsService,
    private val widgetUpdateService: WidgetUpdateService
): ViewModel() {
    private val cardId = savedStateHandle.toRoute<CardsRoute.CardDetail>().cardId

    private val _state = MutableStateFlow(CardDetailState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getCardFlow(cardId)
                .collect { card ->
                    _state.update { state ->
                        state.copy(
                            card = card,
                            cardName = card?.name ?: "",
                            isFavourite = card?.isFavourite ?: false,
                            currency = card?.currency ?: Currency.RUBLE,
                            color = card?.color ?: "#E7E7E7"
                        )
                    }
                }
        }
    }

    fun onAction(action: CardDetailAction) {
        when (action) {
            is CardDetailAction.ToggleEditMode -> {
                analyticsService.logEvent(if (state.value.isEditMode) AnalyticsEvent.CardDetailEditButtonTapped else AnalyticsEvent.CardDetailDoneButtonTapped)
                _state.update { it.copy(isEditMode = !it.isEditMode) }
                val state = state.value
                val card = state.card?.copy(
                    name = state.cardName,
                    isFavourite = state.isFavourite,
                    currency = state.currency,
                    color = state.color
                )
                if (!state.isEditMode && card != null) {
                    viewModelScope.launch(Dispatchers.IO) {
                        repository.createOrUpdate(card)
                    }
                    toastService.showToast(UiText.StringResourceId(R.string.card_list_card_changed))
                    widgetUpdateService.updateWidget()
                }
            }
            is CardDetailAction.DeleteCashback -> {
                analyticsService.logEvent(AnalyticsEvent.CardDetailDeleteCashback)
                viewModelScope.launch(Dispatchers.IO) {
                    val card = state.value.card
                    if (card != null) {
                        deleteCashbackUseCase.execute(card = card, cashback = action.cashback)
                        withContext(Dispatchers.Main) {
                            toastService.showToast(UiText.StringResourceId(R.string.card_detail_cashback_removed))
                            widgetUpdateService.updateWidget()
                        }
                    }
                }
            }
            is CardDetailAction.ChangeCardName -> {
                _state.update { it.copy(cardName = action.name) }
            }
            is CardDetailAction.ToggleFavourite -> {
                analyticsService.logEvent(AnalyticsEvent.CardDetailFavouriteToggle)
                _state.update { it.copy(isFavourite = !it.isFavourite) }
                widgetUpdateService.updateWidget()
            }
            is CardDetailAction.ChangeCurrency -> {
                analyticsService.logEvent(AnalyticsEvent.CardDetailCurrencyChange)
                _state.update { it.copy(currency = action.currency) }
            }
            is CardDetailAction.ChangeColor -> {
                _state.update { it.copy(color = "#${action.color}") }
            }
            is CardDetailAction.DeleteAllCashback -> {
                analyticsService.logEvent(AnalyticsEvent.CardDetailDeleteAllCashbackButtonTapped)
                viewModelScope.launch(Dispatchers.IO) {
                    val card = state.value.card
                    if (card != null) {
                        deleteAllCashbackUseCase.execute(card)
                        viewModelScope.launch(Dispatchers.Main) {
                            _state.update { it.copy(card = card.copy(cashback = emptyList())) }
                            toastService.showToast(UiText.StringResourceId(R.string.card_detail_all_cashback_deleted))
                            widgetUpdateService.updateWidget()
                        }
                    }
                }
            }
            is CardDetailAction.ShowDeleteCardDialog -> {
                analyticsService.logEvent(AnalyticsEvent.CardDetailDeleteCardButtonTapped)
                _state.update { it.copy(isDeleteCardDialogShown = true) }
            }
            is CardDetailAction.DismissDeleteCardDialog -> {
                _state.update { it.copy(isDeleteCardDialogShown = false) }
            }
            is CardDetailAction.DeleteCard -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val card = state.value.card
                    if (card != null) {
                        deleteCardUseCase.execute(card)
                    }
                }
                toastService.showToast(UiText.StringResourceId(R.string.card_detail_card_deleted))
                widgetUpdateService.updateWidget()
            }
            is CardDetailAction.EditCashback -> {
                analyticsService.logEvent(AnalyticsEvent.CardDetailCashbackTapped)
            }
            is CardDetailAction.AddCashback -> {
                analyticsService.logEvent(AnalyticsEvent.CardDetailAddCashbackButtonTapped)
            }
        }
    }
}