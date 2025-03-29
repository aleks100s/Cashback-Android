package com.alextos.cashback.features.cards.scenes.card_detail.domain

import com.alextos.cashback.core.domain.models.Card
import com.alextos.cashback.core.domain.repository.CardRepository
import com.alextos.cashback.core.domain.repository.PaymentRepository

class DeleteCardUseCase(
    private val cardRepository: CardRepository,
    private val paymentRepository: PaymentRepository
) {
    suspend fun execute(card: Card) {
        card.cashback.forEach {
            cardRepository.deleteCashback(it, card.id)
        }
        paymentRepository.deleteAllPayments(card)
        cardRepository.archiveCard(card.copy(isArchived = true))
    }
}