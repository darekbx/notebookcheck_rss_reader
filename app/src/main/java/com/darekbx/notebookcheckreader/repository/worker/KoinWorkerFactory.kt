package com.darekbx.notebookcheckreader.worker

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.darekbx.notebookcheckreader.domain.SynchronizeUseCase
import com.darekbx.notebookcheckreader.repository.RssNotificationManager

class KoinWorkerFactory(
    val synchronizeUseCase: SynchronizeUseCase,
    val notificationManager: RssNotificationManager
) : WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return when (workerClassName) {
            DataRefreshWorker::class.java.name -> {
                DataRefreshWorker(
                    appContext,
                    workerParameters,
                    synchronizeUseCase,
                    notificationManager
                )
            }

            else -> null
        }
    }
}
