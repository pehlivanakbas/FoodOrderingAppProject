package com.cc.foodorderingappproject.retrofit


class ApiUtils {
    companion object {
        val BASE_URL = "http://kasimadalan.pe.hu/"
        //her bir interface için 1 fun oluşturacagız
        //hangi interface ye erişeceksek onu yazacagız

        fun getYemeklerInterface(): YemeklerDaoInterface {
            try {
                return RetrofitClient.getClient(BASE_URL).create(YemeklerDaoInterface::class.java)
            } catch (e: Exception) {
                throw e
            }
        }
    }
}
//Base url burada