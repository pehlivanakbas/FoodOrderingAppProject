package com.cc.foodorderingappproject.retrofit
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {
        companion object {
            //static
            //retrofit ile ilgili ayarları yapıyoruz
            fun getClient(baseUrl: String): Retrofit {
                return Retrofit.Builder().baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
        }
    }

