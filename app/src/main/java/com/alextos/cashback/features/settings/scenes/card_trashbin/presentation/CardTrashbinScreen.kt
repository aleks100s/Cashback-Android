package com.alextos.cashback.features.settings.scenes.card_trashbin.presentation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alextos.cashback.R
import com.alextos.cashback.common.UiText
import com.alextos.cashback.common.views.ContextMenuItem
import com.alextos.cashback.common.views.CustomWideButton
import com.alextos.cashback.common.views.RoundedList
import com.alextos.cashback.common.views.Screen

@Composable
fun CardTrashbinScreen(
    viewModel: CardTrashbinViewModel,
    goBack: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Screen(
        modifier = Modifier,
        title = stringResource(R.string.card_trashbin_title),
        goBack = goBack
    ) {
        CardTrashbinView(
            modifier = it,
            state = state,
            onAction = viewModel::onAction
        )
    }
}

@Composable
private fun CardTrashbinView(
    modifier: Modifier = Modifier,
    state: CardTrashbinState,
    onAction: (CardTrashbinAction) -> Unit
) {
    RoundedList(
        modifier = modifier.padding(horizontal = 16.dp),
        list = state.cards,
        itemView = { modifier, card ->
            Text(
                modifier = modifier.fillMaxWidth().padding(vertical = 4.dp),
                text = card.name
            )
        },
        bottomView = {
            CustomWideButton(
                modifier = Modifier.padding(top = 16.dp),
                title = stringResource(R.string.common_restore_all)
            ) {
                onAction(CardTrashbinAction.RestoreAll)
            }
        },
        emptyView = {
            Text(text = stringResource(R.string.trashbin_empty))
        },
        onItemClick = {},
        onSwipe = { card ->
            onAction(CardTrashbinAction.RestoreCard(card))
        },
        swipeBackground = Color.Green.copy(alpha = 0.5f),
        swipeText = stringResource(R.string.common_restore),
        contextMenuActions = { card ->
            listOf(
                ContextMenuItem(title = UiText.StringResourceId(R.string.common_restore)) {
                    onAction(CardTrashbinAction.RestoreCard(card))
                }
            )
        }
    )
}

@Preview
@Composable
private fun CardTrashbinPreview() {
    CardTrashbinView(state = CardTrashbinState()) {}
}