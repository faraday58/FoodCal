package com.mexiti.foodcal.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.mexiti.foodcal.model.DailyFood
import kotlinx.coroutines.flow.MutableStateFlow

class DailyFoodViewModel: ViewModel() {

    private val firestore = Firebase.firestore

    //Creando objeto con las valores iniciales
    private val _dataDailyFood = MutableStateFlow<List<DailyFood>>(emptyList())

    val dataDailyFood = _dataDailyFood

    var state by mutableStateOf(DailyFood())
        private set

    fun fetchDaysFood(){
        firestore.collection("DayFood")
            .orderBy("day")
            .addSnapshotListener { querysnapshot, error ->
                if(error != null ){
                    return@addSnapshotListener
                }
               val documents = mutableListOf<DailyFood>()
                if( querysnapshot != null){

                    for(document in querysnapshot){
                        val myDocument = document.toObject(DailyFood::class.java)
                            .copy(idDoc = document.id)
                        documents.add(myDocument)
                    }


                }
                _dataDailyFood.value  = documents

            }

    }


}