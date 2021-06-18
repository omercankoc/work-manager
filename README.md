## Work Manager
The WorkManager API is a suitable and recommended replacement for all previous Android background scheduling APIs, including FirebaseJobDispatcher, GcmNetworkManager, and Job Scheduler. WorkManager incorporates the features of its predecessors in a modern, consistent API that works back to API level 14 while also being conscious of battery life.

## Features
In addition to providing a simpler and consistent API, WorkManager has a number of other key benefits, including:

### Work Constraints

Declaratively define the optimal conditions for your work to run using Work Constraints. (For example, run only when the device is Wi-Fi, when the device idle, or when it has sufficient storage space, etc.)

### Robust Scheduling

WorkManager allows you to schedule work to run one- time or repeatedly using flexible scheduling windows. Work can be tagged and named as well, allowing you to schedule unique, replaceable work and monitor or cancel groups of work together. Scheduled work is stored in an internally managed SQLite database and WorkManager takes care of ensuring that this work persists and is rescheduled across device reboots. In addition, WorkManager adheres to power-saving features and best practices like Doze mode, so you don’t have to worry about it.

### Flexible Retry Policy

Sometimes work fails. WorkManager offers flexible retry policies, including a configurable exponential backoff policy.

### Work Chaining

For complex related work, chain individual work tasks together using a fluent, natural, interface that allows you to control which pieces run sequentially and which run in parallel.

## Sample
### Dependencies:
```gradle
dependencies {
    def work_version = "2.5.0"    
    implementation "androidx.work:work-runtime-ktx:$work_version"
}
```
### Work Manager Class:
A simple Work Manager that get data, increments it, and saves it with Shared Preferences.
```kotlin
class RefreshManager(val context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    // Get Data.
    override fun doWork(): Result {

        val getData = inputData
        val received = getData.getInt("data",0)
        refresh(received)
        return Result.success()
    }

    // Increments it, and saves it with Shared Preferences.
    private fun refresh(data : Int){
        val sharedPreferences = context.getSharedPreferences("com.carvio.workmanager",Context.MODE_PRIVATE)
        var savedData = sharedPreferences.getInt("savedData",0)
        savedData += data
        println("savedData : $savedData")
        sharedPreferences.edit().putInt("savedData",savedData).apply()
    }
}
```
### Data Builder:
```kotlin
val data = Data.Builder().putInt("data",1).build() // Veri olustur.
```
### Create Constraints:
```kotlin
val constraints : Constraints = Constraints.Builder() // Build Constraints.
    .setRequiredNetworkType(NetworkType.CONNECTED) // Must be connected to the network.
    .setRequiresCharging(false) // The charge does not need to be full.
    .build() // Build.
```
### Work Request:
it only works once.
```kotlin
val workRequest : WorkRequest = OneTimeWorkRequestBuilder<RefreshManager>()
    .setConstraints(constraints) // Add constraints.
    .setInputData(data) // Send data.
    .setInitialDelay(100, TimeUnit.MILLISECONDS) // Delayed start.
    .addTag("oneTimeRequest") // Add identifier.
    .build() // Build.
```
### Periodic Work Request:
Works with specified periods.
```kotlin
val periodicWorkManager : PeriodicWorkRequest = PeriodicWorkRequestBuilder<RefreshManager>(15,TimeUnit.MINUTES) // Assign period.
    .setConstraints(constraints) // Add constraints.
    .setInputData(data) // Send data.
    .build() // Build.
```
### Chaining Work Manager:
Run multiple work managers in turn.
```kotlin
val oneTimeWorkRequest :OneTimeWorkRequest = OneTimeWorkRequestBuilder<RefreshManager>()
    .setConstraints(constraints) // Add constraints.
    .setInputData(data) // Send data.
    .build() // Build.

WorkManager.getInstance(this).beginWith(oneTimeWorkRequest) // RUN...
    .then(oneTimeWorkRequest) // RUN LATER...
    .then(oneTimeWorkRequest) // RUN LATER...
    .enqueue()
```
### Run Work Manager:
1. Run:
```kotlin
WorkManager.getInstance(this).enqueue(workRequest)
```
2. Run by tag info:
```kotlin
WorkManager.getInstance(this).getWorkInfosByTag("oneTimeRequest")
```
3. Run by ID:
```kotlin
WorkManager.getInstance(this).getWorkInfoByIdLiveData(periodicWorkManager.id)
```
### Stop Work Manager:
1. Stop:
```kotlin
WorkManager.getInstance(this).cancelAllWork()
```
2. Stop by tag info:
```kotlin
WorkManager.getInstance(this).cancelAllWorkByTag("oneTimeRequest")
```
3. Stop by ID:
```kotlin
WorkManager.getInstance(this).cancelWorkById(periodicWorkManager.id)
```
### Observe:
Observes the employed work manager and provides conditional control.
```kotlin
WorkManager.getInstance(this).getWorkInfoByIdLiveData(periodicWorkManager.id).observe(this,
    Observer {
        if(it.state == WorkInfo.State.RUNNING){
            println("RUNNING WORK MANAGER.")
        } else if(it.state == WorkInfo.State.SUCCEEDED){
            println("RUNNING WORK SUCCEEDED.")
        }
    }
)
```
