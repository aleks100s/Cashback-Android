package com.alextos.cashback.common.views

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import com.alextos.cashback.common.UiText

data class ContextMenuItem<Element>(
    val title: UiText,
    val isDestructive: Boolean = false,
    val action: (Element) -> Unit
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun <Element> ContextMenu(
    modifier: Modifier = Modifier,
    element: Element,
    content: @Composable () -> Unit,
    actions: List<ContextMenuItem<Element>>,
    onClick: (Element) -> Unit
) {
    val haptic = LocalHapticFeedback.current
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .combinedClickable(
                onClick = {
                    onClick(element)
                },
                onLongClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    expanded = true
                }
            )
    ) {
        content()

        if (actions.isNotEmpty()) {
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = {
                    expanded = false
                }
            ) {
                actions.forEach {
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = it.title.asString(),
                                color = if (it.isDestructive) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.secondary
                            )
                        },
                        onClick = {
                            it.action(element)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}