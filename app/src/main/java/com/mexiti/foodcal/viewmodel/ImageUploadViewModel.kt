package com.mexiti.foodcal.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mexiti.foodcal.auxiliar.encodeImageToBase64
import com.mexiti.foodcal.network.ImgBBApiService
import com.mexiti.foodcal.network.UploadImageResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File


class ImageUploadViewModel: ViewModel() {
    //Live data to observe result
    private val _uploadResult = MutableLiveData<String?>()
    val uploadResult:LiveData<String?> get() = _uploadResult

    private  val apiService:ImgBBApiService = createImgBBService()

    //Function to initialize Retrofit Service
    private fun createImgBBService():ImgBBApiService{
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.imgbb.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(ImgBBApiService::class.java)

    }

    //Function to upload the image
    fun uploadImage(apiKey:String, imageFile: File){
        val base64Image = encodeImageToBase64(imageFile)

        viewModelScope.launch (Dispatchers.IO){
            val call = apiService.uploadImage(apiKey,base64Image)
            call.enqueue(object :Callback<UploadImageResponse>{
                override fun onResponse(
                    call: Call<UploadImageResponse>,
                    response: Response<UploadImageResponse>
                ) {

                    if (response.isSuccessful && response.body()?.data != null){
                        // If is Successful, assign image's URL to LiveData
                        _uploadResult.postValue( response.body()?.data?.url)
                    }
                    else{
                        _uploadResult.postValue("Error: ${response.errorBody()?.string()}")
                    }

                }

                override fun onFailure(call: Call<UploadImageResponse>, t: Throwable) {
                    _uploadResult.postValue("Failed to upload image: ${t.message}")

                }

            })


        }


    }



}