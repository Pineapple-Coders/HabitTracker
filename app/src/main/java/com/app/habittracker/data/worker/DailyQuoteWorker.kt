package com.app.habittracker.data.worker

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.app.habittracker.MainActivity
import com.app.habittracker.R
import com.app.habittracker.data.local.datastore.PreferencesManager
import com.app.habittracker.domain.usecase.quote.GetDailyQuoteUseCase
import com.app.habittracker.utils.Constants
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first

@HiltWorker
class DailyQuoteWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val getDailyQuoteUseCase: GetDailyQuoteUseCase,
    private val preferencesManager: PreferencesManager
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        // Check if daily quotes are enabled
        val quotesEnabled = preferencesManager.dailyQuotesEnabled.first()
        if (!quotesEnabled) {
            return Result.success()
        }

        try {
            val quote = getDailyQuoteUseCase()
            showNotification(quote.text, quote.author)
            return Result.success()
        } catch (e: Exception) {
            return Result.retry()
        }
    }

    private fun showNotification(quoteText: String, author: String) {
        val notificationManager = applicationContext.getSystemService(
            Context.NOTIFICATION_SERVICE
        ) as NotificationManager

        val intent = Intent(applicationContext, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            applicationContext,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(
            applicationContext,
            Constants.QUOTE_CHANNEL_ID
        )
            .setSmallIcon(android.R.drawable.ic_dialog_info) // We'll update this later
            .setContentTitle("Daily Motivation")
            .setContentText(quoteText)
            .setStyle(NotificationCompat.BigTextStyle().bigText("\"$quoteText\"\n- $author"))
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(NOTIFICATION_ID, notification)
    }

    companion object {
        private const val NOTIFICATION_ID = 1001
    }
}