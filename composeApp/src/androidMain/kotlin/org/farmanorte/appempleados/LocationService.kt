package org.farmanorte.appempleados

import Requests
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class LocationService : Service() {

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private lateinit var locationClient: LocationClient

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        locationClient = DefaultLocationClient(
            applicationContext,
            LocationServices.getFusedLocationProviderClient(applicationContext)
        )
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when(intent?.action){
            ACTION_START -> start()
            ACTION_STOP -> stop()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun start(){
        val notification = NotificationCompat.Builder(this, "location")
            .setContentTitle("Rastreando Ubicación...")
            .setContentText("Ubicación: null")
            .setSmallIcon(R.drawable.logofarmanorte_foreground)
            .setOngoing(true)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val interval = 60000L

        locationClient
            .getLocationUpdates(interval)
            .catch { e -> e.printStackTrace() }
            .onEach { location ->
                val latitud = location.latitude
                val longitud = location.longitude

                val locationUpdated = Requests().UploadLocation(latitud, longitud)
                if(locationUpdated) {
                    val updatedNotification = notification.setContentText(
                        "Ultima Ubicación: ($latitud, $longitud)"
                    )
                    notificationManager.notify(1, updatedNotification.build())
                }
                notification.build()
            }
            .launchIn(serviceScope)
        startForeground(1, notification.build())
    }

    private fun stop(){
        stopForeground(true)
        stopSelf()
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }

    companion object{
        const val ACTION_START = "ACTION_START"
        const val ACTION_STOP = "ACTION_STOP"
    }
}