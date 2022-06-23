package com.example.curiosity

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.browser.customtabs.CustomTabsSession
import androidx.core.app.NotificationCompat
import androidx.core.content.contentValuesOf
import kotlinx.android.synthetic.main.fragment_generator.*
import androidx.core.app.NotificationCompat.PRIORITY_MAX as PRI_MAX

const val notificationID = 1
const val channelID = "channel1"
var titleExtra = "titleExtra"
var messageExtra = "messageExtra"

class Notification : BroadcastReceiver()
{
    override fun onReceive(context: Context, intent: Intent)
    {
        if(CuriosityUsersHelper.listaAreeInteresse.size != 0)
        {
            val rnds = (0..9).random()
            val rnd2 = (0..CuriosityUsersHelper.listaAreeInteresse.size - 1).random()
            Log.i("qua", CuriosityUsersHelper.listaAreeInteresse[rnd2])
            Log.i("qua",
                CuriosityUsersHelper.mapCuriosita?.get(CuriosityUsersHelper.listaAreeInteresse[rnd2] + rnds)
                    .toString()
            )

            titleExtra = CuriosityUsersHelper.listaAreeInteresse[rnd2]
            messageExtra =
                CuriosityUsersHelper.mapCuriosita?.get(CuriosityUsersHelper.listaAreeInteresse[rnd2] + rnds)
                    .toString()
        }


        val bigText = NotificationCompat.BigTextStyle()

        //var actionIntent : PendingIntent = PendingIntent.getBroadcast(context, 0, )

        val activityIntent = Intent(context,MainActivity::class.java)
        val contentIntent = PendingIntent.getActivity(context,0,activityIntent,0)

        val notification = NotificationCompat.Builder(context, channelID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(titleExtra)
            .setContentText(messageExtra)
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