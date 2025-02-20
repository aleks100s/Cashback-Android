package com.alextos.cashback.common.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.alextos.cashback.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Screen(
    modifier: Modifier,
    title: String,
    color: Color? = null,
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
                colors = color?.let {
                    TopAppBarDefaults.topAppBarColors().copy(containerColor = it.copy(0.4f))
                } ?: TopAppBarDefaults.topAppBarColors(),
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
        containerColor = color?.let {
            it.copy(alpha = 0.3f)
        } ?: MaterialTheme.colorScheme.background,
        floatingActionButton = floatingActionButton,
        bottomBar = {
            if (bannerView != null) {
                bannerView()
            }
        }
    ) { innerPaddings ->
        content(Modifier.padding(
            top = innerPaddings.calculateTopPadding(),
            bottom = if (bannerView != null) innerPaddings.calculateBottomPadding() else 0.dp
        ))
    }
}