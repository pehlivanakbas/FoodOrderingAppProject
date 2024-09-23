package com.cc.foodorderingappproject.retrofit

import com.cc.foodorderingappproject.entitiy.YemeklerCevap
import retrofit2.http.GET

interface YemeklerDaoInterface {


    //http://kasimadalan.pe.hu/yemekler/tumYemekleriGetir.php

    @GET("yemekler/tumYemekleriGetir.php")
    suspend fun tumyemekleriYukle(): YemeklerCevap


}


