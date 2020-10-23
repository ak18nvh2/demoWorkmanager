package com.example.demoworkmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.work.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    companion object {
        const val KEY_COUNT_VALUE = "keycountvalue"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnStart.setOnClickListener {
            setOnTimeWorkRequest()
        }
    }

    private fun setOnTimeWorkRequest() {
        val workManager = WorkManager.getInstance(applicationContext)

        val data: Data = Data.Builder()
            .putInt(KEY_COUNT_VALUE, 50)
            .build()
        val constraints = Constraints.Builder()
            .setRequiresCharging(true)
            //.setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val upLoadRequest = OneTimeWorkRequest.Builder(UploadWorker::class.java)
            .setConstraints(constraints)
            .setInputData(data)
            .build()

        val filteringRequest = OneTimeWorkRequest.Builder(FillteringWorker::class.java)
            .build()
        val compressingRequest = OneTimeWorkRequest.Builder(CompressingWorker::class.java)
            .build()
        val downloadingRequest = OneTimeWorkRequest.Builder(DownloadingWorker::class.java)
            .build()
        val paralleWorks: MutableList<OneTimeWorkRequest> = mutableListOf()
        paralleWorks.add(filteringRequest)
        paralleWorks.add(downloadingRequest)
        paralleWorks.add(compressingRequest)

        workManager
            .beginWith(paralleWorks)
            .then(upLoadRequest)
            .enqueue()
        workManager.getWorkInfoByIdLiveData(upLoadRequest.id)
            .observe(this, Observer {
                textView.text = it.state.name
                if (it.state.isFinished) {
                    val data = it.outputData
                    val mess = data.getString(UploadWorker.KEY_WORKER)
                    Log.d("mytag", mess!!)
                }
            })
    }
}