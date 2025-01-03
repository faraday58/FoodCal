package com.mexiti.foodcal.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mexiti.foodcal.ui.screens.DataFields
import com.mexiti.foodcal.ui.screens.FoodsApp
import com.mexiti.foodcal.viewmodel.DailyFoodViewModel

@Composable
fun NavManager(dailyFoodVM:DailyFoodViewModel){
    val navController = rememberNavController()

    NavHost(navController , startDestination = "FoodApp" ){
        composable("FoodApp"){
            FoodsApp(navController, dailyFoodVM )
        }

        composable("OwnReset") {
            DataFields()
        }
    }


}