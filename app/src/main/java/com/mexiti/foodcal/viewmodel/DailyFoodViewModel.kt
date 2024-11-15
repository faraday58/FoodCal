package com.mexiti.foodcal.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.mexiti.foodcal.model.DailyFood
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class DailyFoodViewModel:ViewModel() {
    private val firestore = Firebase.firestore

    //Creando variable con valores inciales
    private val _dataDailyFood = MutableStateFlow<List<DailyFood>>(emptyList())


    val dataDailyFood:StateFlow<List<DailyFood>> = _dataDailyFood

    var state by mutableStateOf(DailyFood())
        private set





    fun fetchDaysFood(){
        firestore.collection("DayFood")
            .addSnapshotListener {
                                 querySnapshot,
                                 error ->
                if( error != null){
                    return@addSnapshotListener
                }
                val documents = mutableListOf<DailyFood>()
                if( querySnapshot != null){

                    for( document in querySnapshot){
                        val myDocument = document.toObject(DailyFood::class.java)
                            .copy(idDoc = document.id)
                        documents.add(myDocument)
                    }
                }
                _dataDailyFood.value = documents
            }
    }
}
