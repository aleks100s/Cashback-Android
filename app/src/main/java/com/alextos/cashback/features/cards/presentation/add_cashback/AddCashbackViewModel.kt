package com.alextos.cashback.features.cards.presentation.add_cashback

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

class AddCashbackViewModel(
    savedStateHandle: SavedStateHandle,
    private val validateCashbackUseCase: ValidateCashbackUseCase,
    private val cardsRepository: CardsRepository,
    private val categoryMediator: CategoryMediator,
    private val toastService: ToastService
): ViewModel() {
    private val cardId = savedStateHandle.toRoute<CardsRoute.AddCashback>().cardId
    private val cashbackId = savedStateHandle.toRoute<CardsRoute.AddCashback>().cashbackId

    private var cashback: Cashback? = null

    private val _state = MutableStateFlow(AddCashbackState())
    val state = _state.asStateFlow()

    init {
        cashbackId?.let { cashbackId ->
            viewModelScope.launch(Dispatchers.IO) {
                cardsRepository.getCashback(cashbackId)?.let { cashback ->
                    this@AddCashbackViewModel.cashback = cashback
                    cardsRepository.getCardFlow(cardId)
                        .mapNotNull { it }
                        .collect { card ->
                            val isValid = validateCashbackUseCase.execute(card, cashback.percent, cashback.category)
                            _state.update { state ->
                                state.copy(
                                    card = card,
                                    percent = (cashback.percent * 100).toString(),
                                    selectedCategory = cashback.category,
                                    isValid = isValid
                                )
                            }
                        }
                }
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            categoryMediator.selectedCategory.collect { category ->
                onAction(AddCashbackAction.CategorySelected(category))
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
                        val isValid = validateCashbackUseCase.execute(card, percent, state.selectedCategory)
                        _state.update { it.copy(isValid = isValid) }
                    }
                }
        }
    }

    fun onAction(action: AddCashbackAction) {
        when(action) {
            is AddCashbackAction.ChangePercent -> {
                _state.update { state ->
                    state.copy(percent = action.value)
                }
            }
            is AddCashbackAction.SaveCashback -> {
                state.value.selectedCategory?.let { category ->
                    val percent = (state.value.percent.toDoubleOrNull() ?: 0.0) / 100
                    val cashback = this.cashback?.copy(
                        percent = percent,
                        category = category
                    ) ?: Cashback(category = category, percent = percent)
                    viewModelScope.launch(Dispatchers.IO) {
                        cardsRepository.createCashback(cashback = cashback, cardId = cardId)
                        state.value.card?.let {
                            cardsRepository.createOrUpdate(it)
                        }
                    }
                    toastService.showToast(UiText.StringResourceId(R.string.add_cashback_added))
                }
            }
            is AddCashbackAction.CategorySelected -> {
                _state.update { state ->
                    state.copy(selectedCategory = action.category)
                }
            }
            is AddCashbackAction.SelectCategory -> {}
        }
    }
}