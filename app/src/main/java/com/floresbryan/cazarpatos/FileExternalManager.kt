package com.floresbryan.cazarpatos

import android.app.Activity
import android.content.Context
import android.os.Environment
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.lang.Exception

class FileExternalManager (val actividad: Activity): FileHandler{
    fun isExternalStorageWritable(): Boolean {
        return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
    }

    fun isExternalStorageReadable(): Boolean {
        return Environment.getExternalStorageState() in
                setOf(Environment.MEDIA_MOUNTED, Environment.MEDIA_MOUNTED_READ_ONLY)
    }

    override fun SaveInformation(datosAGrabar:Pair<String,String>){
        if (isExternalStorageWritable()) {
            FileOutputStream(
                File(
                    actividad.getExternalFilesDir(null),
                    SHAREDINFO_FILENAME
                )
            ).bufferedWriter().use { outputStream ->
                outputStream.write(LOGIN_KEY)
                outputStream.write(System.lineSeparator())
                outputStream.write(PASSWORD_KEY)
                }
        }
    }
    override fun ReadInformation():Pair<String,String>{
        var email = ""
        var clave = ""
        try {
            if (isExternalStorageReadable()) {
                FileInputStream(
                    File(
                        actividad.getExternalFilesDir(null),
                        SHAREDINFO_FILENAME
                    )
                ).bufferedReader().use {
                    val datoLeido = it.readText()
                    val textArray = datoLeido.split(System.lineSeparator())
                    email = textArray[0]
                    clave = textArray[1]
                    println(textArray)

                }

            }
            return (email to clave)
        }
        catch (e:Exception){
            return "" to ""
        }
    }
}