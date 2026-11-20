package com.mexiti.foodcal.network

import com.google.gson.annotations.SerializedName
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Query

data class ImgBbResponse(
    @SerializedName("data") val data: ImgBbData?,
    @SerializedName("success") val success: Boolean,
    @SerializedName("status") val status: Int
)

data class ImgBbData(
    @SerializedName("url") val url: String?,
    @SerializedName("display_url") val displayUrl: String?
)

interface ImgBbApiService {
    @FormUrlEncoded
    @POST("1/upload")
    suspend fun uploadImageBase64(
        @Query("key") apiKey: String,
        @Field("image") base64Image: String
    ): ImgBbResponse
}

object ImgBbClient {
    private const val BASE_URL = "https://api.imgbb.com/"

    val apiService: ImgBbApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ImgBbApiService::class.java)
    }
}
