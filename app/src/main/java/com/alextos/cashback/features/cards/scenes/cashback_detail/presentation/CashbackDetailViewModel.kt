package com.alextos.cashback.features.cards.scenes.cashback_detail.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.alextos.cashback.R
import com.alextos.cashback.core.domain.models.Cashback
import com.alextos.cashback.core.domain.services.ToastService
import com.alextos.cashback.features.cards.CardsRoute
import com.alextos.cashback.core.domain.repository.CardRepository
import com.alextos.cashback.features.cards.scenes.cashback_detail.domain.ValidateCashbackUseCase
import com.alextos.cashback.features.category.CategoryMediator
import com.alextos.cashback.common.UiText
import com.alextos.cashback.features.cards.scenes.cashback_detail.domain.CreateCashbackUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CashbackDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val validateCashbackUseCase: ValidateCashbackUseCase,
    private val createCashbackUseCase: CreateCashbackUseCase,
    private val cardRepository: CardRepository,
    private val categoryMediator: CategoryMediator,
    private val toastService: ToastService
): ViewModel() {
    private val cardId = savedStateHandle.toRoute<CardsRoute.CashbackDetail>().cardId
    private val cashbackId = savedStateHandle.toRoute<CardsRoute.CashbackDetail>().cashbackId

    private var cashback: Cashback? = null

    private val _state = MutableStateFlow(CashbackDetailState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            cardRepository.getCardFlow(cardId)
                .mapNotNull { it }
                .collect { card ->
                    _state.update { it.copy(card = card) }

                    cashbackId?.let { cashbackId ->
                        cardRepository.getCashback(cashbackId)?.let { cashback ->
                            this@CashbackDetailViewModel.cashback = cashback
                            _state.update { state ->
                                state.copy(
                                    card = card,
                                    percent = (cashback.percent * 100).toString(),
                                    selectedCategory = cashback.category,
                                    isValid = true,
                                    title = UiText.StringResourceId(R.string.cashback_detail_edit_title)
                                )
                            }
                        }
                    } ?: run {
                        _state.update {
                            it.copy(title = UiText.StringResourceId(R.string.add_cashback_title))
                        }
                    }
                }
        }

        viewModelScope.launch(Dispatchers.IO) {
            categoryMediator.selectedCategory.collect { category ->
                onAction(CashbackDetailAction.CategorySelected(category))
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            _state
                .distinctUntilChanged { old, new ->
                    old.selectedCategory == new.selectedCategory && old.percent == new.percent
                }
                .collect { state ->
                    state.card?.let { card ->
                        val percent = (state.percent.toDoubleOrNull() ?: 0.0) / 100
                        val isValid = validateCashbackUseCase.execute(card, percent, state.selectedCategory, cashback?.category)
                        _state.update { it.copy(isValid = isValid) }
                    }
                }
        }
    }

    fun onAction(action: CashbackDetailAction) {
        when(action) {
            is CashbackDetailAction.ChangePercent -> {
                _state.update { state ->
                    state.copy(percent = action.value)
                }
            }
            is CashbackDetailAction.SaveCashbackDetail -> {
                val selectedCategory = state.value.selectedCategory
                val card = state.value.card
                if (selectedCategory == null || card == null) {
                    return
                }

                viewModelScope.launch(Dispatchers.IO) {
                    createCashbackUseCase.execute(
                        card = card,
                        existingCashback = cashback,
                        percent = state.value.percent,
                        category = selectedCategory
                    )
                }

                if (cashbackId != null) {
                    toastService.showToast(UiText.StringResourceId(R.string.add_cashback_changed))
                } else {
                    toastService.showToast(UiText.StringResourceId(R.string.add_cashback_added))
                }
            }
            is CashbackDetailAction.CategorySelected -> {
                _state.update { state ->
                    state.copy(selectedCategory = action.category)
                }
            }
            is CashbackDetailAction.SelectCategory -> {}
        }
    }
}