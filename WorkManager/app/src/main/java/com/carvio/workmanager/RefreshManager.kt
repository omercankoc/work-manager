package com.carvio.workmanager

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class RefreshManager(val context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    // Veriyi al.
    override fun doWork(): Result {

        val getData = inputData
        val received = getData.getInt("data",0)
        refresh(received)
        return Result.success()
    }

    // Arttir ve kaydet.
    private fun refresh(data : Int){
        val sharedPreferences = context.getSharedPreferences("com.carvio.workmanager",Context.MODE_PRIVATE)
        var savedData = sharedPreferences.getInt("savedData",0)
        savedData += data
        println("savedData : $savedData")
        sharedPreferences.edit().putInt("savedData",savedData).apply()
    }
}