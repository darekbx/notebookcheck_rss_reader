package com.darekbx.notebookcheckreader.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.darekbx.notebookcheckreader.domain.SynchronizeUseCase
import com.darekbx.notebookcheckreader.repository.RssNotificationManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DataRefreshWorker(
    appContext: Context,
    workerParams: WorkerParameters,
    private val synchronizeUseCase: SynchronizeUseCase,
    private val notificationManager: RssNotificationManager
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            val newItemsCount = synchronizeUseCase()

            // TODO remove after tests
            notificationManager.showNotification(
                "Fetch completed",
                "Added $newItemsCount new items"
            )
            Result.success()
        } catch (e: Exception) {
            Log.e("CoroutineWorker", "Error in worker", e)
            notificationManager.showNotification(
                "Failed to fetch items",
                e.message ?: "Unknown error"
            )
            Result.failure()
        }
    }
}
