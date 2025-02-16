package com.alextos.cashback.features.settings.scenes.settings.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alextos.cashback.R
import com.alextos.cashback.common.ads.AdBannerView
import com.alextos.cashback.common.views.Screen
import com.alextos.cashback.common.views.SectionView

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel,
    openCatalog: () -> Unit,
    openCardTrashbin: () -> Unit,
    openCategoryTrashbin: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Screen(
        modifier = modifier,
        title = stringResource(R.string.settings_title),
        bannerView = {
            AdBannerView("R-M-14164420-2")
        }
    ) {
        SettingsView(
            modifier = it,
            state = state,
            onAction = { action ->
                viewModel.onAction(action)
                when (action) {
                    SettingsAction.ShowCatalog -> {
                        openCatalog()
                    }
                    SettingsAction.ShowCardTrashbin -> {
                        openCardTrashbin()
                    }
                    SettingsAction.ShowCategoryTrashbin -> {
                        openCategoryTrashbin()
                    }
                    else -> {}
                }
            }
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
        modifier = modifier.padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        item {
            SectionView(
                title = stringResource(R.string.settings_notifications_title),
                footer = stringResource(R.string.settings_notifications_footer)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(stringResource(R.string.settings_notifications))

                    Switch(
                        checked = state.isNotificationsEnabled,
                        onCheckedChange = {
                            onAction(SettingsAction.SetNotifications(it))
                        }
                    )
                }
            }
        }

        item {
            SectionView(
                title = stringResource(R.string.settings_category_catalog_header),
                footer = stringResource(R.string.settings_category_catalog_footer)
            ) {
                SectionItem(
                    title = stringResource(R.string.settings_categories),
                    onAction = onAction
                )
            }
        }

        item {
            SectionView(
                title = stringResource(R.string.settings_trashbin_title),
                footer = stringResource(R.string.settings_trashbin_footer)
            ) {
                SectionItem(title = stringResource(R.string.settings_trashbin_cards)) {
                    onAction(SettingsAction.ShowCardTrashbin)
                }

                HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))

                SectionItem(title = stringResource(R.string.settings_trashbin_categories)) {
                    onAction(SettingsAction.ShowCategoryTrashbin)
                }
            }
        }

        item {
            SectionView(title = stringResource(R.string.settings_app_about)) {
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

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun SectionItem(
    title: String,
    onAction: (SettingsAction) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onAction(SettingsAction.ShowCatalog)
            }
            .padding(vertical = 12.dp, horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(title)

        Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, title)
    }
}

@Composable
private fun ClickableItem(
    title: String,
    value: String,
    onAction: (SettingsAction) -> Unit
) {
    val haptic = LocalHapticFeedback.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                onAction(SettingsAction.CopyValue(label = title, text = value))
            }
            .padding(vertical = 12.dp, horizontal = 16.dp),
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