package com.carvio.workmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.work.*
import java.util.concurrent.TimeUnit

class WorkManagerActivity : AppCompatActivity() {

    lateinit var buttonStart : Button
    lateinit var textView : TextView
    lateinit var buttonStop : Button

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_work_manager)

        // Get!
        val unit = Data.Builder().putInt("unit",1).build()

        val constraints : Constraints = Constraints.Builder()
            //.setRequiredNetworkType(NetworkType.CONNECTED) // aga bagli olmasi gerekli...
            //.setRequiresCharging(false) // sarjinin full olmasi gerekli degil...
            .build()

        /*
        val workRequest : WorkRequest = OneTimeWorkRequestBuilder<CounterManager>()
            .setConstraints(constraints)
            .setInputData(unit)
            //.setInitialDelay(5,TimeUnit.MILLISECONDS)
            .build()

        WorkManager.getInstance(this).enqueue(workRequest) */

        /*
        val periodicWorkManager : PeriodicWorkRequest = PeriodicWorkRequestBuilder<CounterManager>(15,TimeUnit.MINUTES)
            .setConstraints(constraints)
            .setInputData(unit)
            .build()

        WorkManager.getInstance(this).enqueue(periodicWorkManager) */

        // Chaining work Manager
        val oneTimeWorkRequest :OneTimeWorkRequest = OneTimeWorkRequestBuilder<CounterManager>()
            .setConstraints(constraints)
            .setInputData(unit)
            .build()

        WorkManager.getInstance(this).beginWith(oneTimeWorkRequest)
            .then(oneTimeWorkRequest)
            .then(oneTimeWorkRequest)
            .enqueue()

        buttonStart = findViewById(R.id.buttonStart)
        textView = findViewById(R.id.textView)
        buttonStop = findViewById(R.id.buttonStop)

        buttonStart.setOnClickListener {

        }

        buttonStop.setOnClickListener {

        }
    }
}