package com.cc.foodorderingappproject.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cc.foodorderingappproject.entitiy.Foods
import com.cc.foodorderingappproject.entitiy.SepettekiYemeklerDatas
import com.cc.foodorderingappproject.repo.SepetYemeklerRepository
import com.cc.foodorderingappproject.repo.YemeklerRepository

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.math.truncate

@HiltViewModel
class HomePageViewModel @Inject constructor(var yrepo: YemeklerRepository,
                                            var syrepo: SepetYemeklerRepository
) : ViewModel() {

    var yemeklerListesi = MutableLiveData<List<Foods>>()
    var sepetListesi = MutableLiveData<List<SepettekiYemeklerDatas>>()

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        loadFoods()
    }


    fun loadFoods() {
        CoroutineScope(Dispatchers.Main).launch {
            _isLoading.value= true
            yemeklerListesi.value = yrepo.yemekleriYukle()
            _isLoading.value = false

        }
    }
    fun sepeteEkle(yemek_adi:String,yemek_resim_adi:String,yemek_fiyat:Int,yemek_siparis_adet:Int,kullanici_adi:String){
        CoroutineScope(Dispatchers.Main).launch {
            syrepo.sepeteEkle(yemek_adi,yemek_resim_adi,yemek_fiyat,yemek_siparis_adet,kullanici_adi)
        }
    }
    fun sepettenSil(sepet_yemek_id:Int,kullanici_adi: String){
        CoroutineScope(Dispatchers.Main).launch{
            syrepo.sepettenSil(sepet_yemek_id,kullanici_adi)
        }
    }

    fun sepetYemekleriYukle(kullanici_adi:String) {

        CoroutineScope(Dispatchers.Main).launch {
            try {
                // API'den yemekleri al
                val response = withContext(Dispatchers.IO) {
                    syrepo.sepettekiYemekleriGetir(kullanici_adi)
                }

                // Eğer cevap boşsa veya geçersizse, bir hata durumunu işleyin
                if (response.isNullOrEmpty()) {
                    sepetListesi.value = emptyList() // Boş liste olarak ayarla
                } else {
                    sepetListesi.value = response // Veriyi ayarla
                }
            } catch (e: Exception) {
                // Hata durumunu işleyin
                Log.e("CartError", "Error loading cart items: ${e.message}")
                sepetListesi.value = emptyList() // Boş liste olarak ayarla
            }
        }


    }

    }