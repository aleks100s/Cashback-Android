package com.alextos.cashback.features.payments.scenes.payments.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.alextos.cashback.R
import com.alextos.cashback.common.makeColor
import com.alextos.cashback.core.domain.models.Payment
import com.alextos.cashback.core.domain.models.currency.symbol
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun PaymentItemView(
    modifier: Modifier,
    payment: Payment
) {
    val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale("ru_RU"))

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Icon(
                    painter = painterResource(R.drawable.credit_card),
                    tint = makeColor(payment.card?.color),
                    contentDescription = payment.card?.name,
                    modifier = Modifier.size(24.dp)
                )

                Text(text = payment.card?.name ?: "")
            }

            Text("${payment.amount} ${payment.card?.currency?.symbol ?: "₽"}")
        }

        Row {
            Text(
                text = formatter.format(payment.date),
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}