package com.cc.foodorderingappproject.entitiy

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Foods(
    @SerializedName("yemek_id")
    @Expose
    var food_id: Int,
    @SerializedName("yemek_adi")
    @Expose
    var food_name: String,
    @SerializedName("yemek_resim_adi")
    @Expose
    var food_image_name: String,
    @SerializedName("yemek_fiyat")
    @Expose
    var food_price: Int,

)