package com.example.curiosity

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_generator.*
import androidx.core.app.NotificationCompat.PRIORITY_MAX as PRI_MAX

const val notificationID = 1
const val channelID = "channel1"
var titleExtra = "titleExtra"
var messageExtra = "messageExtra"

class Notification : BroadcastReceiver()
{
    var auth: FirebaseAuth = Firebase.auth

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


        val  manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


        val activityIntentNonSapevo = Intent(context,NotificationLayout::class.java).apply {
        }

        val contentIntentNonSapevo = PendingIntent.getActivity(context,0,activityIntentNonSapevo,PendingIntent.FLAG_UPDATE_CURRENT)

        val activityIntentSapevo = Intent(context,NotificationLayoutSapeva::class.java).apply {
        }

        val contentIntentSapevo = PendingIntent.getActivity(context,0,activityIntentSapevo,PendingIntent.FLAG_UPDATE_CURRENT)



        val notification = NotificationCompat.Builder(context, channelID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(titleExtra)
            .setContentText(messageExtra)
            .setStyle(bigText)
            .setPriority(PRI_MAX)
            .setAutoCancel(true)
            .addAction(R.drawable.ic_logo, "Lo sapevo", contentIntentSapevo)
            .addAction(R.drawable.ic_logo, "Non Lo sapevo", contentIntentNonSapevo)
            .build()



        //.addAction(R.mipmap.ic_launcher, "Sapevo", actionIntent)
        //    .addAction(R.mipmap.ic_launcher, "Non sapevo", actionIntent)


        manager.notify(notificationID, notification)



    }

}