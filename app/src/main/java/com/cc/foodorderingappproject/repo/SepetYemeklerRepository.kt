package com.cc.foodorderingappproject.repo

import com.cc.foodorderingappproject.datasource.SepetYemeklerDataSource
import com.cc.foodorderingappproject.entitiy.SepettekiYemeklerDatas


class SepetYemeklerRepository(var syds: SepetYemeklerDataSource) {

    suspend fun sepettenSil(sepet_yemek_id:Int,kullanici_adi:String) = syds.sepettenSil(sepet_yemek_id,kullanici_adi)

    suspend fun sepeteEkle(yemek_adi:String,
                           yemek_resim_adi:String,
                           yemek_fiyat:Int,
                           yemek_siparis_adet:Int,
                           kullanici_adi:String) = syds.sepeteEkle(yemek_adi,yemek_resim_adi,yemek_fiyat,yemek_siparis_adet,kullanici_adi)


    suspend fun sepettekiYemekleriGetir(kullanici_adi: String) :List<SepettekiYemeklerDatas> = syds.sepettekiYemekleriGetir(kullanici_adi)


}