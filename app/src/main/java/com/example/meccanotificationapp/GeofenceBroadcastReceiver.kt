package com.example.meccanotificationapp

import android.Manifest
import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingEvent

class GeofenceBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        var geofencingEvent: GeofencingEvent? =null
        geofencingEvent = GeofencingEvent.fromIntent(intent)
        if (geofencingEvent != null)
            if (geofencingEvent.hasError()) return
        else
            Toast.makeText(context, "Error: No Geofencing Event", Toast.LENGTH_SHORT).show()

        val geofenceTransition = geofencingEvent?.geofenceTransition
        if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER) {
            showNotification(context)
        }
        Toast.makeText(context, "Notification Sent Receiver", Toast.LENGTH_SHORT).show()
    }

    private fun showNotification(context: Context) {
        val channelId = "makkah_channel"
        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("Welcome to Makkah!")
            .setContentText("You have entered the holy city.")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        val notificationManager = NotificationManagerCompat.from(context)
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                context as MainActivity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.POST_NOTIFICATIONS),
                1001
            )
        }
        notificationManager.notify(1001, notification)
    }
}