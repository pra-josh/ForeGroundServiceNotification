package com.pra.foregroundservicealarmmanager.service

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.foregroundservicehandler.ResultActivity
import com.pra.foregroundservicealarmmanager.R


class BackGroundService : Service() {

    private val NOTIFICATION_ID = 9898

    override fun onCreate() {
        super.onCreate()
        Log.e("FR Jacket Library ::", "On Create")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val CHANNEL_ID = "BackGroundService"
            val channel = NotificationChannel(
                CHANNEL_ID, "com.backgroundservicedemo", NotificationManager.IMPORTANCE_DEFAULT
            )

            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)

            val intent = Intent(this, ResultActivity::class.java)
            intent.putExtra("com.background.notfyID", NOTIFICATION_ID)
            val pendindIntent = PendingIntent.getActivity(
                this, 0, intent, PendingIntent.FLAG_IMMUTABLE
            )

            val notification = NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Background Service Handler").setContentIntent(pendindIntent)
                .setSmallIcon(R.mipmap.ic_launcher)
                // TODO set Icon null to hide notification from custom OS
                //  .setLargeIcon(null)
                .setContentText("Background Service execution").build()

            startForeground(NOTIFICATION_ID, notification)

        }
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("Start Command ", "Start Command")
        val t = Thread { startJob() }
        t.start()
        return START_STICKY
    }


    private fun startJob() {
        println("Not Fire Command")
        try {
            Thread.sleep(5000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        startJob()
    }

    override fun onBind(intent: Intent?): IBinder? {
        throw UnsupportedOperationException("Not yet implemented")
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        restartService()
        super.onTaskRemoved(rootIntent)
    }

    private fun restartService() {
        try {
            var restartServiceIntent = Intent(applicationContext, this.javaClass)
            restartServiceIntent.`package` = packageName
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(restartServiceIntent)
            } else {
                startService(restartServiceIntent)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("ChargingStatus", "Charging Status service not started from TaskRemoved", e);
        }
    }
}