package com.cc.foodorderingappproject.Screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.cc.foodorderingappproject.Screens.AppComponents.DetailContents
import com.cc.foodorderingappproject.viewModel.DetailPageViewModel


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetaySayfa(
    navController: NavController,
    detailPageViewModel: DetailPageViewModel,
    yemek_id: String
) {

    val yemekListesi = detailPageViewModel.yemeklerListesi.observeAsState(listOf())
    val adetYemek = remember { mutableStateOf(0) }
    var yemek_index = yemek_id.toInt() - 1

// Display a loading indicator while the food details are being fetched
    if (yemekListesi.value[yemek_index] == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        // Once the food details are fetched, show the detail UI
        DetailContents(
            yemek = yemekListesi.value[yemek_index],  // Non-nullable as we've already checked for null
            navController = navController,
            detailPageViewModel
        )
    }
}

