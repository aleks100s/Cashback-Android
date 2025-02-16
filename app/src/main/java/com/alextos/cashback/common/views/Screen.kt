package com.alextos.cashback.common.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.alextos.cashback.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Screen(
    modifier: Modifier,
    title: String,
    goBack: (() -> Unit)? = null,
    backButtonIcon: ImageVector = Icons.AutoMirrored.Filled.ArrowBack,
    floatingActionButton: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    bannerView: @Composable (() -> Unit)? = null,
    content: @Composable (Modifier) -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(title)
                },
                actions = actions,
                navigationIcon = {
                    if (goBack != null) {
                        IconButton(
                            onClick = {
                                goBack()
                            }
                        ) {
                            Icon(
                                backButtonIcon,
                                stringResource(R.string.comon_go_back)
                            )
                        }
                    }
                }
            )
        },
        floatingActionButton = floatingActionButton,
        bottomBar = {
            if (bannerView != null) {
                bannerView()
            }
        }
    ) { innerPaddings ->
        content(Modifier.padding(
            top = innerPaddings.calculateTopPadding(),
            bottom = innerPaddings.calculateBottomPadding()
        ))
    }
}