package com.alextos.cashback.features.settings.presentation.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
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
import com.alextos.cashback.features.settings.presentation.settings.components.SettingsSectionView

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
                    Text(stringResource(R.string.settings_title))
                }
            )
        }
    ) { innerPaddings ->
        SettingsView(
            modifier = Modifier.padding(top = innerPaddings.calculateTopPadding()),
            state = state,
            onAction = viewModel::onAction
        )
    }
}

@Composable
private fun SettingsView(
    modifier: Modifier = Modifier,
    state: SettingsState,
    onAction: (SettingsAction) -> Unit
) {
    LazyColumn(
        modifier = modifier.padding(horizontal = 16.dp)
    ) {
        item {
            SettingsSectionView(title = stringResource(R.string.settings_app_about)) {
                Column {
                    ClickableItem(
                        title = stringResource(R.string.settings_app_version),
                        value = state.appVersion,
                        onAction = onAction
                    )

                    HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))

                    ClickableItem(
                        title = stringResource(R.string.settings_app_build),
                        value = "${state.buildVersion}",
                        onAction = onAction
                    )
                }
            }
        }
    }
}

@Composable
private fun ClickableItem(
    title: String,
    value: String,
    onAction: (SettingsAction) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onAction(SettingsAction.CopyValue(label = title, text = value))
            }
            .padding(vertical = 8.dp, horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(title)
        Text(value)
    }
}

@Preview(backgroundColor = 0xFF03A9F4)
@Composable
fun SettingsPreview() {
    Surface {
        SettingsView(state = SettingsState(appVersion = "1.2.3", buildVersion = 777)) { }

    }
}