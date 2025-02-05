package com.alextos.cashback.features.settings.presentation.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alextos.cashback.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text("Настройки")
                }
            )
        }
    ) { innerPaddings ->
        SettingsView(
            modifier = Modifier.padding(top = innerPaddings.calculateTopPadding()),
            state = state
        )
    }
}

@Composable
private fun SettingsView(
    modifier: Modifier = Modifier,
    state: SettingsState
) {
    LazyColumn(
        modifier = modifier.padding(horizontal = 16.dp)
    ) {
        item {
            Column {
                Text(stringResource(R.string.app_version, state.appVersion))
                Text(stringResource(R.string.app_build, state.buildVersion))
            }
        }
    }
}

@Preview
@Composable
fun SettingsPreview() {

}