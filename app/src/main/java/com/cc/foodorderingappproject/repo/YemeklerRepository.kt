package com.cc.foodorderingappproject.repo

import com.cc.foodorderingappproject.datasource.YemeklerDataSource
import com.cc.foodorderingappproject.entitiy.Foods


class YemeklerRepository(var yds: YemeklerDataSource) {
    suspend fun yemekleriYukle(): List<Foods> = yds.yemekleriYukle()

   /*
    suspend fun sepeteEkle(
    yemekleriYukle
        yemek_adi: String,
        yemek_resim_adi: String,
        yemek_fiyat: Int,
        yemek_siparis_adet: Int,
        kullanici_adi: String
    ) = yds.sepeteEkle(yemek_adi, yemek_resim_adi, yemek_fiyat, yemek_siparis_adet, kullanici_adi)

    suspend fun sepettekiYemekleriGetir(
        kullanici_adi: String
    )=yds.sepettekiYemekleriGetir(kullanici_adi)

    */
}