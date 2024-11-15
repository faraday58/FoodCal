package com.mexiti.foodcal.network

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

data class Thumb(
    val url: String?
)

data class UploadedImageData(
    val url: String?,
    val thumb: Thumb?
)

data class UploadImageResponse(
    val data : UploadedImageData?,
    val success: Boolean,
    val status: Int
)




interface  ImgBBApiService{
    @FormUrlEncoded
    @POST("1/upload")
    fun uploadImage(
        @Field("key") apiKey :String,
        @Field("image") base64Image: String
    ): Call<UploadImageResponse>

}