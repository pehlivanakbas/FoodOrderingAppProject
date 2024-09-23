package com.cc.foodorderingappproject.Screens.AppComponents

import android.media.MediaPlayer
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.cc.foodorderingappproject.R
import com.cc.foodorderingappproject.entitiy.Foods
import com.cc.foodorderingappproject.viewModel.DetailPageViewModel
import com.skydoves.landscapist.glide.GlideImage

import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailContents(
    yemek: Foods,
    navController: NavController,
    detailViewModel: DetailPageViewModel
) {
    var quantity by remember { mutableStateOf(1) }  // Manage the quantity state
    val kullanici_adi = "Pehlivan Akbaş"
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    val context = LocalContext.current

    val addedToCartMessage = "Sepete Eklendi"

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.app_name), fontFamily = FontFamily.Default) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "geri",
                            tint = Color.White
                        )
                    }
                },

                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Green,
                    titleContentColor = Color.White
                )
            )

        }
    ) { paddingValues ->
        val animatedQuantity by animateFloatAsState(targetValue = quantity.toFloat())
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            GlideImage(
                imageModel = "http://kasimadalan.pe.hu/yemekler/resimler/${yemek.food_image_name}",
                modifier = Modifier.size(120.dp),
                contentDescription = ""
            )

            // Large food name
            Text(
                text = yemek.food_name,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center
            )

            // Large food price
            Text(
                text = "${stringResource(id = R.string.app_name)}: \$${yemek.food_price}",
                fontSize = 24.sp,
                color = Color.Gray,
                fontWeight = FontWeight.SemiBold
            )

            // Quantity selection
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = { if (quantity > 1) quantity -= 1 },
                    modifier = Modifier
                        .padding(4.dp)
                        .width(100.dp)
                ) {
                    Text(text = "-", fontSize = 24.sp,color = Color.White)
                }

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = animatedQuantity.toInt().toString()  ,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .width(60.dp),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.width(8.dp))


                Button(
                    onClick = {
                        quantity += 1 },
                    modifier = Modifier
                        .padding(4.dp)
                        .width(100.dp)
                ) {
                    Text(text = "+", fontSize = 24.sp, color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Order Button
            Button(
                onClick = {
                    detailViewModel.sepeteEkle(yemek.food_name,yemek.food_image_name,yemek.food_price,quantity,kullanici_adi)
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar("${quantity} ${yemek.food_name} $addedToCartMessage")
                    }

                },
                modifier = Modifier.fillMaxWidth().padding(20.dp)

                    .shadow(8.dp, RoundedCornerShape(16.dp))
            ) {
                Text(text = stringResource(id = R.string.app_name), fontSize = 18.sp, color = Color.White)
            }
        }
    }
}
