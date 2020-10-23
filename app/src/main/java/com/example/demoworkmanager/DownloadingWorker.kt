package com.example.demoworkmanager

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.lang.Exception


class DownloadingWorker(context: Context, parameters: WorkerParameters): Worker(context,parameters) {
    companion object{
        const val KEY_WORKER ="keywwork"
    }
    override fun doWork(): Result {
        return try {

            for (i in 0..30) {
                Log.d("mytag","Downloading $i")
            }
            Result.success()
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure()
        }

    }

}