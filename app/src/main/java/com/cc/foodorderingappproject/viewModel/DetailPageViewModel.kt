package com.cc.foodorderingappproject.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cc.foodorderingappproject.entitiy.Foods
import com.cc.foodorderingappproject.repo.SepetYemeklerRepository
import com.cc.foodorderingappproject.repo.YemeklerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class DetailPageViewModel @Inject constructor
    (var yrepo: YemeklerRepository,
     var sepetRepo: SepetYemeklerRepository
            ) : ViewModel() {
    var yemeklerListesi = MutableLiveData<List<Foods>>()
    init {
yemekleriYukle()
    }
    fun yemekleriYukle(){
        CoroutineScope(Dispatchers.Main).launch {
            yemeklerListesi.value = yrepo.yemekleriYukle()
        }
    }
    fun sepeteEkle(yemek_adi: String, yemek_resim_adi:String,yemek_fiyat: Int,yemek_siparis_adet:Int,kullanici_adi:String) {
        CoroutineScope(Dispatchers.Main).launch {
            sepetRepo.sepeteEkle(yemek_adi,yemek_resim_adi,yemek_fiyat,yemek_siparis_adet,kullanici_adi)
        }}


    }
