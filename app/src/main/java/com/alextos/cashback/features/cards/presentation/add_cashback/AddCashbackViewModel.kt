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

    private val _state = MutableStateFlow(AddCashbackState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            cardsRepository.getCardFlow(cardId)
                .collect { card ->
                    _state.update { it.copy(card = card) }
                }
        }

        viewModelScope.launch(Dispatchers.IO) {
            categoryMediator.selectedCategory.collect { category ->
                onAction(AddCashbackAction.CategorySelected(category))
            }
        }
    }

    fun onAction(action: AddCashbackAction) {
        when(action) {
            is AddCashbackAction.ChangePercent -> {
                _state.update { state ->
                    state.copy(
                        percent = action.value,
                        isValid = state.card?.let { card ->
                            validateCashbackUseCase.execute(
                                card = card,
                                percent = state.percent,
                                selectedCategory = state.selectedCategory
                            )
                        } ?: false
                    )
                }
            }
            is AddCashbackAction.SaveCashback -> {
                state.value.selectedCategory?.let { category ->
                    val cashback = Cashback(category = category, percent = state.value.percent)
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
                    state.copy(
                        selectedCategory = action.category,
                        isValid = state.card?.let { card ->
                            validateCashbackUseCase.execute(
                                card = card,
                                percent = state.percent,
                                selectedCategory = action.category
                            )
                        } ?: false
                    )
                }
            }
            is AddCashbackAction.SelectCategory -> {}
        }
    }
}