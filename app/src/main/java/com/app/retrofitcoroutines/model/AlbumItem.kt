package com.app.retrofitcoroutines.model


import com.google.gson.annotations.SerializedName

data class AlbumItem(
    @SerializedName("id")
    var id: Int?,
    @SerializedName("title")
    var title: String?,
    @SerializedName("userId")
    var userId: Int?
)