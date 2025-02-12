package com.alextos.cashback.features.settings.scenes.trashbin

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alextos.cashback.R
import com.alextos.cashback.common.views.Screen

@Composable
fun TrashbinScreen(
    viewModel: TrashbinViewModel,
    goBack: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Screen(
        modifier = Modifier,
        title = stringResource(R.string.trashbin_title),
        goBack = goBack
    ) {
        TrashbinView(state = state)
    }
}

@Composable
private fun TrashbinView(
    state: TrashbinState
) {
    Text("Корзина")
}

@Preview
@Composable
private fun TrashbinPreview() {
    TrashbinView(TrashbinState())
}