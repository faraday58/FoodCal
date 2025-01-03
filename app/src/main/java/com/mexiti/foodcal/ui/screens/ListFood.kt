package com.mexiti.foodcal.ui.screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.mexiti.foodcal.R
import com.mexiti.foodcal.viewmodel.DailyFoodViewModel


@Composable
fun FoodsApp(navController: NavHostController, dailyFoodVM:DailyFoodViewModel){
    LaunchedEffect(Unit) {
        dailyFoodVM.fetchDaysFood()
    }

    Scaffold(
        topBar = {
            FoodTopBar(navController)
        }
    ) {
        val dataFood by dailyFoodVM.dataDailyFood.collectAsState()

        LazyColumn(
            contentPadding = it
        ) {
            items(dataFood) {
                food -> CardDayInfo(dayFood = food)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodTopBar(navController: NavHostController){
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
            containerColor = MaterialTheme.colorScheme.onPrimaryContainer
        ),
        navigationIcon = {
            IconButton(onClick = {
                navController.navigate("OwnReset")
            },
                modifier = Modifier.size(50.dp)) {
                Icon(
                    painter = painterResource(id = R.drawable.icono_write_fork),
                    contentDescription ="Agregar receta",
                    modifier = Modifier.size(48.dp),
                    tint = MaterialTheme.colorScheme.onSecondary

                )
            }
        }
    )
}

/*
@Preview(showBackground = true)
@Composable
fun FoodAppPreview(){
    FoodAppTheme {
        Scaffold {
            innerPadding ->
            Box(modifier =
            Modifier.padding(innerPadding) ){
                FoodTopBar()
            }
        }
    }
}
*/