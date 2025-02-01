package com.alextos.cashback

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.alextos.cashback.common.theme.CashbackTheme
import com.alextos.cashback.features.cards.cards_list.presentation.CardsListScreen
import com.alextos.cashback.features.cards.cards_list.presentation.CardsListViewModel
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CashbackTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CardsListScreen(
                        modifier = Modifier.padding(innerPadding),
                        viewModel = koinViewModel()
                    ) {

                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello, $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true, name = "App preview", device = "spec:width=411dp,height=891dp")
@Composable
fun GreetingPreview() {
    CashbackTheme {
        Greeting("Android")
    }
}