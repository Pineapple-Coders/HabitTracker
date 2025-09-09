package com.app.habittracker.data.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.app.habittracker.data.worker.DailyQuoteWorker
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            // Reschedule daily quote worker after device reboot
            val dailyQuoteRequest = PeriodicWorkRequestBuilder<DailyQuoteWorker>(
                1, TimeUnit.DAYS
            ).build()

            WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                "daily_quote_work",
                ExistingPeriodicWorkPolicy.KEEP,
                dailyQuoteRequest
            )
        }
    }
}