package com.carvio.workmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.work.*
import java.util.concurrent.TimeUnit

class WorkManagerActivity : AppCompatActivity() {

    lateinit var buttonStart : Button
    lateinit var textView : TextView
    lateinit var buttonStop : Button

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_work_manager)

        val data = Data.Builder().putInt("data",1).build() // Veri olustur.

        val constraints : Constraints = Constraints.Builder() // Kisitlari olustur.
            .setRequiredNetworkType(NetworkType.CONNECTED) // Aga bagli olmasi gerekli.
            .setRequiresCharging(false) // Sarjinin full olmasi gerekli degil.
            .build() // Olustur.

        /* ---
        // Bir defa calistir.
        val workRequest : WorkRequest = OneTimeWorkRequestBuilder<RefreshManager>()
            .setConstraints(constraints) // Kisitlari ekle.
            .setInputData(data) // Veriyi yolla.
            .setInitialDelay(100, TimeUnit.MILLISECONDS) // Gecikmeli baslat.
            .addTag("oneTimeRequest") // Tanimlayici ekle.
            .build() // Olustur.

        WorkManager.getInstance(this).enqueue(workRequest) // Calistir.
        WorkManager.getInstance(this).cancelAllWork() // Hepsini durdur.
        WorkManager.getInstance(this).getWorkInfosByTag("oneTimeRequest") // TAG'a gore calistir.
        WorkManager.getInstance(this).cancelAllWorkByTag("oneTimeRequest") // TAG'a gore durdur.
        --- */

        /* ---
        // Belli periyotlar ile calistir.
        val periodicWorkManager : PeriodicWorkRequest = PeriodicWorkRequestBuilder<RefreshManager>(15,TimeUnit.MINUTES)
            .setConstraints(constraints) // Kisitlari ekle.
            .setInputData(data) // Veriyi yolla.
            .build() // Olustur.

        WorkManager.getInstance(this).enqueue(periodicWorkManager) // Calistir.
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(periodicWorkManager.id).observe(this,
            Observer {
                if(it.state == WorkInfo.State.RUNNING){
                    println("RUNNING WORK MANAGER.")
                } else if(it.state == WorkInfo.State.SUCCEEDED){
                    println("RUNNING WORK SUCCEEDED.")
                }
            }) // ID'ye gore calistir ve gozlemle.
        WorkManager.getInstance(this).cancelWorkById(periodicWorkManager.id)
        --- */

        /* ---
        // Zincirlenmis gorevler.
        val oneTimeWorkRequest :OneTimeWorkRequest = OneTimeWorkRequestBuilder<RefreshManager>()
            .setConstraints(constraints) // Kisitlari ekle.
            .setInputData(data) // Veriyi yolla.
            .build() // Olustur.

        WorkManager.getInstance(this).beginWith(oneTimeWorkRequest) // Calistir.
            .then(oneTimeWorkRequest) // Sonra calistir...
            .then(oneTimeWorkRequest) // Sonra calistir...
            .enqueue()

        --- */
    }
}