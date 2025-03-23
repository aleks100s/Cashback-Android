package com.alextos.cashback.common.views

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
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
    topView: @Composable () -> Unit = {},
    bottomView: @Composable () -> Unit = {},
    emptyView: @Composable () -> Unit,
    onItemClick: (Element) -> Unit = {},
    contextMenuActions: (Element) -> List<ContextMenuItem<Element>>,
    onSwipe: (Element) -> Unit = {},
    swipeBackground: Color = Color.Red,
    swipeText: String = "",
    allowSwipe: Boolean = true
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
            item {
                topView()
            }

            items(items = list, key = { it.id }) { item ->
                val topCornersShape = if (item == list.firstOrNull()) {
                    RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                } else RectangleShape

                val bottomCornersShape = if (item == list.lastOrNull()) {
                    RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
                } else RectangleShape

                val backgroundTopCornersShape = if (item == list.firstOrNull()) {
                    RoundedCornerShape(topStart = 18.dp, topEnd = 18.dp)
                } else RectangleShape

                val backgroundBottomCornersShape = if (item == list.lastOrNull()) {
                    RoundedCornerShape(bottomStart = 18.dp, bottomEnd = 18.dp)
                } else RectangleShape

                if (allowSwipe) {
                    SwipeableItem(
                        modifier = Modifier
                            .animateItem()
                            .clip(backgroundTopCornersShape)
                            .clip(backgroundBottomCornersShape),
                        content = {
                            ContextMenuView(
                                item = item,
                                itemView = itemView,
                                topCornersShape = topCornersShape,
                                bottomCornersShape = bottomCornersShape,
                                contextMenuActions = contextMenuActions,
                                onItemClick = onItemClick
                            )
                        },
                        onSwipe = {
                            onSwipe(item)
                            true
                        },
                        swipeBackground = swipeBackground,
                        swipeText = swipeText
                    )
                } else {
                    ContextMenuView(
                        item = item,
                        itemView = itemView,
                        topCornersShape = topCornersShape,
                        bottomCornersShape = bottomCornersShape,
                        contextMenuActions = contextMenuActions,
                        onItemClick = onItemClick
                    )
                }

                if (list.lastOrNull() != item) {
                    CustomDivider()
                }
            }

            item {
                bottomView()
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

@Composable
private fun <Element> LazyItemScope.ContextMenuView(
    item: Element,
    itemView: @Composable (Modifier, Element) -> Unit,
    topCornersShape: Shape,
    bottomCornersShape: Shape,
    contextMenuActions: (Element) -> List<ContextMenuItem<Element>>,
    onItemClick: (Element) -> Unit,
) {
    ContextMenu(
        modifier = Modifier.animateItem(),
        element = item,
        content = {
            itemView(
                Modifier
                    .animateItem()
                    .clip(topCornersShape)
                    .clip(bottomCornersShape)
                    .background(MaterialTheme.colorScheme.surfaceColorAtElevation(4.dp))
                    .padding(vertical = 8.dp, horizontal = 16.dp),
                item
            )
        },
        actions = contextMenuActions(item),
        onClick = onItemClick
    )
}