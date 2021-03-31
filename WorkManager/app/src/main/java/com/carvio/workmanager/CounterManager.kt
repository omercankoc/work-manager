package com.carvio.workmanager

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class CounterManager(val context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    override fun doWork(): Result {

        val iData = inputData
        val unit = iData.getInt("unit",0)
        counter(unit)
        return Result.success()
    }

    private fun counter(unit : Int){
        val sharedPreferences = context.getSharedPreferences("com.carvio.workmanager",Context.MODE_PRIVATE)
        var counter = sharedPreferences.getInt("counter",0)
        counter += unit
        println("Counter : $counter")
        sharedPreferences.edit().putInt("counter",counter).apply()
    }

}