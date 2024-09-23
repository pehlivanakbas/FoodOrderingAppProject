package com.cc.foodorderingappproject.retrofit

class ApiUtilsSepet {
    companion object {
        val BASE_URL = "http://kasimadalan.pe.hu/"



        fun getSepetYemeklerDao() : SepetYemeklerDaoInterface {
            try {
                return RetrofitClient.getClient(BASE_URL).create(SepetYemeklerDaoInterface::class.java)
            }catch (e: Exception) {
                throw e
            }

        }
    }
}
