package com.example.curiosity

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.app.NotificationCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.curiosity.CuriosityUsersHelper.Companion.initializeCuriosity
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_home.*

class CuriosityActivity : AppCompatActivity() {

    //La actionbar o hamburger icon gestisce il navigation drawer
    lateinit var actionBar : ActionBarDrawerToggle

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_curiosity)


        //Aggiunta del navigation drawer
        val drawerLayout : DrawerLayout = findViewById( R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START);
        val navView  : NavigationView = findViewById( R.id.nav_view)
        actionBar = androidx.appcompat.app.ActionBarDrawerToggle(this,drawerLayout,R.string.Open,R.string.Close)
        drawerLayout.addDrawerListener(actionBar)
        actionBar.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        auth = Firebase.auth

        //Gestione casi in seguito a click su menu del navigation drawer
        navView.setNavigationItemSelectedListener(){it->

            when(it.itemId)
            {
                //Click sulla pagina home
                R.id.nav_home->{
                    val transaction = supportFragmentManager.beginTransaction();
                    transaction.replace(R.id.container_view,HomeFragment())
                    transaction.commit()
                }

                //Click sulla pagina auto generator
                R.id.nav_auto_generator -> {
                    val transaction = supportFragmentManager.beginTransaction();
                    transaction.replace(R.id.container_view,GeneratorFragment())
                    transaction.commit()
                }

                //Click sulla pagina logout ( disconnette )
                R.id.nav_logout -> {
                    auth.signOut()

                    //manda a view del login
                    val intent = Intent(this, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK );

                    startActivity(intent)

                    finish()
                }

                //Click sulla pagina settings
                R.id.nav_settings -> {
                    val transaction = supportFragmentManager.beginTransaction();
                    transaction.replace(R.id.container_view,SettingsFragment())
                    transaction.commit()

                }
            }
            drawerLayout.closeDrawers();
            true
        }


        //Di default il fragment iniziale è quello denonominato Home
        val transaction = supportFragmentManager.beginTransaction();
        transaction.replace(R.id.container_view,HomeFragment())
        transaction.commit()

        if(intent.getStringExtra("la sapeva").equals("la sapeva"))
        {

            Log.i("metto" , "leggo - la sapeva ")

            val email = auth.currentUser?.email?.replace(".","")

            var nTotali = CuriosityUsersHelper.totalAnswer
            var nCorrette = CuriosityUsersHelper.totalCorrectAnswer
            nCorrette += 1

            nTotali += 1

            CuriosityUsersHelper.updateUserItem(email.toString(),"correctAnswer",nCorrette.toString())
            CuriosityUsersHelper.updateUserItem(email.toString(),"totalAnswer",nTotali.toString())

            intent.extras?.clear()
        }
        else if(intent.getStringExtra("non la sapeva").equals("non la sapeva"))
        {
            Log.i("metto" , "leggo - non la sapeva ")

            val email = auth.currentUser?.email?.replace(".","")

            var nTotali = CuriosityUsersHelper.totalAnswer
            nTotali += 1



            CuriosityUsersHelper.updateUserItem(email.toString(),"totalAnswer",nTotali.toString())

            intent.extras?.clear()
        }
        else{
            initializeCuriosity(this)
        }

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

        Log.i("devo schedulare ogni" , CuriosityUsersHelper.tempoMinutiNotifica)

        if(!CuriosityUsersHelper.tempoMinutiNotifica.equals(""))
        {
            alarmManager.setInexactRepeating(
                AlarmManager.RTC_WAKEUP,
                time,
                1000 * 60,
                pendingIntent
            )
        }

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
            channel.setLockscreenVisibility(NotificationCompat.VISIBILITY_PUBLIC);
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

    }

    //Click hamburger icon per far uscire il menu o cosiddetto navigation drawer
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        //Necessari alla classe per aprire / chiudere il nav drawer
        if(actionBar.onOptionsItemSelected(item))
        {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}