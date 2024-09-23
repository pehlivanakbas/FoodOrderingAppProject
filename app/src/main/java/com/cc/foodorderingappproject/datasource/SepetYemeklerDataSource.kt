package com.cc.foodorderingappproject.datasource

import com.cc.foodorderingappproject.entitiy.SepettekiYemeklerDatas
import com.cc.foodorderingappproject.retrofit.SepetYemeklerDaoInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SepetYemeklerDataSource(var sydao: SepetYemeklerDaoInterface) {
//Ds//
    suspend fun sepeteEkle(yemek_adi:String,
                           yemek_resim_adi:String,
                           yemek_fiyat:Int,
                           yemek_siparis_adet:Int,
                           kullanici_adi:String){



        sydao.sepeteYemekEkle(yemek_adi,yemek_resim_adi,yemek_fiyat,yemek_siparis_adet,kullanici_adi)
    }

    suspend fun sepettekiYemekleriGetir(kullanici_adi:String) :List<SepettekiYemeklerDatas> = withContext(
        Dispatchers.IO)
    {
        return@withContext sydao.sepettekiYemekleriGetir(kullanici_adi).sepet_yemekler
    }


    suspend fun sepettenSil(sepet_yemek_id:Int,kullanici_adi:String){
        sydao.sepettenYemekSil(sepet_yemek_id,kullanici_adi)

    }


}