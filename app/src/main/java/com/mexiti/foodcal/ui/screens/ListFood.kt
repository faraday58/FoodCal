package com.mexiti.foodcal.ui.screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.mexiti.foodcal.R
import com.mexiti.foodcal.viewmodel.DailyFoodViewModel

@Composable
fun FoodsApp(DailyFoodVM: DailyFoodViewModel) {
    var isAddingRecipe by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        DailyFoodVM.fetchDaysFood()
    }

    if (isAddingRecipe) {
        AddRecipeScreen(
            dailyFoodVM = DailyFoodVM,
            onBack = { isAddingRecipe = false }
        )
    } else {
        Scaffold(
            topBar = {
                FoodTopBar()
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { isAddingRecipe = true },
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Agregar receta"
                    )
                }
            }
        ) { paddingValues ->
            val dataFood by DailyFoodVM.dataDailyFood.collectAsState()
            LazyColumn(
                contentPadding = paddingValues
            ) {
                items(dataFood) { food -> CardDayInfo(dayFood = food) }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodTopBar() {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.app_name),
                style = MaterialTheme.typography.headlineMedium
            )
        },
        modifier = Modifier.fillMaxWidth(),
        colors = TopAppBarDefaults.topAppBarColors(
            titleContentColor = MaterialTheme.colorScheme.onSecondary,
            containerColor = MaterialTheme.colorScheme.onBackground
        )
    )
}
