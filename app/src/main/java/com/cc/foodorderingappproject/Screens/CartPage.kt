package com.cc.foodorderingappproject.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.cc.foodorderingappproject.R
import com.cc.foodorderingappproject.entitiy.SepettekiYemeklerDatas
import com.cc.foodorderingappproject.viewModel.CartPageViewModel
import com.skydoves.landscapist.glide.GlideImage

import java.util.concurrent.TimeUnit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartPage(cartPageViewModel: CartPageViewModel,
             navController: NavController) {
    val sepetliste = cartPageViewModel.sepettekiyemeklerListesi.observeAsState(listOf())
    val kullanici_Adi = "Pehlivan Akbas"
    val yemekAdi = remember {
        mutableStateOf("")
    }
    val context = LocalContext.current


    LaunchedEffect(key1 = true) {
        cartPageViewModel.sepetYemekleriYukle(kullanici_Adi)
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Sepetim") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                actions =
                {
                    // Clear Cart Button with conditional enabled/disabled state
                    val isCartEmpty = sepetliste.value.isEmpty()

                    Button(
                        onClick = {
                            if (!isCartEmpty) {
                                cartPageViewModel.sepetiBosalt(kullanici_Adi)
                            }

                        },
                        enabled = !isCartEmpty,  // Button is disabled if the cart is empty
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isCartEmpty) Color.Gray else Color.DarkGray, // Gray if disabled, Red if enabled
                            contentColor = Color.White
                        ),
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Clear, // Clear icon
                            contentDescription = stringResource(id = R.string.clear_cart),
                            tint = if (isCartEmpty) Color.Gray else Color.White
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = stringResource(id = R.string.clear_cart), fontSize = 16.sp)
                    }

                },

                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = colorResource(id = R.color.white),
                    titleContentColor = colorResource(id = R.color.white),
                )
            )
        },
        bottomBar = {
            if (sepetliste.value.isNotEmpty()) {
                val totalAmount =
                    sepetliste.value.sumOf { it.yemek_fiyat * it.yemek_siparis_adet }
                val vat = (totalAmount * 0.18).toInt()
                val finalAmount = totalAmount + vat
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "${stringResource(id = R.string.total)}: \$${totalAmount}",
                            fontSize = 18.sp,
                            color = MaterialTheme.colorScheme.onSurface

                        )
                        Text(
                            text = "${stringResource(id = R.string.vat)}: \$${vat}",
                            fontSize = 18.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "${stringResource(id = R.string.final_amount)}: \$${finalAmount}",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    Button(
                        onClick = {
                          /*  val istek = OneTimeWorkRequestBuilder<MyWorker>().setInitialDelay(
                                15,
                                TimeUnit.SECONDS
                            ).build()
                            WorkManager.getInstance(context).enqueue(istek)


                           */
                        }
                    ) {
                        Text(
                            text = stringResource(id = R.string.order_now),
                            fontSize = 18.sp,
                            color = Color.White
                        )
                    }

                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(MaterialTheme.colorScheme.background)
            ) {
                if (sepetliste.value.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(id = R.string.cart_empty),
                            fontSize = 24.sp,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.weight(1f)
                    ) {
                        val groupedYemekler = sepetliste.value.groupBy { it.yemek_adi }
                        items(groupedYemekler.entries.toList()) { entry ->
                            val yemekAdi = entry.key
                            val yemekList = entry.value
                            val toplamAdet = yemekList.sumOf { it.yemek_siparis_adet }
                            val yemek = yemekList.first()

                            CartComponent(yemek = yemek.copy(yemek_siparis_adet = toplamAdet)) {
                                cartPageViewModel.sepettenTekTekSil(
                                    kullanici_adi = kullanici_Adi,
                                    sepet_yemek_id = yemek.sepet_yemek_id
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

    @Composable
    fun CartComponent(yemek: SepettekiYemeklerDatas, sepetYemeklerDeleteClick: () -> Unit) {

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.onPrimaryContainer,
                contentColor = MaterialTheme.colorScheme.onSurface
            ),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                GlideImage(
                    imageModel = "http://kasimadalan.pe.hu/yemekler/resimler/${yemek.yemek_resim_adi}",
                    modifier = Modifier.size(120.dp),
                    contentDescription = ""
                )

                Column {
                    Text(text = yemek.yemek_adi, fontSize = 18.sp,color = MaterialTheme.colorScheme.onSurface)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Adet: ${yemek.yemek_siparis_adet}", fontSize = 18.sp,color = MaterialTheme.colorScheme.onSurface)
                }
                Text(text = "Fiyat: ${yemek.yemek_fiyat * yemek.yemek_siparis_adet}",color = MaterialTheme.colorScheme.onSurface)
                IconButton(onClick = sepetYemeklerDeleteClick

                ) {
                    Icon(imageVector = Icons.Default.Clear, contentDescription = "Delete Task", tint = Color.Gray)
                }
            }
        }
    }
