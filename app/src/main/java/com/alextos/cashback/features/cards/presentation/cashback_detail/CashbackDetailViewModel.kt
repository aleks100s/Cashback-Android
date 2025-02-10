package com.alextos.cashback.features.cards.presentation.cashback_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.alextos.cashback.R
import com.alextos.cashback.core.domain.models.Cashback
import com.alextos.cashback.core.domain.services.ToastService
import com.alextos.cashback.features.cards.CardsRoute
import com.alextos.cashback.features.cards.domain.CardsRepository
import com.alextos.cashback.features.cards.domain.use_cases.ValidateCashbackUseCase
import com.alextos.cashback.features.category.CategoryMediator
import com.alextos.cashback.util.UiText
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
    private val cardsRepository: CardsRepository,
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
            cardsRepository.getCardFlow(cardId)
                .mapNotNull { it }
                .collect { card ->
                    _state.update { it.copy(card = card) }

                    cashbackId?.let { cashbackId ->
                        cardsRepository.getCashback(cashbackId)?.let { cashback ->
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

                val percent = (state.value.percent.toDoubleOrNull() ?: 0.0) / 100
                viewModelScope.launch(Dispatchers.IO) {
                    this@CashbackDetailViewModel.cashback?.let { oldCashback ->
                        viewModelScope.launch(Dispatchers.IO) {
                            cardsRepository.deleteCashback(oldCashback, cardId)
                        }
                    }

                    val newCashback = Cashback(
                        percent = percent,
                        category = selectedCategory
                    )
                    cardsRepository.createCashback(newCashback, cardId)
                    state.value.card?.let { card ->
                        cardsRepository.createOrUpdate(card)
                    }
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