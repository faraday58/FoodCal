package com.mexiti.foodcal.viewmodel

import android.content.Context
import android.net.Uri
import android.util.Base64
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.mexiti.foodcal.model.DailyFood
import com.mexiti.foodcal.network.ImgBbClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface AddRecipeState {
    object Idle : AddRecipeState
    object Loading : AddRecipeState
    object Success : AddRecipeState
    data class Error(val message: String) : AddRecipeState
}

class DailyFoodViewModel : ViewModel() {

    private val firestore = Firebase.firestore

    private val _dataDailyFood = MutableStateFlow<List<DailyFood>>(emptyList())
    val dataDailyFood = _dataDailyFood.asStateFlow()

    var addState by mutableStateOf<AddRecipeState>(AddRecipeState.Idle)
        private set

    fun resetAddState() {
        addState = AddRecipeState.Idle
    }

    fun fetchDaysFood() {
        firestore.collection("DayFood")
            .orderBy("day")
            .addSnapshotListener { querysnapshot, error ->
                if (error != null) {
                    return@addSnapshotListener
                }
                val documents = mutableListOf<DailyFood>()
                if (querysnapshot != null) {
                    for (document in querysnapshot) {
                        val myDocument = document.toObject(DailyFood::class.java)
                            .copy(idDoc = document.id)
                        documents.add(myDocument)
                    }
                }
                _dataDailyFood.value = documents
            }
    }

    fun addRecipe(
        context: Context,
        apiKey: String,
        day: Int,
        title: String,
        description: String,
        imageUri: Uri?
    ) {
        if (day <= 0 || title.isBlank() || description.isBlank()) {
            addState = AddRecipeState.Error("Por favor completa todos los campos requeridos.")
            return
        }

        if (imageUri == null) {
            addState = AddRecipeState.Error("Por favor selecciona una imagen.")
            return
        }

        if (apiKey.isBlank()) {
            addState = AddRecipeState.Error("Por favor ingresa tu API Key de ImgBB.")
            return
        }

        addState = AddRecipeState.Loading

        viewModelScope.launch {
            try {
                val inputStream = context.contentResolver.openInputStream(imageUri)
                val bytes = inputStream?.readBytes()
                inputStream?.close()

                if (bytes == null) {
                    addState = AddRecipeState.Error("No se pudo leer la imagen seleccionada.")
                    return@launch
                }

                val base64Image = Base64.encodeToString(bytes, Base64.NO_WRAP)
                val response = ImgBbClient.apiService.uploadImageBase64(apiKey, base64Image)

                val imageUrl = response.data?.url ?: response.data?.displayUrl

                if (response.success && !imageUrl.isNullOrBlank()) {
                    val newDailyFood = DailyFood(
                        day = day,
                        title = title,
                        description = description,
                        imageRes = imageUrl
                    )

                    firestore.collection("DayFood")
                        .add(newDailyFood)
                        .addOnSuccessListener {
                            addState = AddRecipeState.Success
                        }
                        .addOnFailureListener { e ->
                            addState = AddRecipeState.Error("Error al guardar en Firebase: ${e.localizedMessage}")
                        }
                } else {
                    addState = AddRecipeState.Error("Error al subir imagen a ImgBB. Verifica tu API Key.")
                }
            } catch (e: Exception) {
                addState = AddRecipeState.Error("Excepción: ${e.localizedMessage}")
            }
        }
    }
}
