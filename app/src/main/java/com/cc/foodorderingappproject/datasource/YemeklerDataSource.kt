package com.cc.foodorderingappproject.datasource
import com.cc.foodorderingappproject.entitiy.Foods
import com.cc.foodorderingappproject.retrofit.YemeklerDaoInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class YemeklerDataSource(var ydao: YemeklerDaoInterface) {


    suspend fun yemekleriYukle(): List<Foods> = withContext(Dispatchers.IO) {

        return@withContext ydao.tumyemekleriYukle().yemekler

    }
    /*

    suspend fun sepeteEkle(
        yemek_adi: String,
        yemek_resim_adi: String,
        yemek_fiyat: Int,
        yemek_siparis_adet: Int,
        kullanici_adi: String
    ) {
        ydao.sepeteYemekEkle(
            yemek_adi,
            yemek_resim_adi,
            yemek_fiyat,
            yemek_siparis_adet,
            kullanici_adi
        )

    }
    suspend fun sepettekiYemekleriGetir(
        kullanici_adi: String
    ){
        ydao.sepettekiYemekleriGetir(kullanici_adi)
    }


     */

}
