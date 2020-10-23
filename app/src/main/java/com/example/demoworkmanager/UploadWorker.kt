package com.example.demoworkmanager

import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class UploadWorker(context: Context, parameters: WorkerParameters): Worker(context,parameters) {
    companion object{
        const val KEY_WORKER ="keywwork"
    }
    override fun doWork(): Result {
        return try {
            val count = inputData.getInt(MainActivity.KEY_COUNT_VALUE,0)
            for (i in 0..count) {
                Log.d("mytag","Uploading $i")
            }
            val time = SimpleDateFormat("dd/MM/yyyy hh:mm:ss")
            val currentDate = time.format(Date())
            val outputData = Data.Builder()
                .putString(KEY_WORKER,currentDate)
                .build()

            Result.success(outputData)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure()
        }

    }

}