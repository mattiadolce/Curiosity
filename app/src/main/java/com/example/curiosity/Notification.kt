package com.example.curiosity

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.browser.customtabs.CustomTabsSession
import androidx.core.app.NotificationCompat
import androidx.core.content.contentValuesOf
import androidx.core.app.NotificationCompat.PRIORITY_MAX as PRI_MAX

const val notificationID = 1
const val channelID = "channel1"
const val titleExtra = "titleExtra"
const val messageExtra = "messageExtra"

class Notification : BroadcastReceiver()
{
    override fun onReceive(context: Context, intent: Intent)
    {
        val bigText = NotificationCompat.BigTextStyle()

        //var actionIntent : PendingIntent = PendingIntent.getBroadcast(context, 0, )

        val activityIntent = Intent(context,MainActivity::class.java)
        val contentIntent = PendingIntent.getActivity(context,0,activityIntent,0)

        val notification = NotificationCompat.Builder(context, channelID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(intent.getStringExtra(titleExtra))
            .setContentText(intent.getStringExtra(messageExtra))
            .setStyle(bigText)
            .setPriority(PRI_MAX)
            .addAction(R.drawable.ic_logo, "Lo sapevo", contentIntent)
            .addAction(R.drawable.ic_logo, "Non lo sapevo", contentIntent)


            .build()

        //.addAction(R.mipmap.ic_launcher, "Sapevo", actionIntent)
        //    .addAction(R.mipmap.ic_launcher, "Non sapevo", actionIntent)

        val  manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(notificationID, notification)
    }

}