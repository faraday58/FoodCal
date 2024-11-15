package com.mexiti.foodcal.auxiliar

import android.util.Base64
import java.io.File

fun encodeImageToBase64(imageFile:File):String{
    val bytes = imageFile.readBytes()

    return Base64.encodeToString(bytes,Base64.DEFAULT)
}

