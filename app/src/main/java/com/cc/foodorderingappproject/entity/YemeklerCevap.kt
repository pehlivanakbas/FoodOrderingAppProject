package com.cc.foodorderingappproject.entitiy

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
/*
data class YemeklerCevap(
    @SerializedName("yemekler")
    @Expose
    var yemekler: List<Foods>,
    @SerializedName("success")
    @Expose
    var success: Int
) {

}

 */
data class YemeklerCevap(var yemekler: List<Foods>, var success: String)
