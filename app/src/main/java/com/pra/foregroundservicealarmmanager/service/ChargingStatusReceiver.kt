package com.pra.foregroundservicealarmmanager.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import java.io.*
import java.text.SimpleDateFormat
import java.util.*

class ChargingStatusReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        context?.WriteSavedDataIntoFile(getCurrentDateTime().createCSVDeviceLog())
        for (i in 1..500) {
            Log.d(
                "Broadcast Called =$i ",
                "$i = SERVICE CALLED @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
            )
        }
    }

    private fun String.createCSVDeviceLog(): String {
        return "$this\n"
    }


    private  fun getCurrentDateTime(): String {
        return SimpleDateFormat("dd-MM-yy HH:mm:ss").format(Date())
    }

    private fun Context.WriteSavedDataIntoFile(Value: String) {
        try {
            var path: String
            path = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                this.externalMediaDirs[0].toString()
            } else {
                this.getExternalFilesDir(null).toString()
            }
            println("Path=====> $path")
            var file = File(path)
            if (!file.exists()) {
                file.mkdirs()
            }
            println("File Path ==> " + file.absolutePath)
            println("File Path ==> " + file.path)

            path = "$path//TransactionLog.csv";

            file = File(path)
            if (!file.exists()) {
                file.createNewFile()
            }
            println("File with Path ==> " + file.absolutePath)
            println("File with Path ==> " + file.path)
            val data: String = file.readFileAsString()
            try {
                val fileOutputStream = FileOutputStream(file, true)
                val writer = OutputStreamWriter(fileOutputStream)
                if (data == null || data == "") {
                    writer.append(Value)
                } else {
                    writer.append(Value)
                }
                writer.close()
                fileOutputStream.close()
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        } catch (e1: Exception) {
            e1.printStackTrace()
        }
    }

    fun File.readFileAsString(): String {
        val stringBuilder = StringBuilder()
        var line: String?
        var `in`: BufferedReader? = null
        try {
            `in` = BufferedReader(FileReader(this))
            while (`in`.readLine().also { line = it } != null) stringBuilder.append(line)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return stringBuilder.toString()
    }


}