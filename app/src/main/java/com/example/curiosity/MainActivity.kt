package com.example.curiosity

import android.annotation.SuppressLint
import android.app.*
import android.app.Notification.VISIBILITY_PUBLIC
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat.VISIBILITY_PUBLIC as VP1
import java.util.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val transaction = supportFragmentManager.beginTransaction();
        transaction.replace(R.id.fragmentContainerView,LoginFragment())


        //actionbar.setHomeAsUpIndicator(R.drawable.ic_menu)


    }

    fun create_and_schedule()
    {
        createNotificationChannel()
        scheduleNotification()
    }


    override fun onStart() {
        super.onStart()
    }

    override fun onStop() {
        super.onStop()
    }

    fun scheduleNotification()
    {
        val intent = Intent(applicationContext, Notification::class.java)
        val title = "Titolo notifica ricorrente"
        val message = "Messaggio corpo del testo"
        intent.putExtra(titleExtra, title)
        intent.putExtra(messageExtra, message)

        val pendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            notificationID,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val time = System.currentTimeMillis()

        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            time,
            1000 * 60,
            pendingIntent
        )
    }


    fun createNotificationChannel()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            Log.i("entro","entro")

            val name = "Notif Channel"
            val desc = "A Description of the Channel"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(channelID, name, importance)
            channel.description = desc
            channel.setShowBadge(true);
            channel.setLockscreenVisibility(VP1);
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

    }

}