package com.mexiti.foodcal.ui.screens

import android.net.Uri
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityOptionsCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.mexiti.foodcal.R
import com.mexiti.foodcal.ui.theme.FoodAppTheme




@Composable
fun DataFields(){
    Scaffold {
        Column(
            modifier = Modifier.padding(it)
        ) {
            InputData(
                inputText = "Mi reseta",
                label = "Nombre de la reseta"
            )
            InputData(
                inputText = "Descripción de la reseta",
                label = "Descripción",
                maxLines = 10
            )
            OwnImage(
                imageUri = null,
                imageBitmap = null,
                launcher = FakeLauncher
            )
            ButtonAdd()
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DataTopBar(navController: NavHostController){
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
                navController.popBackStack()
            },
                modifier = Modifier.size(50.dp)) {
                Icon(
                    painter = painterResource(id = R.drawable.arrow_back),
                    contentDescription ="Agregar receta",
                    modifier = Modifier.size(48.dp),
                    tint = MaterialTheme.colorScheme.onSecondary

                )
            }
        }
    )
}



@Composable
fun InputData(
    modifier: Modifier = Modifier,
    inputText :String="Mi Reseta",
    label: String = "Nombre de la reseta",
    maxLines:Int = 2
){

    OutlinedTextField(
        value =inputText,
        singleLine = false,
        label ={ Text(
            text = label,
            style = MaterialTheme.typography.labelLarge
        ) }
        , onValueChange = {
        },
        maxLines = maxLines,
        modifier = modifier
            .fillMaxWidth()
            .padding(
                dimensionResource(
                    id = R.dimen.padding_component
                )
            )
        ,
        shape = RoundedCornerShape(
            dimensionResource(
                id = R.dimen.padding_component)
        ),
        colors = TextFieldDefaults.colors(

        )
    )
}



@Composable
fun ButtonAdd(){
    Button(
        onClick = { /*TODO*/ },
        elevation = ButtonDefaults.elevatedButtonElevation(
            defaultElevation = 20.dp,
            pressedElevation = 20.dp,
            disabledElevation = 4.dp,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        Text(text = stringResource(R.string.add_reset))
    }
}

@Composable
fun OwnImage(
    imageUri:Uri?,
    imageBitmap: ImageBitmap?,
    launcher: ActivityResultLauncher<String>
){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
            .padding(dimensionResource(id = R.dimen.padding_component)),
        contentAlignment = Alignment.Center
    ){
        if(imageBitmap != null){
            //Show selected image
            Image(
                bitmap = imageBitmap!!,
                contentDescription = stringResource(R.string.selected_image),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(395.dp)
                    .clickable {
                        launcher.launch("image/*")
                    }
            )
        }else{
            //Showing default image
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.BottomCenter
            ) {
                Image(painter = painterResource(id = R.drawable.taco),
                    contentDescription = stringResource(R.string.default_image),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .clickable {
                            launcher.launch("image/*")
                        },
                    alpha = 0.2F
                )
                Text(
                    text = stringResource(R.string.touch_image),
                    modifier = Modifier.padding(
                        dimensionResource(id = R.dimen.padding_component)
                    ),
                    style = MaterialTheme.typography.titleSmall
                )

            }
        }


    }

}

val FakeLauncher = object : ActivityResultLauncher<String>(){
    override fun launch(input: String?, options: ActivityOptionsCompat?) {
        TODO("Not yet implemented")
    }

    override fun unregister() {
        TODO("Not yet implemented")
    }

    override fun getContract(): ActivityResultContract<String, *> {
        TODO("Not yet implemented")
    }


}


@Preview(showBackground = true)
@Composable
fun OwnImagePreview(){
    OwnImage(
        imageUri =  null,
        imageBitmap = null,
        launcher = FakeLauncher)
}


@Preview(showBackground = true)
@Composable
fun DataTopBarPreview() {
    FoodAppTheme {
        val navController = rememberNavController()
        Scaffold { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                DataTopBar(navController)
            }

        }
    }
}


@Preview(showBackground = true)
@Composable
fun InputDataPreview(){
    FoodAppTheme {
        InputData()
    }
}

@Preview(showBackground = true)
@Composable
fun ButtonAdPreview(){
    FoodAppTheme {
        ButtonAdd()
    }

}


@Preview(showBackground = true)
@Composable
fun DataFieldsPreview(){
    FoodAppTheme {
        DataFields()
    }
}