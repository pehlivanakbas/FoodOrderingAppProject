package com.cc.foodorderingappproject.Screens

import android.media.MediaPlayer
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.cc.foodorderingappproject.R
import com.cc.foodorderingappproject.Screens.AppComponents.AramaFiltreItem
import com.cc.foodorderingappproject.entitiy.Foods
import com.cc.foodorderingappproject.viewModel.HomePageViewModel
import com.google.gson.Gson
import com.skydoves.landscapist.glide.GlideImage

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Anasayfa(navController: NavController, anasayfaViewModel: HomePageViewModel) {

    val yemekListesi = anasayfaViewModel.yemeklerListesi.observeAsState(listOf())
    var searchQuery by remember { mutableStateOf("") }
    var isSearchActive by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val isLoading by anasayfaViewModel.isLoading.observeAsState(false)

    LaunchedEffect(key1 = true) {
        anasayfaViewModel.loadFoods()
    }


    Scaffold(topBar = {
        TopAppBar(title = {
            if (isSearchActive) {
                TextField(value = searchQuery,
                    onValueChange = { query ->
                        searchQuery = query
                    },
                    placeholder = { Text(text = "Aramak istediğiniz yemeği yazın.") },
                    singleLine = true,
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = MaterialTheme.colorScheme.background,
                        cursorColor = MaterialTheme.colorScheme.onBackground,
                        focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                        unfocusedIndicatorColor = MaterialTheme.colorScheme.onBackground.copy(
                            alpha = 0.5f
                        )
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            } else {
                Text(
                    text = stringResource(id = R.string.app_name),
                    fontFamily = FontFamily.SansSerif
                )
            }
        }, navigationIcon = {
            if (isSearchActive) {
                IconButton(onClick = {
                    isSearchActive = false
                    searchQuery = "" // Reset the search query when closing search
                }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back Icon",
                        tint = Color.White
                    )
                }
            }
        },
            actions = {
            if (!isSearchActive) {
                IconButton(onClick = { isSearchActive = true }) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon",
                        tint = Color.White
                    )
                }
            }
        },

            colors = TopAppBarDefaults.smallTopAppBarColors(
                containerColor = colorResource(id = R.color.white),
                titleContentColor = colorResource(id = R.color.maincolor),
            )
        )
    },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    // Navigate to the cart screen
                    navController.navigate("sepet")
                },

                containerColor = MaterialTheme.colorScheme.primary, contentColor = Color.White
            ) {
                Icon(Icons.Filled.ShoppingCart, contentDescription = "Go to Cart")
            }

        },
        floatingActionButtonPosition = FabPosition.End,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }) { PaddingValues ->


        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(PaddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            val filteredYemekler = if (searchQuery.isEmpty()) {
                listOf()

            } else {
                yemekListesi.value.filter {
                    it.food_name.contains(searchQuery, ignoreCase = true)

                }.take(3)
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues = PaddingValues)
            ) {
                if (isSearchActive && filteredYemekler.isNotEmpty()) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        filteredYemekler.forEach { yemek ->
                            AramaFiltreItem(yemek = yemek, onItemClick = {
                                navController.navigate("detay_sayfa/${yemek.food_id}")
                            })
                        }
                    }
                }
                YemekList(
                    navController,
                    yemekler = yemekListesi.value,
                    anasayfaViewModel,
                    snackbarHostState = snackbarHostState,
                    coroutineScope = coroutineScope
                )


            }


            /*
                        LazyVerticalGrid(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(1.dp),
                            columns = GridCells.Fixed(count = 3)
                        ) {
                            items(
                                count = yemekListesi.value.count(),
                                itemContent = {
                                    val yemek = yemekListesi.value!![it]
                                    Card(
                                        modifier = Modifier
                                            .padding(all = 5.dp)
                                            .fillMaxWidth()

                                    ) {
                                        Column(modifier = Modifier.clickable {
                                            //yemek nesnesini string türünde eklemek için Gson kullanırız.
                                            val yemekJson = Gson().toJson(yemek)
                                            navController.navigate("detay_sayfa/$yemekJson")
                                        }) {

                                            GlideImage(
                                                model = "http://kasimadalan.pe.hu/yemekler/resimler/${yemek.food_image_name}",
                                                modifier = Modifier.size(100.dp),
                                                contentDescription = ""
                                            )
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                modifier = Modifier.fillMaxWidth()
                                            ) {
                                                Column(
                                                    verticalArrangement = Arrangement.SpaceEvenly,
                                                    modifier = Modifier.fillMaxHeight()
                                                ) {
                                                    Text(text = yemek.food_name, fontSize = 20.sp)
                                                    Spacer(modifier = Modifier.size(30.dp))
                                                    Text(
                                                        text = "${yemek.food_price} ₺",
                                                        color = Color.Blue
                                                    )
                                                }
                                                Icon(
                                                    painter = painterResource(id = R.drawable.arrow_resim),
                                                    contentDescription = ""
                                                )
                                            }
                                        }
                                    }

                                }
                            )
                        }
                        */
        }

    }

}

@Composable
fun YemekList(
    navController: NavController,
    yemekler: List<Foods>,
    homeViewModel: HomePageViewModel,
    snackbarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(yemekler.size) { index ->
            YemekCard(
                navController = navController,
                yemek = yemekler[index],
                homeViewModel = homeViewModel,
                snackbarHostState = snackbarHostState,
                coroutineScope = coroutineScope
            )
        }
    }
}


@Composable
fun YemekCard(
    yemek: Foods,
    navController: NavController,
    homeViewModel: HomePageViewModel,
    snackbarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope
) { // Add a callback for button click
    val kullanici_adi = "Pehlivan Akbaş"

    var showSnackbar by remember { mutableStateOf(false) }
    val context = LocalContext.current


    val quantity = remember {
        mutableStateOf(1)
    }
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .height(260.dp)
            .clickable {
                navController.navigate("detay_sayfa/${yemek.food_id}")
            },  // Increased height to accommodate the button
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val url = "http://kasimadalan.pe.hu/yemekler/resimler/${yemek.food_image_name}"

            // Image

            GlideImage(
                imageModel = "http://kasimadalan.pe.hu/yemekler/resimler/${yemek.food_image_name}",
                modifier = Modifier.size(120.dp),
                contentDescription = ""
            )

            // Text
            Column(
                modifier = Modifier.weight(1f), verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = yemek.food_name,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "${(yemek.food_price)}",
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }

            // Order Button
            Button(
                onClick = {
                    homeViewModel.sepeteEkle(
                        yemek.food_name,
                        yemek.food_image_name,
                        yemek.food_price,
                        quantity.value,
                        kullanici_adi
                    )
                    coroutineScope.launch {
                        showSnackbar = true
                        delay(2000)
                        showSnackbar = false
                    }


                },  // Call the onOrderClick callback when button is pressed
                shape = RoundedCornerShape(12.dp), modifier = Modifier.fillMaxWidth(),

                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)// Button fills the width of the card
            ) {
                Text(text = "SEpet Ekle", fontSize = 16.sp, color = Color.White)
            }
        }
    }
    AnimatedVisibility(visible = showSnackbar) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            color = MaterialTheme.colorScheme.surface,
            shape = RoundedCornerShape(16.dp), // Köşeleri yuvarlayarak eliptik görünüm sağlar
            shadowElevation = 4.dp,// Gölge efekti için
        ) {
            Box(
                modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    text = ("${yemek.food_name} sepete eklendi"),
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 16.sp,

                    )
            }
        }
    }
}
