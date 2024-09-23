package com.cc.foodorderingappproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.cc.foodorderingappproject.SayfaGecisleri.SayfaGecisleri
import com.cc.foodorderingappproject.ui.theme.FoodOrderingAppProjectTheme
import com.cc.foodorderingappproject.viewModel.CartPageViewModel
import com.cc.foodorderingappproject.viewModel.DetailPageViewModel
import com.cc.foodorderingappproject.viewModel.HomePageViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    //VM's
    val anasayfaViewModel: HomePageViewModel by viewModels()
    val detailPageViewModel: DetailPageViewModel by viewModels()
    val cartPageViewModel: CartPageViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FoodOrderingAppProjectTheme {
                SayfaGecisleri(anasayfaViewModel,detailPageViewModel,cartPageViewModel)
            }
        }
    }
}