package com.cc.foodorderingappproject.SayfaGecisleri

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.cc.foodorderingappproject.Screens.Anasayfa
import com.cc.foodorderingappproject.Screens.CartPage
import com.cc.foodorderingappproject.Screens.DetaySayfa
import com.cc.foodorderingappproject.viewModel.CartPageViewModel
import com.cc.foodorderingappproject.viewModel.DetailPageViewModel
import com.cc.foodorderingappproject.viewModel.HomePageViewModel


@Composable
fun SayfaGecisleri(
    anasayfaviewModel: HomePageViewModel,
    detailPageViewModel: DetailPageViewModel,
    cartPageViewModel: CartPageViewModel
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "anasayfa") {


        composable("anasayfa") {
            Anasayfa(navController = navController, anasayfaviewModel)
        }

        composable("detay_sayfa/{yemek_id}", arguments = listOf(
            navArgument("yemek_id") { type = NavType.StringType }
        )) { navBackStackEntry ->
            val yemek_id = navBackStackEntry.arguments?.getString("yemek_id")
            if (yemek_id != null) {
                DetaySayfa(navController, detailPageViewModel, yemek_id)
            }
        }
        composable("sepet") {
            CartPage(cartPageViewModel = cartPageViewModel, navController)
        }

    }
}