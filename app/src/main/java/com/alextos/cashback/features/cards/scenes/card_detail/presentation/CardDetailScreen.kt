package com.alextos.cashback.features.cards.scenes.card_detail.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alextos.cashback.R
import com.alextos.cashback.core.domain.models.currency.Currency
import com.alextos.cashback.common.views.Screen
import com.alextos.cashback.common.views.ContextMenuItem
import com.alextos.cashback.features.cards.scenes.card_detail.presentation.components.CashbackView
import com.alextos.cashback.common.UiText
import com.alextos.cashback.common.ads.AdBannerView
import com.alextos.cashback.common.makeColor
import com.alextos.cashback.common.views.ColorPicker
import com.alextos.cashback.common.views.CustomButton
import com.alextos.cashback.common.views.CustomDivider
import com.alextos.cashback.common.views.CustomTextField
import com.alextos.cashback.common.views.CustomWideButton
import com.alextos.cashback.common.views.Dialog
import com.alextos.cashback.common.views.CustomLabel
import com.alextos.cashback.common.views.FavouriteButton
import com.alextos.cashback.common.views.PickerDropdown
import com.alextos.cashback.common.views.RoundedList
import com.alextos.cashback.common.views.SectionView

@Composable
fun CardDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: CardDetailViewModel,
    goBack: () -> Unit,
    onAddCashback: () -> Unit,
    onEditCashback: (String) -> Unit,
    onDelete: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val haptic = LocalHapticFeedback.current

    Screen(
        modifier = modifier,
        title = state.cardName,
        color = makeColor(state.color),
        goBack = goBack,
        floatingActionButton = {
            if (!state.isEditMode) {
                Button(onClick = {
                    viewModel.onAction(CardDetailAction.AddCashback)
                    onAddCashback()
                }) {
                    CustomLabel(
                        title = stringResource(R.string.card_detail_add_cashback),
                        imageVector = Icons.Filled.Add
                    )
                }
            }
        },
        actions = {
            CustomButton(
                title = if (state.isEditMode) {
                    stringResource(R.string.common_save)
                } else {
                    stringResource(R.string.common_edit)
                }
            ) {
                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                viewModel.onAction(CardDetailAction.ToggleEditMode)
            }
        },
        bannerView = if (state.isAdVisible) {
            { AdBannerView(viewModel.bannerId) }
        } else null
    ) {
        if (state.isEditMode) {
            EditCardView(
                modifier = it,
                state = state,
                onAction = { action ->
                    viewModel.onAction(action)
                    when (action) {
                        is CardDetailAction.DeleteCard -> {
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        }
                        is CardDetailAction.ToggleFavourite -> {
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        }
                        else -> {}
                    }
                }
            )
        } else {
            CardDetailView(
                modifier = it,
                state = state,
                onAction = { action ->
                    viewModel.onAction(action)
                    when (action) {
                        is CardDetailAction.EditCashback -> {
                            onEditCashback(action.cashback.id)
                        }
                        is CardDetailAction.DeleteCashback -> {
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        }
                        else -> {}
                    }
                }
            )
        }
    }

    if (state.isDeleteCardDialogShown) {
        Dialog(
            title = stringResource(R.string.common_are_you_sure),
            text = stringResource(R.string.card_detail_delete_card_warning),
            actionTitle = stringResource(R.string.common_remove),
            onConfirm = {
                viewModel.onAction(CardDetailAction.DeleteCard)
                onDelete()
            },
            onDismiss = {
                viewModel.onAction(CardDetailAction.DismissDeleteCardDialog)
            }
        )
    }
}

