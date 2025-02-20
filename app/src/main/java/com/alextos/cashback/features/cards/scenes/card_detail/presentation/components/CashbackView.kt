package com.alextos.cashback.features.cards.scenes.card_detail.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alextos.cashback.core.domain.models.Cashback
import com.alextos.cashback.core.domain.models.generateMockCashback
import com.alextos.cashback.core.presentation.views.CategoryIconSize
import com.alextos.cashback.core.presentation.views.CategoryIconView

@Composable
fun CashbackView(
    modifier: Modifier = Modifier,
    cashback: Cashback,
    color: Color
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        CategoryIconView(
            category = cashback.category,
            size = CategoryIconSize.Medium,
            color = color
        )

        Text(
            modifier = Modifier.width(48.dp),
            text = cashback.percentString()
        )

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = cashback.category.name
        )
    }
}

@Preview
@Composable
private fun CashbackPreview() {
    CashbackView(cashback = generateMockCashback(), color = Color.Red)
}