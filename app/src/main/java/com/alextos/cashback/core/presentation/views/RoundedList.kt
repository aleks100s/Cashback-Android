package com.alextos.cashback.core.presentation.views

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp

interface ListElement {
    val id: String
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun <Element: ListElement> RoundedList(
    modifier: Modifier = Modifier,
    list: List<Element>,
    itemView: @Composable (Modifier, Element) -> Unit,
    stickyHeader: @Composable () -> Unit = {},
    emptyView: @Composable () -> Unit,
    onClick: (Element) -> Unit
) {
    val scrollState: LazyListState = rememberLazyListState()

    LazyColumn(modifier = modifier, state = scrollState) {
        stickyHeader {
            AnimatedVisibility(
                visible = scrollState.lastScrolledBackward || !scrollState.canScrollBackward,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                stickyHeader()
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }

        if (list.isNotEmpty()) {
            items(items = list, key = { it.id }) { item ->
                val topCornersShape = if (item == list.firstOrNull()) {
                    RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                } else RectangleShape

                val bottomCornersShape = if (item == list.lastOrNull()) {
                    RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
                } else RectangleShape

                itemView(
                    Modifier
                        .animateItem()
                        .clickable {
                            onClick(item)
                        }
                        .clip(topCornersShape)
                        .clip(bottomCornersShape)
                        .background(MaterialTheme.colorScheme.surfaceColorAtElevation(4.dp))
                        .padding(vertical = 12.dp, horizontal = 16.dp),
                    item
                )

                if (list.lastOrNull() != item) {
                    HorizontalDivider()
                }
            }
        } else {
            item {
                emptyView()
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