@Composable
private fun CardDetailView(
    modifier: Modifier = Modifier,
    state: CardDetailState,
    onAction: (CardDetailAction) -> Unit
) {
    val list = state.card?.cashback ?: emptyList()
    val color = makeColor(state.card?.color)

    RoundedList(
        modifier = modifier.padding(horizontal = 16.dp),
        list = list,
        itemView = { itemModifier, cashback ->
            CashbackView(
                modifier = itemModifier,
                cashback = cashback,
                color = color
            )
        },
        emptyView = {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(R.drawable.credit_card),
                    contentDescription = stringResource(R.string.card_detail_no_cashback),
                    tint = color.copy(alpha = 0.3f),
                )

                Text(
                    text = stringResource(R.string.card_detail_no_cashback),
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center
                )
            }
        },
        topView = {
            Text(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .padding(vertical = 4.dp),
                text = stringResource(R.string.card_detail_cashback_list_title)
            )
        },
        bottomView = {
            Text(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .padding(vertical = 4.dp),
                text = stringResource(R.string.card_detail_cashback_currency_form, state.card?.currency ?: ""),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.secondary
            )
        },
        onItemClick = {
            onAction(CardDetailAction.EditCashback(it))
        },
        onSwipe = {
            onAction(CardDetailAction.DeleteCashback(it))
        },
        swipeText = stringResource(R.string.common_remove),
        contextMenuActions = {
            listOf(
                ContextMenuItem(
                    title = UiText.StringResourceId(R.string.card_detail_edit_cashback),
                    action = {
                        onAction(CardDetailAction.EditCashback(it))
                    }
                ),
                ContextMenuItem(
                    title = UiText.StringResourceId(R.string.card_detail_delete_cashback),
                    isDestructive = true,
                    action = {
                        onAction(CardDetailAction.DeleteCashback(it))
                    }
                )
            )
        }
    )
}

@Composable
private fun EditCardView(
    modifier: Modifier = Modifier,
    state: CardDetailState,
    onAction: (CardDetailAction) -> Unit
) {
    LazyColumn(
        modifier = modifier.padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        item {
            Spacer(Modifier.height(16.dp))
        }
        item {
            SectionView(title = stringResource(R.string.card_detail_edit_card_info)) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    CustomTextField(
                        value = state.cardName,
                        onValueChange = {
                            onAction(CardDetailAction.ChangeCardName(it))
                        },
                        label = stringResource(R.string.card_detail_card_name)
                    )

                    CustomDivider()

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = stringResource(R.string.card_detail_cashback_currency))

                        PickerDropdown(
                            modifier = Modifier.offset(x = 16.dp),
                            selected = state.currency,
                            options = Currency.entries,
                            onSelect = {
                                onAction(CardDetailAction.ChangeCurrency(it))
                            }
                        )
                    }

                    CustomDivider()

                    ColorPicker(
                        title = stringResource(R.string.card_detail_color),
                        color = state.color,
                        onColorChange = {
                            onAction(CardDetailAction.ChangeColor(it))
                        }
                    )

                    CustomDivider()

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (state.isFavourite) {
                                stringResource(R.string.card_detail_in_favourites)
                            } else {
                                stringResource(R.string.card_detail_add_to_favourites)
                            }
                        )

                        FavouriteButton(
                            modifier = Modifier.offset(x = 12.dp),
                            isFavourite = state.isFavourite,
                            onFavouriteToggle = {
                                onAction(CardDetailAction.ToggleFavourite)
                            }
                        )
                    }
                }
            }
        }

        item {
            SectionView(
                title = stringResource(R.string.card_detail_for_brave_users),
                footer = stringResource(R.string.card_detail_this_action_cannot_be_undone)
            ) {
                Column(
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    if (state.card?.cashback?.isNotEmpty() == true) {
                        CustomWideButton(
                            title = stringResource(R.string.card_detail_delete_all_cashback),
                            color = MaterialTheme.colorScheme.error
                        ) {
                            onAction(CardDetailAction.DeleteAllCashback)
                        }

                        CustomDivider()
                    }

                    CustomWideButton(
                        title = stringResource(R.string.card_detail_delete_card),
                        color = MaterialTheme.colorScheme.error
                    ) {
                        onAction(CardDetailAction.ShowDeleteCardDialog)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun CardDetailPreview() {
    CardDetailView(state = CardDetailState()) {}
}